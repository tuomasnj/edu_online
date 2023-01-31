package com.onlineSchool.handler;

import com.onlineSchool.commonUtils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result exception(){
        Result result = Result.error().message("项目发生全局异常，error!");
        return result;
    }
}
