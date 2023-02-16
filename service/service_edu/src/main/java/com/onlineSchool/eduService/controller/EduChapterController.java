package com.onlineSchool.eduService.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.eduService.entity.EduChapter;
import com.onlineSchool.eduService.entity.vo.ChapterVo;
import com.onlineSchool.eduService.service.EduChapterService;
import com.onlineSchool.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
@RestController
@RequestMapping("/eduService/edu-chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    //根据courseId查询课程的章节和小节内容
    public Result getChapterAndVideo(@PathVariable String courseId) {
        List<ChapterVo> lists = eduChapterService.queryChapterAndVideo(courseId);
        Map<String, Object> map = new HashMap<>();
        map.put("allChapterAndVideo", lists);
        return Result.ok().data(map);
    }

    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter) {
        try {
            eduChapterService.save(eduChapter);
        } catch (Exception e) {
            throw new GuliException(20001, "添加章节异常");
        }
        return Result.ok();
    }

    @GetMapping("/getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return Result.ok().data("chapter", eduChapter);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteChapterById(@PathVariable String id) {
        boolean flag = eduChapterService.removeChapterById(id);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("/updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return Result.ok();
    }
}

