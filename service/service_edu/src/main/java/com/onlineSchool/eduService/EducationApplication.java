package com.onlineSchool.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.onlineSchool")
@EnableDiscoveryClient
@EnableFeignClients
public class EducationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducationApplication.class,args);
    }
}
