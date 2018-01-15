package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.entity.Department;
import com.galaxy.framework.aquarius.mapper.DepartmentMapper;
import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.aquarius.service.SequenceService;
import com.galaxy.framework.pisces.exception.db.DeleteException;
import com.galaxy.framework.pisces.exception.db.InsertException;
import com.galaxy.framework.pisces.exception.db.UpdateException;
import com.galaxy.framework.pisces.exception.db.VersionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Transactional
@Service
public class DepartmentServiceImpl extends CrudServiceImpl<Department, Long> implements DepartmentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SequenceService redisSequenceService;

    @Override
    public void insert(List<Department> vars) {
        try {
            jdbcTemplate.batchUpdate("INSERT INTO sys_department(code,name,parent_code,full_path,full_name,status,created,creator,version) VALUES (?,?,?,?,?,?,now(),?,1)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Department department = vars.get(i);
                            preparedStatement.setString(1, department.getCode());
                            preparedStatement.setString(2, department.getName());
                            preparedStatement.setString(3, department.getParentCode());
                            preparedStatement.setString(4, department.getFullPath());
                            preparedStatement.setString(5, department.getFullName());
                            preparedStatement.setString(6, department.getStatus());
                            preparedStatement.setString(7, department.getCreator());
                        }

                        @Override
                        public int getBatchSize() {
                            return vars.size();
                        }
                    });
        } catch (Exception e) {
            throw new InsertException();
        }
    }

    @Override
    public void update(List<Department> vars) {
        int[] arrays = null;
        try {
            arrays = jdbcTemplate.batchUpdate("UPDATE sys_department SET name=?,full_path=?,full_name=?,parent_code=?,status=?,modifier=?,modified=now(),version=version+1 WHERE id=? AND version=?",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Department department = vars.get(i);
                            preparedStatement.setString(1, department.getName());
                            preparedStatement.setString(2, department.getFullPath());
                            preparedStatement.setString(3, department.getFullName());
                            preparedStatement.setString(4, department.getParentCode());
                            preparedStatement.setString(5, department.getStatus());
                            preparedStatement.setString(6, department.getModifier());
                            preparedStatement.setLong(7, department.getId());
                            preparedStatement.setInt(8, department.getVersion());
                        }

                        @Override
                        public int getBatchSize() {
                            return vars.size();
                        }
                    });
        } catch (Exception e) {
            throw new UpdateException();
        }
        StringBuilder sb = new StringBuilder();
        for (int index : arrays) {
            if (index == 0) { // 因版本不一致更新失败
                Department department = vars.get(index);
                sb.append(department.getFullName()).append(",");
            }
        }
        if (sb.length() > 0) {
            sb.append("数据在更新过程中已被其他进程更新,请重新提交");
            throw new VersionException(sb.toString());
        }
    }

    @Override
    public void delete(List<Long> vars) {
        try {
            jdbcTemplate.batchUpdate("DELETE FROM sys_department WHERE id=?", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Long id = vars.get(i);
                    preparedStatement.setLong(1, id);
                }

                @Override
                public int getBatchSize() {
                    return vars.size();
                }
            });
        } catch (Exception e) {
            throw new DeleteException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> selectByFullPath(String fullPath) {
        return departmentMapper.selectByFullPath(fullPath);
    }

    @Override
    public Department save(Department department) {
        if (StringUtils.isEmpty(department.getCode())) {
            Department exist = selectByCode(department.getCode(), "启用");
            if (!StringUtils.pathEquals(exist.getParentCode(), department.getParentCode())) { // 迁移到新的部门下，修改自本节点起以下所有节点的路径
                Department parent = selectParentByCode(department.getParentCode(), "启用", true); // 新的上级组织
                List<Department> departments = selectByFullPath(exist.getFullPath()); // 根据旧路径找到所有需要变更的部门
                departments.forEach(dept -> {
                    dept.setFullName(dept.getFullName().replace(exist.getFullName(), parent.getFullName())); // 将全路径名称用新的替换掉旧的
                    dept.setFullPath(dept.getFullPath().replace(exist.getFullPath(), parent.getFullPath())); // 将全路径名称用新的替换掉旧的
                });
                update(departments);
                // 更新新上级的parent状态
                parent.setParent(true);
                update(parent);

                // 查找旧的上级，并判断是否更新parent状态
                Department oldParent = selectParentByCode(exist.getParentCode(), "启用", true); // 新的上级组织
                departments = selectByFullPath(oldParent.getFullPath());
                if (departments.isEmpty()) { // 旧有上级已无下级部门，需要更新parent状态
                    oldParent.setParent(false);
                    update(oldParent);
                }
            }


            if (!exist.getName().equals(department.getName())) {// 名字变更，更新自本届点起及以下节点所有节点的fullName
                // 先变更本届点fullName，后变更字节点fullName
                StringBuilder sb = new StringBuilder(department.getFullName());
                int index = sb.lastIndexOf(exist.getName());
                if (index > 0) {
                    department.setFullName(sb.replace(index, sb.length(), department.getName()).toString()); // 变更本节点
                }
                // 查询所有字节点, 不包括本节点
                List<Department> departments = selectByFullPath(exist.getFullPath() + "-");
                departments.forEach(dept -> {
                    dept.setFullName(dept.getFullName().replace(exist.getFullName(), department.getFullName())); // 将全路径名称用新的替换掉旧的
                });
                // 更新字节点fullName
                update(departments);
            }

            if (!exist.getStatus().equals(department.getStatus())) {// 状态变更，更新自本节点起以下所有节点状态
                // 用department的fullName替代exist的fullName查询所有字节点, 不包括本节点
                List<Department> departments = selectByFullPath(department.getFullPath() + "-");
                departments.forEach(dept -> {
                    dept.setStatus(department.getStatus());
                });
                // 更新子节点状态
                update(departments);
            }
            update(department);
        } else {
            department.setCode(redisSequenceService.generate(Department.class.getName()));
            if (StringUtils.isEmpty(department.getParentCode())) { // 创建根节点
                department.setFullPath(department.getCode());
                department.setFullName(department.getName());
            } else {
                Department parent = selectParentByCode(department.getParentCode(), "启用", true);
                department.setFullPath(parent.getFullPath() + "-" + department.getCode());
                department.setFullName(parent.getFullName() + "-" + department.getName());
                parent.setParent(true);
                update(parent);
            }
            insert(department);
        }
        return department;
    }

    @Override
    public Department selectByCode(String code, String status) {
        Department query = new Department();
        query.setCode(code);
        query.setStatus(status);
        return departmentMapper.selectByCode(query);
    }

    @Transactional(readOnly = true)
    @Override
    public Department selectParentByCode(String code, String status, boolean parent) {
        Department query = new Department();
        query.setCode(code);
        query.setStatus(status);
        query.setParent(parent);
        return departmentMapper.selectOne(query);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> selectAllByStatus(String status) {
        return departmentMapper.selectAllByStatus(status);
    }

    @Override
    public List<Department> selectAllOrderByFullPath() {
        return departmentMapper.selectAllOrderByFullPath();
    }

    @Override
    public int deleteByCode(String code) {
        return 0;
    }
}
