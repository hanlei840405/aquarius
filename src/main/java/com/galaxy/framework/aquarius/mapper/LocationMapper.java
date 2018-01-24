package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.pisces.entity.Location;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Location record);

    int insertSelective(Location record);

    Location selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);

    List<Location> findByStatus(String status);

    Location selectByCode(Location location);

    int deleteByCode(String code);
}