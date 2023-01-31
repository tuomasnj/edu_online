package com.onlineSchool.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.onlineSchool.serviceBase", "com.onlineSchool.eduService", "com.onlineSchool.handler"})
public class EducationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducationApplication.class,args);
    }
}
