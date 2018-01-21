package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PositionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> find(Map<String,Object> params);

    Position selectByCode(String code);

    List<Position> findByResource(Map<String,Object> params);
}