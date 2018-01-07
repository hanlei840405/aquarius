package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.entity.Resource;

import java.util.List;

public interface ResourceService extends CrudService<Resource, Long> {
    void grant(Resource resource, List<Position> positions);

    void cancel(Resource resource, List<Position> positions);

    List<Resource> findByPosition(String positionCode);
}
