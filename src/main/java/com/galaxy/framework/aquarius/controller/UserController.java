package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Department;
import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.entity.User;
import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.aquarius.service.PositionService;
import com.galaxy.framework.aquarius.service.UserService;
import com.galaxy.framework.pisces.vo.aquarius.DepartmentVo;
import com.galaxy.framework.pisces.vo.aquarius.PositionVo;
import com.galaxy.framework.pisces.vo.aquarius.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public PageInfo<UserVo> findAll(String departmentCode, String code, String name, Integer pageNo, Integer pageSize) {
        User query = new User();
        query.setCode(code);
        query.setName(name);
        query.setDepartmentCode(departmentCode);
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = userService.select(query);
        List<UserVo> userVos = users.stream().map(user -> convert(user)).collect(Collectors.toList());
        PageInfo<UserVo> pageInfo = new PageInfo<>(userVos);
        return pageInfo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public UserVo save(@RequestBody UserVo userVo) {
        User user = convert(userVo);
        userService.save(user);
        return userVo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public int delete(@RequestBody List<String> codes) {
        userService.deleteByCode(codes);
        return 200;
    }

    private UserVo convert(User user) {
        UserVo vo = new UserVo();
        if (user != null) {
            BeanUtils.copyProperties(user, vo, "id", "departmentCode", "positionCode");
            Position position = positionService.selectByCode(user.getPositionCode(), "启用");
            if (position != null) {
                PositionVo positionVo = new PositionVo();
                BeanUtils.copyProperties(position, positionVo, "parentCode", "departmentCode");
                vo.setPositionVo(positionVo);
            }
            Department department = departmentService.selectByCode(user.getDepartmentCode(), "启用");
            if (department != null) {
                DepartmentVo departmentVo = new DepartmentVo();
                BeanUtils.copyProperties(department, departmentVo, "parentCode");
                vo.setDepartmentVo(departmentVo);
            }
        }
        return vo;
    }

    private User convert(UserVo vo) {
        User user = new User();
        if (vo != null) {
            BeanUtils.copyProperties(vo, user, "positionVo", "departmentVo", "positionVos");
            if (vo.getPositionVo() != null) {
                user.setPositionCode(vo.getPositionVo().getCode());
            }
            if (vo.getDepartmentVo() != null) {
                user.setDepartmentCode(vo.getDepartmentVo().getCode());
            }
        }
        return user;
    }
}
