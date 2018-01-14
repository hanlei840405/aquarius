package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Position;

import java.util.List;

public interface PositionService extends CrudService<Position, Long> {

    Position selectByCode(String code, String status);

    Position save(Position position);

    void deleteByCode(List<String> codes);
}
