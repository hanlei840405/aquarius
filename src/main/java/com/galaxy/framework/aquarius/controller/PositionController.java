package com.galaxy.framework.aquarius.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.framework.aquarius.service.PositionService;
import com.galaxy.framework.pisces.entity.Position;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PositionController {

    @Autowired
    private PositionService positionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/position")
    public String index() {
        return "position";
    }

    @RequestMapping("/position/page")
    @ResponseBody
    public PageInfo<Position> page(String search, Integer pageNo, Integer pageSize) throws IOException {

        PageInfo<Position> pageInfo = positionService.page(objectMapper.readValue(search, new TypeReference<Map<String, Object>>() {
        }), pageNo, pageSize);
        return pageInfo;
    }

    @RequestMapping("/position/findAll")
    @ResponseBody
    public List<Position> findAll(String departmentCode) {
        Map<String, Object> search = new HashMap<>();
        search.put("departmentCode", departmentCode);
        search.put("status", "启用");
        List<Position> positions = positionService.find(search);
        return positions;
    }

    @PostMapping("/position/save")
    @ResponseBody
    public Position save(@RequestBody Position position) {
        position.setStatus("启用");
        positionService.save(position);
        return position;
    }

    @PostMapping("/position/delete")
    public int delete(@RequestBody Position position) {
        Position exist = positionService.selectByCode(position.getCode());
        exist.setStatus("删除");
        positionService.update(exist);
        return 200;
    }

    @PostMapping("/position/reuse")
    @ResponseBody
    public int reuse(@RequestBody Position position) {
        Position exist = positionService.selectByCode(position.getCode());
        exist.setStatus("启用");
        positionService.update(exist);
        return 200;
    }
}
