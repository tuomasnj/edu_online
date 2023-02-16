package com.onlineSchool.eduService.mapper;

import com.onlineSchool.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlineSchool.eduService.entity.vo.CourseDetailVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CourseDetailVo selectCourseDetailById(String courseId);
}
