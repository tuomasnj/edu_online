package com.onlineSchool.eduService.controller;

import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.eduService.entity.EduChapter;
import com.onlineSchool.eduService.entity.EduSubject;
import com.onlineSchool.eduService.entity.vo.EduSubjectResp;
import com.onlineSchool.eduService.service.EduChapterService;
import com.onlineSchool.eduService.service.EduSubjectService;
import com.onlineSchool.eduService.service.EduTeacherService;
import com.onlineSchool.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author guo
 * @since 2023-01-31
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/edu-subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    public Result addSubject(@RequestParam("file") MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return Result.ok();
    }

    @GetMapping("/getAllSubject")
    public Result getAllSubject() {
        List<EduSubjectResp> lists = eduSubjectService.listAllSubject();
        return Result.ok().data("list", lists);
    }

    @GetMapping("/getSubjectById/{subjectId}")
    //根据二级学科Id查询所有的同类二级学科信息
    public Result getSubjectsById(@PathVariable String subjectId) {
        List<EduSubject> eduSubjects = eduSubjectService.querySubject(subjectId);
        return Result.ok().data("result", eduSubjects);
    }
}

