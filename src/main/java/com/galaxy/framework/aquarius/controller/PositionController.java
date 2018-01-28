package com.galaxy.framework.aquarius.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.framework.aquarius.entity.Position;
import com.galaxy.framework.aquarius.service.PositionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/page")
    public PageInfo<Position> page(String search, Integer pageNo, Integer pageSize) throws IOException {

        PageInfo<Position> pageInfo = positionService.page(objectMapper.readValue(search, new TypeReference<Map<String, Object>>() {
        }), pageNo, pageSize);
        return pageInfo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public List<Position> findAll(String departmentCode, String resourceCode) {
        Map<String, Object> search = new HashMap<>();
        search.put("departmentCode", departmentCode);
        search.put("resourceCode", resourceCode);
        search.put("status", "启用");
        List<Position> positions = positionService.find(search);
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
    public int delete(@RequestBody Position position) {
        Position exist = positionService.selectByCode(position.getCode());
        exist.setStatus("删除");
        positionService.update(exist);
        return 200;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reuse")
    public int reuse(@RequestBody Position position) {
        Position exist = positionService.selectByCode(position.getCode());
        exist.setStatus("启用");
        positionService.update(exist);
        return 200;
    }
}
