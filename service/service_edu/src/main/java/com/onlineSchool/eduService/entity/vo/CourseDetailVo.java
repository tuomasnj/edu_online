package com.onlineSchool.eduService.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseDetailVo {
    private String id;

    private String title;

    private String description;

    private Integer lessonNum;

    private BigDecimal price;

    private String teacherName;

    private String subjectId;

    private String subjectTitle;

    private String parentId;

    private String parentTitle;

    private String cover;
}
