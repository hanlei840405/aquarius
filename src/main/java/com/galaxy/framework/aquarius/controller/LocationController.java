package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.pisces.entity.Location;
import com.galaxy.framework.aquarius.service.LocationService;
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
    @RequestMapping("/findAll")
    public List<Location> findAll() {
        List<Location> locations = locationService.findByStatus("启用");
        return locations;
    }
}
