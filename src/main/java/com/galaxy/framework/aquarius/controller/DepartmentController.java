package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Department;
import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.pisces.vo.TreeVo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/get")
    public Department get(String code) throws IOException {
        Department department = departmentService.selectByCode(code);
        return department;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/tree")
    public List<TreeVo> tree() throws IOException {
        List<Department> departments = departmentService.selectAll();
        List<TreeVo> treeVos = Lists.newArrayList();
        departments.forEach(department -> {
            TreeVo treeVo = new TreeVo();
            treeVo.setId(department.getCode());
            treeVo.setText(department.getName());
            if (department.isParent()) {
                treeVo.setType("folder");
            }
            if (StringUtils.isEmpty(department.getParentCode())) {
                treeVo.setParent("#");
            } else {
                treeVo.setParent(department.getParentCode());
            }
            treeVo.setState(ImmutableMap.of("opened", true));
            treeVos.add(treeVo);
        });
        return treeVos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public List<Department> findAll(String status) throws IOException {
        if (StringUtils.isEmpty(status)) {
            return departmentService.selectAll();
        }
        return departmentService.selectAllByStatus(status);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("save")
    public Department save(@RequestBody Department department) {
        return departmentService.save(department);
    }
}
