package com.onlineSchool.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.eduService.entity.EduTeacher;
import com.onlineSchool.eduService.entity.vo.TeacherQuery;
import com.onlineSchool.eduService.service.EduTeacherService;
import com.onlineSchool.eduService.service.impl.EduTeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author guo
 * @since 2022-12-23
 */
@RestController
@RequestMapping("/eduService/edu-teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherServiceImpl teacherService;

    @GetMapping("/findAll")
    public Result findAllTeachers(){
        List<EduTeacher> eduTeachers = teacherService.list(new QueryWrapper<>());
        return Result.ok().data("items",eduTeachers);
    }

    @DeleteMapping("/delete/{id}")
    public Result removeTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        if(eduTeacher == null){
            return Result.error();
        }else {
            teacherService.removeById(id);
            return Result.ok();
        }
    }

    /*分页查询接口*/
    @GetMapping("/pageTeacher/{current}/{recordNum}")
    public Result pageSelectTeacher(@PathVariable Long current, @PathVariable Long recordNum){
        Page<EduTeacher> pageTeacher =new Page<>(current, recordNum);
        teacherService.page(pageTeacher, null);
        Result result =Result.ok();
        result.data("total",pageTeacher.getTotal());//总记录数
        result.data("rows",pageTeacher.getRecords());//当前页显示的行数
        return result;
    }

    /*条件查询+分页*/
    @PostMapping("/pageConditionTeacher/{current}/{recordNum}")
    public Result pageSelectCondition(@PathVariable Long current, @PathVariable Long recordNum, @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current,recordNum);

        //构建条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        if(teacherQuery != null){
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();
            if(!StringUtils.isEmpty(name)){
                queryWrapper.like("name",name);
            }
            if(!StringUtils.isEmpty(level)){
                queryWrapper.eq("level",level);
            }
            if(!StringUtils.isEmpty(begin)){
                queryWrapper.ge("gmt_create",begin);
            }
            if(!StringUtils.isEmpty(end)){
                queryWrapper.le("gmt_create",end);
            }
        }

        //查询
        teacherService.page(eduTeacherPage, queryWrapper);

        //返回结果
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> list = eduTeacherPage.getRecords();
        return Result.ok().data("total",total).data("rows",list);
    }

    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getTeacher/{id}")
    public Result findTeacherById(@PathVariable String id){
        EduTeacher teacherById = teacherService.getById(id);
        HashMap<String, Object> map =new HashMap();
        map.put("teacher", teacherById);
        return Result.ok().data(map);
    }

    @PostMapping("/update/{id}")
    public Result update(@PathVariable String id ,@RequestBody EduTeacher teacher){
        teacher.setId(id);
        boolean ans = teacherService.updateById(teacher);
        if(ans){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

