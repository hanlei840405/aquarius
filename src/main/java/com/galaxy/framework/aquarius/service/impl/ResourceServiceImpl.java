package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.entity.Resource;
import com.galaxy.framework.aquarius.mapper.ResourceMapper;
import com.galaxy.framework.aquarius.service.ResourceService;
import com.galaxy.framework.pisces.db.DeleteException;
import com.galaxy.framework.pisces.db.InsertException;
import com.galaxy.framework.pisces.db.UpdateException;
import com.galaxy.framework.pisces.db.VersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class ResourceServiceImpl extends CrudServiceImpl<Resource, Long> implements ResourceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceMapper resourceMapper;

    @Transactional
    @Override
    public void insert(List<Resource> vars) {
        try {
            jdbcTemplate.batchUpdate("INSERT INTO sys_resource (code,name,parent_code,status,created,creator,version) VALUES (?,?,?,?,now(),?,1)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Resource resource = vars.get(i);
                            preparedStatement.setString(1, resource.getCode());
                            preparedStatement.setString(2, resource.getName());
                            preparedStatement.setString(3, resource.getParentCode());
                            preparedStatement.setString(4, resource.getStatus());
                            preparedStatement.setString(5, resource.getCreator());
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

    @Transactional
    @Override
    public void update(List<Resource> vars) {
        try {
            int[] arrays = jdbcTemplate.batchUpdate("UPDATE sys_resource SET name=?, parent_code=?, status=?, modifier=?, modified=now(), version=version+1 WHERE id=? AND version=?",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Resource resource = vars.get(i);
                            preparedStatement.setString(1, resource.getName());
                            preparedStatement.setString(2, resource.getParentCode());
                            preparedStatement.setString(3, resource.getStatus());
                            preparedStatement.setString(4, resource.getModifier());
                            preparedStatement.setLong(5, resource.getId());
                            preparedStatement.setInt(6, resource.getVersion());
                        }

                        @Override
                        public int getBatchSize() {
                            return vars.size();
                        }
                    });
            StringBuilder sb = new StringBuilder();
            for (int index : arrays) {
                if (index == 0) { // 因版本不一致更新失败
                    Resource resource = vars.get(index);
                    sb.append(resource.getName()).append(",");
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
    public void delete(List<Long> vars) {
        try {
            jdbcTemplate.batchUpdate("DELETE FROM sys_resource WHERE id=?", new BatchPreparedStatementSetter() {
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

    @Override
    public void grant(Resource resource, List<Position> positions) {
        try {
            jdbcTemplate.batchUpdate("INSERT INTO sys_resource_position (resource_code, position_code) VALUES (?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Position position = positions.get(i);
                            preparedStatement.setString(1, resource.getCode());
                            preparedStatement.setString(2, position.getCode());
                        }

                        @Override
                        public int getBatchSize() {
                            return positions.size();
                        }
                    });
        } catch (Exception e) {
            throw new InsertException();
        }
    }

    @Override
    public void cancel(Resource resource, List<Position> positions) {
        try {
            jdbcTemplate.batchUpdate("DELETE FROM sys_resource_position WHERE resource_code=? AND position_code=?", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Position position = positions.get(i);
                    preparedStatement.setString(1, resource.getCode());
                    preparedStatement.setString(2, position.getCode());
                }

                @Override
                public int getBatchSize() {
                    return positions.size();
                }
            });
        } catch (Exception e) {
            throw new DeleteException();
        }
    }

    @Override
    public List<Resource> findByPosition(String positionCode) {
        return resourceMapper.findByPosition(positionCode);
    }
}
