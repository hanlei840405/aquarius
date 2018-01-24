package com.galaxy.framework.aquarius.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model  model) {
        model.addAttribute("name","韩磊");
        return "index";
    }
}
