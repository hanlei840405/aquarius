package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Department;
import com.galaxy.framework.aquarius.entity.Location;
import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.aquarius.service.LocationService;
import com.galaxy.framework.pisces.exception.db.NotExistException;
import com.galaxy.framework.pisces.exception.rule.EmptyException;
import com.galaxy.framework.pisces.vo.TreeVo;
import com.galaxy.framework.pisces.vo.aquarius.DepartmentVo;
import com.galaxy.framework.pisces.vo.aquarius.LocationVo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/get")
    public DepartmentVo get(String code, String status) throws IOException {
        Department department = departmentService.selectByCode(code, status);
        if (department != null) {
            DepartmentVo departmentVo = convert(department);
            return departmentVo;
        }
        throw new NotExistException();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/tree")
    public List<TreeVo> tree() throws IOException {
        List<Department> departments = departmentService.selectAllOrderByFullPath();
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
    public List<DepartmentVo> findAll(String status) throws IOException {
        List<Department> departments;
        if (StringUtils.isEmpty(status)) {
            departments = departmentService.selectAll();
        } else {
            departments = departmentService.selectAllByStatus(status);
        }
        List<DepartmentVo> departmentVos = departments.stream().map(department -> convert(department)).collect(Collectors.toList());
        return departmentVos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("save")
    public DepartmentVo save(@RequestBody DepartmentVo departmentVo) {
        if (departmentVo != null) {
            departmentService.save(convert(departmentVo));
            return departmentVo;
        }
        throw new EmptyException();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("delete")
    public int delete(@RequestBody DepartmentVo departmentVo) {
        return departmentService.deleteByCode(departmentVo.getCode());
    }

    private DepartmentVo convert(Department department) {
        DepartmentVo vo = new DepartmentVo();
        BeanUtils.copyProperties(department, vo, "id", "parentCode", "locationCode");
        Department parent = departmentService.selectByCode(department.getParentCode(), "启用");
        if (parent != null) {
            DepartmentVo parentVo = new DepartmentVo();
            BeanUtils.copyProperties(parent, parentVo, "id", "parentCode", "locationCode");
            vo.setDepartmentVo(parentVo);
        }
        Location location = locationService.selectByCode(department.getLocationCode(), "启用");
        if (location != null) {
            LocationVo locationVo = new LocationVo();
            BeanUtils.copyProperties(location, locationVo, "id");
            vo.setLocationVo(locationVo);
        }
        return vo;
    }

    private Department convert(DepartmentVo vo) {
        Department department = new Department();
        BeanUtils.copyProperties(vo, department, "departmentVo", "positionVos", "locationVo");
        if (vo.getDepartmentVo() != null) {
            department.setParentCode(vo.getDepartmentVo().getCode());
        }
        if (vo.getLocationVo() != null) {
            department.setLocationCode(vo.getLocationVo().getCode());
        }
        return department;
    }
}
