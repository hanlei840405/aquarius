package com.galaxy.framework.aquarius.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.framework.aquarius.entity.User;
import com.galaxy.framework.aquarius.service.UserService;
import com.galaxy.framework.pisces.exception.db.NotExistException;
import com.galaxy.framework.pisces.exception.rule.EmptyException;
import com.galaxy.framework.pisces.util.FileUtil;
import com.galaxy.framework.pisces.vo.aquarius.UserVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public List<User> findAll(String departmentCode, String positionCode, String status) {
        Map<String, Object> search = Maps.newHashMap();
        search.put("departmentCode", departmentCode);
        search.put("positionCode", positionCode);
        search.put("status", status);
        List<User> users = userService.find(search);
        return users;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/page")
    public PageInfo<User> page(String search, Integer pageNo, Integer pageSize) throws IOException {
        PageInfo<User> pageInfo = userService.page(objectMapper.readValue(search, new TypeReference<Map<String, Object>>() {
        }), pageNo, pageSize);
        return pageInfo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/getByCode")
    public UserVo getByCode(String code) {
        User user = userService.selectByCode(code);
        UserVo userVo = new UserVo();
        userVo.setCode(user.getCode());
        userVo.setName(user.getName());
        return userVo;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public User save(@RequestBody User user) {
        user.setStatus("启用");
        return userService.save(user);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/upload")
    public int upload(@RequestBody Map<String, String> userInfo) throws IOException {
        String headImg = userInfo.get(("headImg"));

        String code = userInfo.get(("code"));
        if (StringUtils.isEmpty(headImg)) {
            throw new EmptyException("图片为空");
        } else if (StringUtils.isEmpty(code)) {
            throw new EmptyException("图片所属人为空");
        } else {
            String flieName = FileUtil.writeFromBase64(headImg, code);
            User user = userService.selectByCode(code);
            user.setHeadImg(flieName);
            userService.update(user);
        }
        return 200;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reuse")
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
