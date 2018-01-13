package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.entity.Sequence;
import com.galaxy.framework.aquarius.mapper.DbSequenceMapper;
import com.galaxy.framework.aquarius.service.SequenceService;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;

@Service
public class DbSequenceServiceImpl implements SequenceService {

    @Autowired
    private DbSequenceMapper dbSequenceMapper;

    @Override
    public String generate(String category) {
        Sequence query = new Sequence();
        query.setCategory(category);
        Sequence sequence = dbSequenceMapper.selectOne(query);
        int cnt = dbSequenceMapper.updateByVersion(sequence);
        while (cnt == 0) {
             sequence = dbSequenceMapper.selectOne(query);
             cnt = dbSequenceMapper.updateByVersion(sequence);
        }
        String code = String.format("%08d", sequence.getSequence());
        return code;
    }
}
