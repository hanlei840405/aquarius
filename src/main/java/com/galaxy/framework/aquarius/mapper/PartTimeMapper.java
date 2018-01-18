package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.PartTime;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PartTimeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartTime record);

    int insertSelective(PartTime record);

    PartTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartTime record);

    int updateByPrimaryKey(PartTime record);
}