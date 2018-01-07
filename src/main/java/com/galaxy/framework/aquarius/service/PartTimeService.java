package com.galaxy.framework.aquarius.service;

import com.galaxy.framework.aquarius.entity.PartTime;

public interface PartTimeService extends CrudService<PartTime, Long> {
    void deleteByUserCode(String userCode);
}
