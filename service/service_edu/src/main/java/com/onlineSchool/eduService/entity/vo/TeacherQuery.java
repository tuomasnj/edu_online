package com.onlineSchool.eduService.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherQuery {
    private String name;

    private Integer level;

    private String begin;

    private String end;
}
