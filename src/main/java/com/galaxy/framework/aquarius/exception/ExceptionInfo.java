package com.galaxy.framework.aquarius.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ExceptionInfo implements Serializable {
    private static final long serialVersionUID = -3399631464627427166L;

    private String message;

    private String code;
}
