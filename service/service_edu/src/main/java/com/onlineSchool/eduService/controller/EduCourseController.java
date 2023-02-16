package com.onlineSchool.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.eduService.entity.EduCourse;
import com.onlineSchool.eduService.entity.EduTeacher;
import com.onlineSchool.eduService.entity.vo.CourseDetailVo;
import com.onlineSchool.eduService.entity.vo.CourseInfoVo;
import com.onlineSchool.eduService.entity.vo.CourseQuery;
import com.onlineSchool.eduService.entity.vo.TeacherQuery;
import com.onlineSchool.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/edu-course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    //添加课程基本信息
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId", id);
    }

    //根据课程ID查询课程信息
    @GetMapping("/getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo res = eduCourseService.queryCourseById(courseId);
        return Result.ok().data("course", res);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourse(courseInfoVo);
        return Result.ok();
    }

    //查询课程的详细信息(多表查询)
    @GetMapping("/queryCourseDetailInfo/{courseId}")
    public Result getCourseDetailInfo(@PathVariable String courseId) {
        CourseDetailVo courseDetail = eduCourseService.getCourseDetail(courseId);
        return Result.ok().data("course", courseDetail);
    }

    //修改课程状态完成课程发布
    @PostMapping("/publishCourse/{id}")
    public Result publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

    //查询所有的课程信息
    @GetMapping("/getCourseList")
    public Result getCourseList() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        List<EduCourse> list = eduCourseService.list(queryWrapper);
        return Result.ok().data("courseList", list);
    }

    /*分页查询*/
    @GetMapping("/pageCourse/{current}/{recordNum}")
    public Result getCourseByPage(@PathVariable long current, long recordNum) {
        Page<EduCourse> coursePage = new Page<>(current, recordNum);
        eduCourseService.page(coursePage, null);
        Result result = Result.ok();
        result.data("total", coursePage.getTotal());
        result.data("rows", coursePage.getRecords());
        return result;
    }

    /*条件查询 + 分页查询*/
    @PostMapping("/pageCourse/{current}/{recordNum}")
    public Result pageSelectCondition(@PathVariable long current, @PathVariable long recordNum, @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        Page<EduCourse> coursePage = new Page<>(current, recordNum);

        //构建条件
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        if (courseQuery != null) {
            String title = courseQuery.getTitle();
            String status = courseQuery.getStatus();
            if (!StringUtils.isEmpty(title)) {
                queryWrapper.like("title", title);
            }
            if (!StringUtils.isEmpty(status)) {
                queryWrapper.eq("status", status);
            }
        }

        //查询
        eduCourseService.page(coursePage, queryWrapper);

        //返回结果
        long total = coursePage.getTotal();
        List<EduCourse> list = coursePage.getRecords();
        return Result.ok().data("total", total).data("rows", list);
    }

    //删除课程
    @DeleteMapping("/deleteCourse/{id}")
    public Result deleteCourse(@PathVariable String id){
        eduCourseService.deleteCourseById(id);
        return Result.ok();
    }
}

