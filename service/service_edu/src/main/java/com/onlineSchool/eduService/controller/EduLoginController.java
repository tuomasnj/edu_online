package com.onlineSchool.eduService.controller;

import com.onlineSchool.commonUtils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin
public class EduLoginController {
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","editor-token");
    }

    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("roles",new String[]{"editor"}).data("name","editor").data("avatar","https://education-online0101.oss-cn-nanjing.aliyuncs." +
                "com/2023/01/30/715077a104b842b6a57a3c594eba8e8dgirl01.jpg");
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
