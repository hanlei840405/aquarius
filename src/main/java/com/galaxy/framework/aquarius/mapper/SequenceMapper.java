package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Department;
import com.galaxy.framework.aquarius.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SequenceMapper extends tk.mybatis.mapper.common.Mapper<Sequence> {
    int updateByVersion(Sequence sequence);
}
