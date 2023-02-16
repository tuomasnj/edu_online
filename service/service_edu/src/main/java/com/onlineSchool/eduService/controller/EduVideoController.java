package com.onlineSchool.eduService.controller;


import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.eduService.entity.EduVideo;
import com.onlineSchool.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author guo
 * @since 2023-02-04
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/edu-video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return Result.ok();
    }

    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id){
        eduVideoService.removeById(id);
        return Result.ok();
    }

    @GetMapping("/getVideo/{videoId}")
    public Result getVideo(@PathVariable String videoId){
        EduVideo video = eduVideoService.getById(videoId);
        return Result.ok().data("video", video);
    }
}

