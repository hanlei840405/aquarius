package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.Department;

import java.util.List;

public interface DepartmentService {

    Department insert(Department department);

    void insert(List<Department> departments);

    Department update(Department department);

    void update(List<Department> departments);

    int delete(Long id);

    void delete(List<Long> ids);

    List<Department> selectByFullPath(String fullPath);

    Department save(Department department);

    Department selectByCode(String code);

    List<Department> selectAllByStatus(String status);

    List<Department> selectAllOrderByFullPath();

    int deleteByCode(String code);

    int reuse(String code);
}
