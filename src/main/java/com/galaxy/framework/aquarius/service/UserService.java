package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends CrudService<User, Long> {

    User selectByCode(String code, String status);

    User save(User user);

    void deleteByCode(List<String> codes);

    List<User> selectByDepartment(Map<String, Object> params);

    List<User> selectByPosition(Map<String, Object> params);
}
