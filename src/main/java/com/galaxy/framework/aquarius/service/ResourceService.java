package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceService {

    Resource insert(Resource resource);

    Resource update(Resource resource);

    int delete(Long id);

    List<Resource> find(Map<String, Object> search);

    List<Resource> selectAllOrderByFullPath();

    Resource save(Resource resource);

    Resource selectByCode(String code);

    void update(List<Resource> resources);

    int deleteByCode(String code);

    int reuse(String code);

    int grant(String code, List<String> creates, List<String> deletes);
}
