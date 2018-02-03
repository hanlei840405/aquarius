package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Location;
import com.galaxy.framework.aquarius.service.LocationService;
import com.galaxy.framework.pisces.vo.aquarius.LocationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/get")
    public LocationVo get(String code, String status) {
        Location location = locationService.selectByCode(code, status);
        LocationVo vo = new LocationVo();
        vo.setCode(location.getCode());
        vo.setName(location.getName());
        return vo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public List<Location> findAll() {
        List<Location> locations = locationService.findByStatus("启用");
        return locations;
    }
}
