package com.onlineEdu;

import com.onlineSchool.eduService.EducationApplication;
import com.onlineSchool.eduService.entity.EduSubject;
import com.onlineSchool.eduService.entity.vo.CourseDetailVo;
import com.onlineSchool.eduService.mapper.EduCourseMapper;
import com.onlineSchool.eduService.mapper.EduSubjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = EducationApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class BaseTests {
    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduSubjectMapper eduSubjectMapper;

    @Data
    class King {
        private String name;
        private int age;
    }

    @Test
    public void method() {
        King king = new King();
        System.out.println(king);
    }

    @Test
    public void testTree() {
        //构建树形结构数据
        List<EduSubject> dataList = eduSubjectMapper.selectList(null);
        //根据parentId进行分组
        List<EduSubject> roots = dataList.stream().filter(item -> item.getParentId().equals("0")).collect(Collectors.toList());
        Map<String, List<EduSubject>> map = dataList.stream().collect(Collectors.groupingBy(EduSubject::getParentId));
        recursion(dataList, map);
        System.out.println(roots);
    }

    public void recursion(List<EduSubject> list, Map<String, List<EduSubject>> map) {
        for (EduSubject eduSubject : list) {
            List<EduSubject> eduSubjects = map.get(eduSubject.getId());
            eduSubject.setChildren(eduSubjects);
            if (eduSubjects != null && eduSubjects.size() > 0) {
                recursion(eduSubjects, map);
            }
        }
    }

    @Test
    public void testSelect() {
        CourseDetailVo res = eduCourseMapper.selectCourseDetailById("1622837381641183233");
        log.info(res.getDescription());
    }
    @Test
    public void testDelete(){
        int i = eduCourseMapper.deleteById("1");
        log.info(String.valueOf(i));
    }
}
