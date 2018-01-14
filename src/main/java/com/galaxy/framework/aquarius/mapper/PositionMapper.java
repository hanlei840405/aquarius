package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionMapper extends tk.mybatis.mapper.common.Mapper<Position> {

    List<Position> findByCodes(List<String> codes);
}
