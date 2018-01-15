package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by hanlei6 on 2018/1/15.
 */
@Setter
@Getter
@Table(name = "sys_location", schema = "system")
public class Location extends BaseEntity implements Serializable {

    private String code;

    private String name;
}
