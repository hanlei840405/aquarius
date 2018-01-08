package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Department;

import java.util.List;

public interface DepartmentService extends CrudService<Department, Long> {
    List<Department> selectByFullPath(String fullPath);

    Department save(Department department);

    Department selectByCode(String code);

    List<Department> selectAllByStatus(String status);

    List<Department> selectAllOrderByFullPath();
}
