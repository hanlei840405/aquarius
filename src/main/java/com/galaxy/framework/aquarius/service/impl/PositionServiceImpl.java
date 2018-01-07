package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.service.PositionService;
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
public class PositionServiceImpl extends CrudServiceImpl<Position, Long> implements PositionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void insert(List<Position> vars) {
        try {
            jdbcTemplate.batchUpdate("INSERT INTO sys_position (code,name,parent_code,department_code,status,created,creator,version) VALUES (?,?,?,?,?,now(),?,1)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Position position = vars.get(i);
                            preparedStatement.setString(1, position.getCode());
                            preparedStatement.setString(2, position.getName());
                            preparedStatement.setString(3, position.getParentCode());
                            preparedStatement.setString(4, position.getDepartmentCode());
                            preparedStatement.setString(5, position.getStatus());
                            preparedStatement.setString(6, position.getCreator());
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
    public void update(List<Position> vars) {
        try {
            int[] arrays = jdbcTemplate.batchUpdate("UPDATE sys_position SET name=?, parent_code=?, department_code=?, status=?, modifier=?, modified=now(), version=version+1 WHERE id=? AND version=?",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            Position position = vars.get(i);
                            preparedStatement.setString(1, position.getName());
                            preparedStatement.setString(2, position.getParentCode());
                            preparedStatement.setString(3, position.getDepartmentCode());
                            preparedStatement.setString(4, position.getStatus());
                            preparedStatement.setString(5, position.getModifier());
                            preparedStatement.setLong(6, position.getId());
                            preparedStatement.setInt(7, position.getVersion());
                        }

                        @Override
                        public int getBatchSize() {
                            return vars.size();
                        }
                    });
            StringBuilder sb = new StringBuilder();
            for (int index : arrays) {
                if (index == 0) { // 因版本不一致更新失败
                    Position position = vars.get(index);
                    sb.append(position.getName()).append(",");
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
            jdbcTemplate.batchUpdate("DELETE FROM sys_position WHERE id=?", new BatchPreparedStatementSetter() {
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
}
