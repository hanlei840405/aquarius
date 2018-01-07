package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class BaseEntity implements Serializable{

    private static final long serialVersionUID = -3693794039597112865L;

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String status;

    private String creator;

    private Date created;

    private String modifier;

    private Date modified;

    private int version = 0;
}
