package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.service.DepartmentService;
import com.galaxy.framework.aquarius.service.PositionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public PageInfo<Position> findAll(String departmentCode, Integer pageNo, Integer pageSize) {
        PageInfo<Position> pageInfo = positionService.page(departmentCode, pageNo, pageSize);
        return pageInfo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/parents")
    public List<Position> parents(String departmentCode, String status) {
        Map<String, Object> params = new HashMap<>();
        Position query = new Position();
        query.setDepartmentCode(departmentCode);
        query.setStatus(status);
        List<Position> positions = positionService.find(query);
        return positions;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public Position save(@RequestBody Position position) {
        position.setStatus("启用");
        positionService.save(position);
        return position;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public int delete(@RequestBody List<String> codes) {
        positionService.deleteByCode(codes);
        return 200;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reuse")
    public int reuse(@RequestBody List<String> codes) {
        positionService.reuse(codes);
        return 200;
    }
}
