package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.pisces.entity.User;
import com.galaxy.framework.aquarius.mapper.UserMapper;
import com.galaxy.framework.aquarius.service.SequenceService;
import com.galaxy.framework.aquarius.service.UserService;
import com.galaxy.framework.pisces.exception.db.DeleteException;
import com.galaxy.framework.pisces.exception.db.InsertException;
import com.galaxy.framework.pisces.exception.db.UpdateException;
import com.galaxy.framework.pisces.exception.db.VersionException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SequenceService redisSequenceService;


    @Override
    public User selectByCode(String code) {
        return userMapper.selectByCode(code);
    }

    @Override
    public List<User> find(Map<String, Object> search) {
        return userMapper.find(search);
    }

    @Override
    public User insert(User user) {
        try {
            userMapper.insert(user);
            return user;
        } catch (Exception e) {
            throw new InsertException();
        }
    }

    @Override
    public User update(User user) {
        try {
            int cnt = userMapper.updateByPrimaryKey(user);
            if (cnt == 0) {
                throw new VersionException();
            }
            return user;
        } catch (Exception e) {
            throw new UpdateException();
        }
    }

    @Transactional
    @Override
    public void insert(List<User> users) {
        try {
            jdbcTemplate.batchUpdate("INSERT INTO sys_user (code,name,email,mobile,gender,birthday,entryDay,regularDay,leaveDay,position_code,department_code,head_img,status,created,creator,version) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,1)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            User user = users.get(i);
                            preparedStatement.setString(1, user.getCode());
                            preparedStatement.setString(2, user.getName());
                            preparedStatement.setString(3, user.getEmail());
                            preparedStatement.setString(4, user.getMobile());
                            preparedStatement.setString(5, user.getGender());
                            preparedStatement.setString(6, user.getBirthday());
                            preparedStatement.setString(7, user.getEntryDay());
                            preparedStatement.setString(8, user.getRegularDay());
                            preparedStatement.setString(9, user.getLeaveDay());
                            preparedStatement.setString(10, user.getPositionCode());
                            preparedStatement.setString(11, user.getDepartmentCode());
                            preparedStatement.setString(12, user.getHeadImg());
                            preparedStatement.setString(13, user.getStatus());
                            preparedStatement.setString(14, user.getCreator());
                        }

                        @Override
                        public int getBatchSize() {
                            return users.size();
                        }
                    });
        } catch (Exception e) {
            throw new InsertException();
        }
    }

    @Transactional
    @Override
    public void update(List<User> users) {
        try {
            int[] arrays = jdbcTemplate.batchUpdate("UPDATE sys_user SET name=?, email=?, mobile=?, gender=?, birthday=?, entryDay=?, regularDay=?, leaveDay=?, position_code=?, department_code=?, head_img=?, status=?, modified=now(), version=version+1 WHERE id=? AND version=?",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            User user = users.get(i);
                            preparedStatement.setString(1, user.getName());
                            preparedStatement.setString(2, user.getEmail());
                            preparedStatement.setString(3, user.getMobile());
                            preparedStatement.setString(4, user.getGender());
                            preparedStatement.setString(5, user.getBirthday());
                            preparedStatement.setString(6, user.getEntryDay());
                            preparedStatement.setString(7, user.getRegularDay());
                            preparedStatement.setString(8, user.getLeaveDay());
                            preparedStatement.setString(9, user.getPositionCode());
                            preparedStatement.setString(10, user.getDepartmentCode());
                            preparedStatement.setString(11, user.getHeadImg());
                            preparedStatement.setString(12, user.getStatus());
                            preparedStatement.setLong(13, user.getId());
                            preparedStatement.setInt(14, user.getVersion());
                        }

                        @Override
                        public int getBatchSize() {
                            return users.size();
                        }
                    });
            StringBuilder sb = new StringBuilder();
            for (int index : arrays) {
                if (index == 0) { // 因版本不一致更新失败
                    User user = users.get(index);
                    sb.append(user.getName()).append(",");
                }
            }
            if (sb.length() > 0) {
                sb.append("数据在更新过程中已被其他进程更新,请重新提交");
                throw new VersionException(sb.toString());
            }
        } catch (Exception e) {
            throw new UpdateException();
        }
    }

    @Transactional
    @Override
    public void delete(List<Long> users) {
        try {
            jdbcTemplate.batchUpdate("DELETE FROM sys_user WHERE id=?", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Long id = users.get(i);
                    preparedStatement.setLong(1, id);
                }

                @Override
                public int getBatchSize() {
                    return users.size();
                }
            });
        } catch (Exception e) {
            throw new DeleteException();
        }
    }

    @Override
    public User save(User user) {
        if (StringUtils.isEmpty(user.getCode())) { // 新增
            String sequence = redisSequenceService.generate(User.class.getName());
            user.setCode(sequence);
            insert(user);
        } else { // 更新
            update(user);
        }
        return user;
    }

    @Override
    public void deleteByCode(List<String> codes) {
        try {
            jdbcTemplate.batchUpdate("DELETE FROM sys_user WHERE code=?", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    preparedStatement.setString(1, codes.get(i));
                }

                @Override
                public int getBatchSize() {
                    return codes.size();
                }
            });
        } catch (Exception e) {
            throw new DeleteException();
        }
    }

    @Override
    public PageInfo<User> page(Map<String, Object> search, Integer pageNo, Integer pageSize) {
        PageInfo<User> pageInfo = PageHelper.startPage(pageNo, pageSize).doSelectPageInfo(() -> find(search));
        return pageInfo;
    }
}
