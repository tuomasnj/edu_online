package com.onlineSchool.ossService.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.onlineSchool.ossService.service.OssService;
import com.onlineSchool.ossService.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String upLoadFileAvatar(MultipartFile multipartFile) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        //创建ossclient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //防止文件名称重复
        String filename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        filename = uuid + filename;

        //文件按照日期分类
        String datePath = new DateTime().toString("yyyy/MM/dd")+"/";
        filename = datePath + filename;
        try {
            ossClient.putObject(bucketName, filename, new ByteArrayInputStream(multipartFile.getBytes()));
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
