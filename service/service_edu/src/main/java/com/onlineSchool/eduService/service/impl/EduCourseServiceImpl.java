package com.onlineSchool.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlineSchool.eduService.entity.EduChapter;
import com.onlineSchool.eduService.entity.EduCourse;
import com.onlineSchool.eduService.entity.EduCourseDescription;
import com.onlineSchool.eduService.entity.EduVideo;
import com.onlineSchool.eduService.entity.vo.CourseDetailVo;
import com.onlineSchool.eduService.entity.vo.CourseInfoVo;
import com.onlineSchool.eduService.mapper.EduChapterMapper;
import com.onlineSchool.eduService.mapper.EduCourseDescriptionMapper;
import com.onlineSchool.eduService.mapper.EduCourseMapper;
import com.onlineSchool.eduService.mapper.EduVideoMapper;
import com.onlineSchool.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlineSchool.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseMapper courseMapper;

    @Autowired
    EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Autowired
    EduChapterMapper eduChapterMapper;

    @Autowired
    EduVideoMapper eduVideoMapper;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = courseMapper.insert(eduCourse);
        if(insert == 0){
            throw new GuliException(20001, "课程信息添加失败");
        }

        //向课程简介表添加信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        int insert1 = eduCourseDescriptionMapper.insert(eduCourseDescription);
        if(insert1 == 0){
            throw new GuliException(20001, "课程信息添加失败");
        }
        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo queryCourseById(String courseId) {
        EduCourse eduCourse = courseMapper.selectById(courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int ans1 = courseMapper.updateById(eduCourse);
        if(ans1 == 0){
            throw new GuliException(20001, "修改课程信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        int ans2 = eduCourseDescriptionMapper.updateById(eduCourseDescription);
        if(ans2 == 0){
            throw new GuliException(20001, "修改课程信息失败");
        }
    }

    @Override
    public CourseDetailVo getCourseDetail(String courseId) {
        if(courseId == null){
            throw new GuliException(20001,"id参数不能为null");
        }
        return courseMapper.selectCourseDetailById(courseId);
    }

    @Override
    @Transactional
    public void deleteCourseById(String courseId) {
        int update = courseMapper.deleteById(courseId);
        if(update == 0){
            throw new GuliException(20001, "删除课程记录异常");
        }
        eduVideoMapper.delete(new QueryWrapper<EduVideo>().eq("course_id", courseId));
        eduChapterMapper.delete(new QueryWrapper<EduChapter>().eq("course_id", courseId));
        eduCourseDescriptionMapper.deleteById(courseId);
    }
}
