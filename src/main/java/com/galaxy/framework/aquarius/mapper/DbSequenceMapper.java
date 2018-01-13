package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DbSequenceMapper extends tk.mybatis.mapper.common.Mapper<Sequence> {
    int updateByVersion(Sequence sequence);
}
