package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Position;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface PositionService {

    Position insert(Position position);

    void insert(List<Position> positions);

    Position update(Position position);

    void update(List<Position> positions);

    int delete(Long id);

    void delete(List<Long> ids);

    Position selectByCode(String code, String status);

    Position save(Position position);

    List<Position> find(Map<String, Object> params);

    void reuse(List<String> codes);

    PageInfo<Position> page(Map<String, Object> params, int pageNo, int pageSize);
}
