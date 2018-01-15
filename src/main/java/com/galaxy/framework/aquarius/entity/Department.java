package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Table(name = "sys_department", schema = "system")
public class Department extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -141438274154966805L;

    private String code;

    private String name;

    private String fullPath;

    private String fullName;

    // 上级部门
    private String parentCode;

    private boolean parent;

    private String locationCode;
}
