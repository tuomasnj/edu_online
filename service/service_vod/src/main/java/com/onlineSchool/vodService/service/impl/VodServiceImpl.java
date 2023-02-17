package com.onlineSchool.vodService.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.onlineSchool.exception.GuliException;
import com.onlineSchool.vodService.service.VodService;
import com.onlineSchool.vodService.utils.ConstantPropertyUtils;
import com.onlineSchool.vodService.utils.CreateVodClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideos(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertyUtils.ACCESS_KEY_ID,
                    ConstantPropertyUtils.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId))
                    throw new GuliException(20001, errorMessage);
            }
            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    @Override
    public Map<String, Object> searchUrlById(String videoId) {
        DefaultAcsClient defaultAcsClient = null;
        try {
            defaultAcsClient = CreateVodClient.initVodClient(ConstantPropertyUtils.ACCESS_KEY_ID, ConstantPropertyUtils.ACCESS_KEY_SECRET);
        } catch (ClientException e) {
            throw new GuliException(20001,"阿里云服点播服务异常");
        }

        //2.根据视频id获取播放地址的信息，封装成response对象返回
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        GetPlayInfoResponse response = null;
        try {
            response = defaultAcsClient.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001,"无法获取视频");
        }

        //3.从response对象中获取url
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        if(playInfoList.size() > 1){
            throw new GuliException(20001, "返回不止一个视频");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileName", response.getVideoBase().getTitle());
        map.put("url", playInfoList.get(0).getPlayURL());
        return map;
    }

    @Override
    public void deleteVideo(String id) {
        try{
            DefaultAcsClient client = CreateVodClient.initVodClient(ConstantPropertyUtils.ACCESS_KEY_ID,ConstantPropertyUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }
}
