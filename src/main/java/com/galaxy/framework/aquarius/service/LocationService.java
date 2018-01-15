package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Location;

import java.util.List;

/**
 * Created by hanlei6 on 2018/1/15.
 */
public interface LocationService extends CrudService<Location, Long> {
    List<Location> findByStatus(String status);

    Location selectByCode(String code, String status);

    void deleteByCode(List<String> codes);

    Location save(Location location);
}
