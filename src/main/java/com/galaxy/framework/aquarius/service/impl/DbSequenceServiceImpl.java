package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.mapper.DbSequenceMapper;
import com.galaxy.framework.aquarius.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbSequenceServiceImpl implements SequenceService {

    @Autowired
    private DbSequenceMapper dbSequenceMapper;

    @Override
    public String generate(String category) {
        return null;
    }
}
