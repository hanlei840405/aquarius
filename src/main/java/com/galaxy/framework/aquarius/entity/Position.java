package com.galaxy.framework.aquarius.entity;

import com.galaxy.framework.pisces.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Position extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 573452572222497588L;

    private String code;

    private String name;

    // 是否是部门负责人关键岗位
    private boolean manager;

    private String departmentCode;

    private String positionCode;

    private Department department;

    private Position position;
}
