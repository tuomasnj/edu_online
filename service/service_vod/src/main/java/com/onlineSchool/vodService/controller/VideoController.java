package com.onlineSchool.vodService.controller;

import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.vodService.service.VodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
@Slf4j
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

    @PostMapping("/url/{id}")
    public Result getUrlByVideoId(@PathVariable String id){
        Map<String, Object> hashmap = vodService.searchUrlById(id);
        return Result.ok().data(hashmap);
    }

    @DeleteMapping("/removeAliyunVideo/{id}")
    public Result deleteAliyunVideo(@PathVariable String id){
        vodService.deleteVideo(id);
        return Result.ok();
    }

}
