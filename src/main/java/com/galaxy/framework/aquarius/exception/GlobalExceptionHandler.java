package com.galaxy.framework.aquarius.exception;

import com.galaxy.framework.pisces.exception.GlobalException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class GlobalExceptionHandler {
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public ExceptionInfo jsonExceptionHandler(GlobalException e) {
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setCode(e.code);
        exceptionInfo.setMessage(e.message);
        return exceptionInfo;
    }
}
