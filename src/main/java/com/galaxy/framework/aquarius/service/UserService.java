package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.User;

import java.util.List;

public interface UserService extends CrudService<User, Long> {

    User selectByCode(String code, String status);

    User save(User user);

    void deleteByCode(List<String> codes);
}
