package com.galaxy.framework.aquarius.mapper;

import com.galaxy.framework.aquarius.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User> {

    List<User> selectByDepartment(Map<String, Object> params);

    List<User> selectByPosition(Map<String, Object> params);
}
