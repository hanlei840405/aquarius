package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Table(name = "sys_user", schema = "system")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6290826770540342881L;

    private String code;

    private String name;

    private String email;

    private String mobile;

    private String gender;

    private String birthday;

    // 入职日期
    private String entryDay;

    // 转正日期
    private String regularDay;

    // 离职日期
    private String leaveDay;

    private String departmentCode;

    private String positionCode;

    private String headImg;
}
