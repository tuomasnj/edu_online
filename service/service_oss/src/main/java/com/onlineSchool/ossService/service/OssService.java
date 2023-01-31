package com.onlineSchool.ossService.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String upLoadFileAvatar(MultipartFile multipartFile);
}
