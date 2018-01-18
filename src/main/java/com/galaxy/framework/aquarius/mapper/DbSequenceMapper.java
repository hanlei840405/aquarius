package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DbSequenceMapper {
    int updateByVersion(Sequence sequence);
}
