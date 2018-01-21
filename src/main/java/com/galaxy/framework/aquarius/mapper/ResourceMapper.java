package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    List<Resource> find(Map<String, Object> search);

    Resource selectByCode(String code);

    List<Resource> selectAllOrderByFullPath();

    List<Resource> selectByFullPath(String fullPath);
}