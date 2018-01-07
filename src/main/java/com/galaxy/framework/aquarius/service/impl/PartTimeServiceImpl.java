package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.entity.PartTime;
import com.galaxy.framework.aquarius.service.PartTimeService;
import com.galaxy.framework.pisces.db.DeleteException;
import com.galaxy.framework.pisces.db.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class PartTimeServiceImpl extends CrudServiceImpl<PartTime, Long> implements PartTimeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void insert(List<PartTime> vars) {
        try {
            jdbcTemplate.batchUpdate("INSERT INTO sys_part_time (user_code,position_code,status,created,creator,version) VALUES (?,?,?,now(),?,1)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            PartTime partTime = vars.get(i);
                            preparedStatement.setString(1, partTime.getUserCode());
                            preparedStatement.setString(2, partTime.getPositionCode());
                            preparedStatement.setString(3, partTime.getStatus());
                            preparedStatement.setString(4, partTime.getCreator());
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
    public void update(List<PartTime> vars) {
    }

    @Transactional
    @Override
    public void delete(List<Long> vars) {
        try {
            jdbcTemplate.batchUpdate("DELETE FROM sys_part_time WHERE id=?", new BatchPreparedStatementSetter() {
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
    public void deleteByUserCode(String userCode) {
        try {
            jdbcTemplate.update("DELETE FROM sys_part_time WHERE user_code=?", preparedStatement -> preparedStatement.setString(1, userCode));
        } catch (Exception e) {
            throw new DeleteException();
        }
    }
}
