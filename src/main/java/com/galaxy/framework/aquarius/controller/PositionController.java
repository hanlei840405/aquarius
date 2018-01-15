package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Department;
import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.aquarius.service.PositionService;
import com.galaxy.framework.pisces.vo.aquarius.DepartmentVo;
import com.galaxy.framework.pisces.vo.aquarius.PositionVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public PageInfo<PositionVo> findAll(String departmentCode, Integer pageNo, Integer pageSize) {
        Position query = new Position();
        query.setDepartmentCode(departmentCode);
        PageHelper.startPage(pageNo, pageSize);
        List<Position> positions = positionService.select(query);
        List<PositionVo> positionVos = positions.stream().map(position -> convert(position)).collect(Collectors.toList());
        PageInfo<PositionVo> pageInfo = new PageInfo<>(positionVos);
        return pageInfo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/parents")
    public List<PositionVo> parents(String departmentCode, String status) {
        Position query = new Position();
        query.setDepartmentCode(departmentCode);
        query.setStatus(status);
        List<Position> positions = positionService.select(query);
        List<PositionVo> positionVos = Lists.newArrayList();
        positions.forEach(position -> {
            positionVos.add(convert(position));
        });
        return positionVos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public PositionVo save(@RequestBody PositionVo positionVo) {
        Position position = convert(positionVo);
        positionService.save(position);
        return positionVo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public int delete(@RequestBody List<String> codes) {
        positionService.deleteByCode(codes);
        return 200;
    }

    private PositionVo convert(Position position) {
        PositionVo vo = new PositionVo();
        if (position != null) {
            BeanUtils.copyProperties(position, vo, "id", "parentCode", "departmentCode");
            Position parent = positionService.selectByCode(position.getParentCode(), "启用");
            if (parent != null) {
                PositionVo parentVo = new PositionVo();
                BeanUtils.copyProperties(parent, parentVo, "parentCode", "departmentCode");
                vo.setPositionVo(parentVo);
            }
            Department department = departmentService.selectByCode(position.getDepartmentCode(), "启用");
            if (department != null) {
                DepartmentVo departmentVo = new DepartmentVo();
                BeanUtils.copyProperties(department, departmentVo, "parentCode");
                vo.setDepartmentVo(departmentVo);
            }
        }
        return vo;
    }

    private Position convert(PositionVo vo) {
        Position position = new Position();
        if (vo != null) {
            BeanUtils.copyProperties(vo, position, "positionVo", "departmentVo");
            if (vo.getPositionVo() != null) {
                position.setParentCode(vo.getPositionVo().getCode());
            }
            if (vo.getDepartmentVo() != null) {
                position.setDepartmentCode(vo.getDepartmentVo().getCode());
            }
        }
        return position;
    }
}
