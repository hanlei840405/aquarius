package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name ="sys_sequence", schema = "system")
@Setter
@Getter
public class Sequence implements Serializable{

    private static final long serialVersionUID = -9108923525489025L;
    private Long id;
    private String category;
    private Long sequence;
    private Long version;
}
