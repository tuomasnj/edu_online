package com.onlineSchool.ossService.controller;


import com.onlineSchool.commonUtils.Result;
import com.onlineSchool.ossService.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String url = ossService.upLoadFileAvatar(multipartFile);
        return Result.ok().data("url", url);
    }
}
