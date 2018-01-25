package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.pisces.entity.Department;
import com.galaxy.framework.pisces.exception.db.NotExistException;
import com.galaxy.framework.pisces.exception.rule.EmptyException;
import com.galaxy.framework.pisces.vo.TreeVo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/department")
    public String index() {
        return "system/department";
    }

    @RequestMapping("/department/get")
    @ResponseBody
    public Department get(String code) {
        Department department = departmentService.selectByCode(code);
        if (department != null) {
            return department;
        }
        throw new NotExistException();
    }

    @RequestMapping("/department/tree")
    @ResponseBody
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

    @RequestMapping("/department/findAll")
    @ResponseBody
    public List<Department> findAll(String status) {
        if (StringUtils.isEmpty(status)) {
            status="启用";
        }
        List<Department> departments = departmentService.selectAllByStatus(status);
        return departments;
    }

    @PostMapping("/department/save")
    @ResponseBody
    public Department save(Department department) {
        if (department != null) {
            department.setStatus("启用");
            departmentService.save(department);
            return department;
        }
        throw new EmptyException();
    }

    @PostMapping("/department/delete")
    @ResponseBody
    public int delete(@RequestBody String code) {
        if (!StringUtils.isEmpty(code)) {
            return departmentService.deleteByCode(code);
        }
        throw new EmptyException();
    }

    @PostMapping("/department/reuse")
    @ResponseBody
    public int reuse(@RequestBody String code) {
        if (!StringUtils.isEmpty(code)) {
            return departmentService.reuse(code);
        }
        throw new EmptyException();
    }
}
