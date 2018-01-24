package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.pisces.entity.Location;
import com.galaxy.framework.aquarius.mapper.LocationMapper;
import com.galaxy.framework.aquarius.service.LocationService;
import com.galaxy.framework.aquarius.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hanlei6 on 2018/1/15.
 */
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<Location> findByStatus(String status) {
        return locationMapper.findByStatus(status);
    }

    @Override
    public Location selectByCode(String code, String status) {
        Location query = new Location();
        query.setCode(code);
        query.setStatus(status);
        return locationMapper.selectByCode(query);
    }
}
