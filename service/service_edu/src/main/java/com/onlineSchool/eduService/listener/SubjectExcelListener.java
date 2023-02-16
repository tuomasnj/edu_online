package com.onlineSchool.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlineSchool.eduService.entity.EduSubject;
import com.onlineSchool.eduService.entity.excel.SubjectData;
import com.onlineSchool.eduService.service.EduSubjectService;
import com.onlineSchool.exception.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    private EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public SubjectExcelListener() {
    }

    //一行一行的读取Excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //每次读取一行的两个值：一级分类的值和二级分类的值
        //首先处理一级分类
        //如果一级分类不存在就存入，如果存在就不操作
        EduSubject eduOneSubject = existOneSubject(subjectData.getOneSubjectName());
        if(eduOneSubject == null){
            eduOneSubject = new EduSubject();
            eduOneSubject.setTitle(subjectData.getOneSubjectName());
            eduOneSubject.setParentId("0");
            eduSubjectService.save(eduOneSubject);
        }

        //二级分类，类比一级分类
        EduSubject eduTwoSubject = existTwoSubject(subjectData.getTwoSubjectName(), eduOneSubject.getId());
        if(eduTwoSubject == null){
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduTwoSubject.setParentId(eduOneSubject.getId());
            eduSubjectService.save(eduTwoSubject);
        }
    }

    //判断一级分类是否已经存在
    public EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", 0);
        eduSubjectQueryWrapper.eq("title", name);
        EduSubject oneSubject = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return oneSubject;
    }

    public EduSubject existTwoSubject(String name, String parentId){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", parentId);
        eduSubjectQueryWrapper.eq("title", name);
        EduSubject twoSubject = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
