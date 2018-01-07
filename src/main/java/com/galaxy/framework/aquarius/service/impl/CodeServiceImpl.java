package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.service.CodeService;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String generate(String category) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(category);
        String code = String.format("%08d", atomicLong.incrementAndGet());
        return code;
    }
}
