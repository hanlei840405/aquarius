package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Location;
import com.galaxy.framework.aquarius.service.LocationService;
import com.galaxy.framework.pisces.vo.aquarius.LocationVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public PageInfo<LocationVo> findAll(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Location> locations = locationService.selectAll();
        List<LocationVo> page = locations.stream().map(location -> convert(location)).collect(Collectors.toList());
        PageInfo<LocationVo> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public LocationVo save(@RequestBody LocationVo locationVo) {
        Location location = convert(locationVo);
        locationService.save(location);
        return locationVo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public int delete(@RequestBody List<String> codes) {
        locationService.deleteByCode(codes);
        return 200;
    }

    private LocationVo convert(Location location) {
        LocationVo vo = new LocationVo();
        if (location != null) {
            BeanUtils.copyProperties(location, vo, "id");
        }
        return vo;
    }

    private Location convert(LocationVo vo) {
        Location location = new Location();
        if (vo != null) {
            BeanUtils.copyProperties(vo, location);
        }
        return location;
    }
}
