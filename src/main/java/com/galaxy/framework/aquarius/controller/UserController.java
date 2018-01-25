package com.galaxy.framework.aquarius.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.framework.aquarius.service.UserService;
import com.galaxy.framework.pisces.entity.User;
import com.galaxy.framework.pisces.exception.db.NotExistException;
import com.galaxy.framework.pisces.exception.rule.EmptyException;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/user")
    public String index() {
        return "user";
    }

    @RequestMapping("/user/page")
    @ResponseBody
    public PageInfo<User> page(String search, Integer pageNo, Integer pageSize) throws IOException {
        PageInfo<User> pageInfo = userService.page(objectMapper.readValue(search, new TypeReference<Map<String, Object>>() {
        }), pageNo, pageSize);
        return pageInfo;
    }

    @RequestMapping("/user/getByCode")
    @ResponseBody
    public User getByCode(String code) {
        return userService.selectByCode(code);
    }

    @PostMapping("/user/save")
    @ResponseBody
    public User save(@RequestBody User user) {
        user.setStatus("启用");
        return userService.save(user);
    }

    @PostMapping("/user/upload")
    @ResponseBody
    public int upload(@RequestBody Map<String, String> userInfo) throws IOException {
        String headImg = userInfo.get(("headImg"));

        String code = userInfo.get(("code"));
        if (StringUtils.isEmpty(headImg)) {
            throw new EmptyException("图片为空");
        } else if (StringUtils.isEmpty(code)) {
            throw new EmptyException("图片所属人为空");
        } else {
            String flieName = "";
            User user = userService.selectByCode(code);
            user.setHeadImg(flieName);
            userService.update(user);
        }
        return 200;
    }

    @PostMapping("/user/delete")
    @ResponseBody
    public int delete(@RequestBody User user) {
        User exist = userService.selectByCode(user.getCode());
        if (exist != null) {
            exist.setStatus("删除");
            userService.update(exist);
            return 200;
        } else {
            throw new NotExistException("查询的员工不存在");
        }
    }

    @PostMapping("/user/reuse")
    @ResponseBody
    public int reuse(@RequestBody User user) {
        User exist = userService.selectByCode(user.getCode());
        if (exist != null) {
            exist.setStatus("启用");
            userService.update(exist);
            return 200;
        } else {
            throw new NotExistException("查询的员工不存在");
        }
    }
}
