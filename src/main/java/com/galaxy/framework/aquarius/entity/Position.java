package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Table(name = "sys_position", schema = "system")
public class Position extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 573452572222497588L;

    private String code;

    private String name;

    // 上级部门
    private String parentCode;

    // 岗位所在部门
    private String departmentCode;

    // 是否是部门负责人关键岗位
    private boolean manager;
}
