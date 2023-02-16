package com.onlineSchool.vodService.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVideos(MultipartFile file);
}
