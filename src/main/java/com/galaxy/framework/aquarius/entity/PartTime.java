package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Table(name = "sys_part_time", schema = "system")
public class PartTime extends BaseEntity implements Serializable {

    private String positionCode;

    private String userCode;
}
