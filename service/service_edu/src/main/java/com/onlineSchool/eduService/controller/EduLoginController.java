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
        return Result.ok().data("roles",new String[]{"editor"}).data("name","editor").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
