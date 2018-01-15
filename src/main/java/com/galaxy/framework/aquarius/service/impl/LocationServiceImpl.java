package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.entity.Location;
import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.mapper.LocationMapper;
import com.galaxy.framework.aquarius.service.LocationService;
import com.galaxy.framework.aquarius.service.SequenceService;
import com.galaxy.framework.pisces.exception.db.DeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hanlei6 on 2018/1/15.
 */
@Service
public class LocationServiceImpl extends CrudServiceImpl<Location, Long> implements LocationService {
    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private SequenceService redisSequenceService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Location selectByCode(String code, String status) {
        Location query = new Location();
        query.setCode(code);
        query.setStatus(status);
        return locationMapper.selectOne(query);
    }

    @Override
    public Location save(Location location) {
        Location exist = selectByCode(location.getCode(), "启用");
        if (exist != null) { // 更新
            update(location);
        } else { // 新增
            location.setCode(redisSequenceService.generate(Position.class.getName()));
            insert(location);
        }
        return location;
    }

    @Override
    public void insert(List<Location> vars) {

    }

    @Override
    public void update(List<Location> vars) {

    }

    @Override
    public void delete(List<Long> vars) {

    }

    @Transactional
    @Override
    public void deleteByCode(List<String> codes) {
        try {
            jdbcTemplate.batchUpdate("DELETE FROM sys_location WHERE code=?", new BatchPreparedStatementSetter() {
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
}
