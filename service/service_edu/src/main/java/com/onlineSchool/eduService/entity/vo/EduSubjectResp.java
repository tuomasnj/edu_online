package com.onlineSchool.eduService.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class EduSubjectResp {
    private String id;

    private String title;

    private List<EduSubjectResp> children;
}
