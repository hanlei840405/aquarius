package com.galaxy.framework.aquarius.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/resources")
public class ResourceController {

    @RequestMapping("/load/{token}")
    public boolean load(@PathVariable("token") String token) {
        log.info(token);
        return true;
    }
}
