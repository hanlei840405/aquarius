package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Location;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocationMapper extends tk.mybatis.mapper.common.Mapper<Location> {
    int deleteByCode(String code);
}
