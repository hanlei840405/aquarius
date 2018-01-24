package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.pisces.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> selectByFullPath(String fullPath);

    Department selectByCode(String code);

    List<Department> selectAllByStatus(String status);

    List<Department> selectAllOrderByFullPath();

    List<Department> selectAllByParent(Map<String, Object> params);

    int deleteByCode(String code);
}