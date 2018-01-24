package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.pisces.entity.Department;
import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.pisces.exception.db.NotExistException;
import com.galaxy.framework.pisces.exception.rule.EmptyException;
import com.galaxy.framework.pisces.vo.TreeVo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/get")
    public Department get(String code) {
        Department department = departmentService.selectByCode(code);
        if (department != null) {
            return department;
        }
        throw new NotExistException();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/tree")
    public List<TreeVo> tree() {
        List<Department> departments = departmentService.selectAllOrderByFullPath();
        List<TreeVo> treeVos = Lists.newArrayList();
        departments.forEach(department -> {
            TreeVo treeVo = new TreeVo();
            treeVo.setId(department.getCode());
            treeVo.setText(department.getName());
            if (department.isParent()) {
                treeVo.setType("folder");
            }
            if (StringUtils.isEmpty(department.getDepartmentCode())) {
                treeVo.setParent("#");
            } else {
                treeVo.setParent(department.getDepartmentCode());
            }
            treeVo.setState(ImmutableMap.of("opened", true));
            treeVos.add(treeVo);
        });
        return treeVos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public List<Department> findAll(String status) {
        List<Department> departments = departmentService.selectAllByStatus(status);
        return departments;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public Department save(@RequestBody Department department) {
        if (department != null) {
            department.setStatus("启用");
            departmentService.save(department);
            return department;
        }
        throw new EmptyException();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public int delete(@RequestBody String code) {
        if (!StringUtils.isEmpty(code)) {
            return departmentService.deleteByCode(code);
        }
        throw new EmptyException();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reuse")
    public int reuse(@RequestBody String code) {
        if (!StringUtils.isEmpty(code)) {
            return departmentService.reuse(code);
        }
        throw new EmptyException();
    }
}
