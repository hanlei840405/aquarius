package com.galaxy.framework.aquarius.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Sequence implements Serializable {

    private static final long serialVersionUID = -9108923525489025L;
    private Long id;
    private String category;
    private Long sequence;
    private Long version;
}
