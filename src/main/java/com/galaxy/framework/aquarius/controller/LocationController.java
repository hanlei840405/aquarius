package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.service.LocationService;
import com.galaxy.framework.pisces.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    @RequestMapping("/location")
    public String index(Model model) {
        List<Location> locations = locationService.findByStatus("启用");
        model.addAttribute("locations", locations);
        return "system/location";
    }

    @RequestMapping("/location/findAll")
    @ResponseBody
    public List<Location> findAll() {
        List<Location> locations = locationService.findByStatus("启用");
        return locations;
    }
}
