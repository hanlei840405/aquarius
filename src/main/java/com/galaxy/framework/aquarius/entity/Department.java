package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Department extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -141438274154966805L;

    private String code;

    private String name;

    private String fullPath;

    private String fullName;

    private boolean parent;

    private String locationCode;

    private String departmentCode;

    private Location location;

    private Department department;
}
