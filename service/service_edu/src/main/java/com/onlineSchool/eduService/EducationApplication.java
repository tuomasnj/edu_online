package com.onlineSchool.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.onlineSchool")
public class EducationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducationApplication.class,args);
    }
}
