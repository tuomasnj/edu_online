package com.onlineSchool.eduService.service;

import com.onlineSchool.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlineSchool.eduService.entity.vo.EduSubjectResp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guo
 * @since 2023-01-31
 */
public interface EduSubjectService extends IService<EduSubject> {
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<EduSubjectResp> listAllSubject();

    List<EduSubject> querySubject(String subjectId);
}
