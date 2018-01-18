package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User selectByCode(String code);

    List<User> find(User user);

    User insert(User user);

    void insert(List<User> users);

    User update(User user);

    void update(List<User> users);

    void delete(List<Long> vars);

    User save(User user);

    void deleteByCode(List<String> codes);
}
