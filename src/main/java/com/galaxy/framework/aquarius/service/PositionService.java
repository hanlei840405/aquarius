package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Position;

import java.util.List;
import java.util.Map;

public interface PositionService extends CrudService<Position, Long> {

    Position selectByCode(String code, String status);

    Position save(Position position);

    void deleteByCode(List<String> codes);

    List<Position> selectByDepartment(Map<String, Object> params);

    List<Position> selectByParent(Map<String, Object> params);

    void reuse(List<String> codes);
}
