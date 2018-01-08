package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper extends tk.mybatis.mapper.common.Mapper<Department> {
    List<Department> selectByFullPath(String fullPath);

    Department selectByCode(String code);

    List<Department> selectAllByStatus(String status);

    List<Department> selectAllOrderByFullPath();
}
