package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper extends tk.mybatis.mapper.common.Mapper<Resource> {

    List<Resource> findByPosition(String positionCode);
}
