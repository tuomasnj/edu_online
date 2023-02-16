package com.onlineSchool.vodService.controller;

import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.vodService.service.VodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VideoController {
    @Autowired
    VodService vodService;
    @GetMapping
    public Result dd(){
        return Result.ok();
    }

    //上传视频到阿里云
    @PostMapping("/upload")
    public Result uploadVideo(@RequestParam("file") MultipartFile file){
        String id = vodService.uploadVideos(file);
        return Result.ok().data("id", id);
    }
}
