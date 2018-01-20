package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    User selectByCode(String code);

    List<User> find(Map<String, Object> search);

    User insert(User user);

    void insert(List<User> users);

    User update(User user);

    void update(List<User> users);

    void delete(List<Long> vars);

    User save(User user);

    void deleteByCode(List<String> codes);

    PageInfo<User> page(Map<String, Object> search, Integer pageNo, Integer pageSize);
}
