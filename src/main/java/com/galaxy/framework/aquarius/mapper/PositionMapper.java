package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PositionMapper extends tk.mybatis.mapper.common.Mapper<Position> {

    List<Position> findByCodes(List<String> codes);

    List<Position> selectByDepartment(Map<String, Object> params);

    List<Position> selectByParent(Map<String, Object> params);
}
