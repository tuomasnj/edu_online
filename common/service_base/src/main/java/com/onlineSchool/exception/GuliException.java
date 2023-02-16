package com.onlineSchool.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{
    private Integer code;//状态码

    private String Msg;//异常信息
}
