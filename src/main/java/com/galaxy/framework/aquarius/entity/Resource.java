package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Table(name = "sys_resource", schema = "system")
public class Resource extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5388446520597202753L;

    private String code;

    private String name;

    // 上级资源
    private String parentCode;
}
