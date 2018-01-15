package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Department;

import java.util.List;

public interface DepartmentService extends CrudService<Department, Long> {
    List<Department> selectByFullPath(String fullPath);

    Department save(Department department);

    Department selectByCode(String code, String status);

    Department selectParentByCode(String code, String status, boolean parent);

    List<Department> selectAllByStatus(String status);

    List<Department> selectAllOrderByFullPath();

    int deleteByCode(String code);
}
