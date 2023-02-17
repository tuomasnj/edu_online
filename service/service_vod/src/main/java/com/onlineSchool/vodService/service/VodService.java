package com.onlineSchool.vodService.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VodService {
    String uploadVideos(MultipartFile file);

    Map<String,Object> searchUrlById(String videoId);

    void deleteVideo(String id);
}
