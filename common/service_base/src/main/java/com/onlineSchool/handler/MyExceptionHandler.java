package com.onlineSchool.handler;

import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.exception.GuliException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        Result result = Result.error().message("项目发生全局异常，error!");
        return result;
    }

    @ExceptionHandler(GuliException.class)
    public Result exception(GuliException e){
        e.printStackTrace();
        Result result = Result.error().code(e.getCode()).message(e.getMsg());
        return result;
    }
}
