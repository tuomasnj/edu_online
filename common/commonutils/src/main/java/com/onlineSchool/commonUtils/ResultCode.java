package com.onlineSchool.commonUtils;

public enum ResultCode {
    OK(20000,"SUCCESS"),
    ERROR(20001,"FAILED");
    private Integer code;
    private String message;
    private ResultCode(Integer code,String message){
        this.code =code;
        this.message =message;
    }

    public int getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
