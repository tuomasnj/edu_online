package com.onlineSchool.eduService.service;

import com.onlineSchool.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlineSchool.eduService.entity.vo.CourseDetailVo;
import com.onlineSchool.eduService.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo queryCourseById(String courseId);

    void updateCourse(CourseInfoVo courseInfoVo);

    CourseDetailVo getCourseDetail(String courseId);

    void deleteCourseById(String courseId);
}
