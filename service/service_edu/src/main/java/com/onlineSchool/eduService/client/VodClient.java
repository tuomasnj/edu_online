package com.onlineSchool.eduService.client;

import com.onlineSchool.commonUtils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-vod")
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAliyunVideo/{id}")
    Result removeVodById(@PathVariable("id") String id);
}
