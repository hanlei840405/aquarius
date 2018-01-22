package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.entity.Resource;
import com.galaxy.framework.aquarius.mapper.ResourceMapper;
import com.galaxy.framework.aquarius.service.PositionService;
import com.galaxy.framework.aquarius.service.ResourceService;
import com.galaxy.framework.aquarius.service.SequenceService;
import com.galaxy.framework.pisces.exception.db.DbException;
import com.galaxy.framework.pisces.exception.db.NotExistException;
import com.galaxy.framework.pisces.exception.db.UpdateException;
import com.galaxy.framework.pisces.exception.db.VersionException;
import com.galaxy.framework.pisces.exception.rule.NotEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private SequenceService redisSequenceService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PositionService positionService;

    @Override
    public Resource insert(Resource resource) {
        resourceMapper.insert(resource);
        return resource;
    }

    @Override
    public Resource update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
        return resource;
    }

    @Override
    public int delete(Long id) {
        return resourceMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Resource> find(Map<String, Object> search) {
        return resourceMapper.find(search);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Resource> selectAllOrderByFullPath() {
        return resourceMapper.selectAllOrderByFullPath();
    }

    @Override
    public Resource save(Resource resource) {
        if (!StringUtils.isEmpty(resource.getCode())) {
            Resource exist = selectByCode(resource.getCode());
            resource.setId(exist.getId());
            if (!StringUtils.pathEquals(exist.getResourceCode(), resource.getResourceCode())) { // 迁移到新的部门下，修改自本节点起以下所有节点的路径
                Resource parent = selectByCode(resource.getResourceCode()); // 新的上级组织

                resource.setFullPath(parent.getFullPath() + "-" + resource.getCode()); // 将全路径名称用新的替换掉旧的

                List<Resource> resources = selectByFullPath(exist.getFullPath()); // 根据旧路径找到所有需要变更的部门
                resources.forEach(res -> {
                    res.setFullPath(parent.getFullPath() + "-" + res.getCode()); // 将全路径名称用新的替换掉旧的
                });
                update(resources);
            }
            update(resource);
        } else {
            resource.setCode(redisSequenceService.generate(Resource.class.getName()));
            if (StringUtils.isEmpty(resource.getResourceCode())) { // 创建根节点
                resource.setFullPath(resource.getCode());
            } else {
                Resource parent = selectByCode(resource.getResourceCode());
                resource.setFullPath(parent.getFullPath() + "-" + resource.getCode());
                update(parent);
            }
            insert(resource);
        }
        return resource;
    }

    @Transactional(readOnly = true)
    @Override
    public Resource selectByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return resourceMapper.selectByCode(code);
    }

    @Override
    public void update(List<Resource> resources) {
        int[] arrays;
        try {
            arrays = jdbcTemplate.batchUpdate("UPDATE sys_resource SET name=?,full_path=?,resource_code=?,status=?,modifier=?,modified=now(),version=version+1 WHERE id=? AND version=?",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Resource resource = resources.get(i);
                            preparedStatement.setString(1, resource.getName());
                            preparedStatement.setString(2, resource.getFullPath());
                            preparedStatement.setString(3, resource.getResourceCode());
                            preparedStatement.setString(4, resource.getStatus());
                            preparedStatement.setString(5, resource.getModifier());
                            preparedStatement.setLong(6, resource.getId());
                            preparedStatement.setInt(7, resource.getVersion());
                        }

                        @Override
                        public int getBatchSize() {
                            return resources.size();
                        }
                    });
        } catch (Exception e) {
            throw new UpdateException();
        }
        StringBuilder sb = new StringBuilder();
        for (int index : arrays) {
            if (index == 0) { // 因版本不一致更新失败
                Resource resource = resources.get(index);
                sb.append(resource.getName()).append(",");
            }
        }
        if (sb.length() > 0) {
            sb.append("数据在更新过程中已被其他进程更新,请重新提交");
            throw new VersionException(sb.toString());
        }
    }

    private List<Resource> selectByFullPath(String fullPath) {
        return resourceMapper.selectByFullPath(fullPath);
    }


    @Override
    public int deleteByCode(String code) {
        Resource resource = selectByCode(code);
        if (resource == null) {
            throw new NotExistException("查询的资源不存在");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("resourceCode", code);
        params.put("status", "启用");
        List<Resource> resources = find(params);
        if (!resources.isEmpty()) {
            throw new NotEmptyException("存在启用的夏季资源，请确保下级资源都已删除");
        }
        params.clear();
        params.put("resourceCode", code);
        params.put("status", "启用");
        List<Position> positions = positionService.findByResource(params);
        if (!positions.isEmpty()) {
            throw new NotEmptyException("存在授权的岗位，请确保该资源已与岗位解除关系");
        }
        resource.setStatus("删除");
        update(resource);
        return 200;
    }

    @Override
    public int reuse(String code) {
        Resource resource = selectByCode(code);
        if (resource == null) {
            throw new NotExistException("查询的资源不存在");
        }
        Resource parent = selectByCode(resource.getResourceCode());
        if (parent == null || "删除".equals(parent.getStatus())) {
            throw new DbException("未找到启用的上级资源");
        }
        resource.setStatus("启用");
        update(resource);
        return 200;
    }

    @Override
    public int grant(String code, List<String> creates, List<String> deletes) {
        jdbcTemplate.batchUpdate("DELETE FROM sys_resource_position WHERE resource_code=? AND position_code=?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                String position = deletes.get(i);
                preparedStatement.setString(1, code);
                preparedStatement.setString(2, position);
            }

            @Override
            public int getBatchSize() {
                return deletes.size();
            }
        });
        jdbcTemplate.batchUpdate("INSERT INTO sys_resource_position (resource_code, position_code) SELECT ?,? FROM DUAL " +
                "WHERE NOT EXISTS (SELECT * FROM sys_resource_position WHERE resource_code=? AND position_code=?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                String position = creates.get(i);
                preparedStatement.setString(1, code);
                preparedStatement.setString(2, position);
                preparedStatement.setString(3, code);
                preparedStatement.setString(4, position);
            }

            @Override
            public int getBatchSize() {
                return creates.size();
            }
        });
        return 200;
    }
}
