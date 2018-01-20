package com.galaxy.framework.aquarius.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.framework.aquarius.entity.User;
import com.galaxy.framework.aquarius.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/page")
    public PageInfo<User> page(String search, Integer pageNo, Integer pageSize) throws IOException {
        PageInfo<User> pageInfo = userService.page(objectMapper.readValue(search, new TypeReference<Map<String, Object>>() {
        }), pageNo, pageSize);
        return pageInfo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/getByCode")
    public User getByCode(String code) {
        return userService.selectByCode(code);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public int delete(@RequestBody User user) {
        User exist = userService.selectByCode(user.getCode());
        exist.setStatus("删除");
        userService.update(exist);
        return 200;
    }
}
