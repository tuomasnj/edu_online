package com.onlineSchool.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlineSchool.eduService.entity.EduSubject;
import com.onlineSchool.eduService.entity.excel.SubjectData;
import com.onlineSchool.eduService.entity.vo.EduSubjectResp;
import com.onlineSchool.eduService.listener.SubjectExcelListener;
import com.onlineSchool.eduService.mapper.EduSubjectMapper;
import com.onlineSchool.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author guo
 * @since 2023-01-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    EduSubjectMapper eduSubjectMapper;

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        InputStream ins;
        try{
            ins =file.getInputStream();
        }catch (Exception exception){
            throw new RuntimeException();
        }
        EasyExcel.read(ins, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
    }

    @Override
    public List<EduSubjectResp> listAllSubject() {
        //储存结果的集合
        List<EduSubjectResp> resp = new ArrayList<>();

        //分别查询一级和二级课程信息
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", "0");
        List<EduSubject> eduOneSubjects = baseMapper.selectList(eduSubjectQueryWrapper);
        eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.ne("parent_id", "0");
        List<EduSubject> eduTwoSubjects = baseMapper.selectList(eduSubjectQueryWrapper);

        //将EduSubject对象封装成EduSubjectResp对象
        EduSubjectResp eduOneSubject;
        EduSubjectResp eduTwoSubject;
        List<EduSubjectResp> children;
        for(EduSubject item : eduOneSubjects){
            eduOneSubject = new EduSubjectResp();
            eduOneSubject.setId(item.getId());
            eduOneSubject.setTitle(item.getTitle());
            children = null;
            for(EduSubject child : eduTwoSubjects){
                if(child.getParentId().equals(eduOneSubject.getId())){
                    if (children == null) {
                        children = new ArrayList<>();
                    }
                    eduTwoSubject = new EduSubjectResp();
                    eduTwoSubject.setId(child.getId());
                    eduTwoSubject.setTitle(child.getTitle());
                    children.add(eduTwoSubject);
                }
            }
            eduOneSubject.setChildren(children);
            resp.add(eduOneSubject);
        }
        return resp;
    }

    @Override
    public List<EduSubject> querySubject(String subjectId) {
        EduSubject eduSubject = eduSubjectMapper.selectById(subjectId);
        String parentId = eduSubject.getParentId();
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<EduSubject> eduSubjects = eduSubjectMapper.selectList(wrapper);
        return eduSubjects;
    }
}
