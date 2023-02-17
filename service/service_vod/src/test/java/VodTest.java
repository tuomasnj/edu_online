import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.onlineSchool.vodService.VodApplication;
import com.onlineSchool.vodService.utils.CreateVodClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest(classes = VodApplication.class)
@RunWith(SpringRunner.class)
public class VodTest {
    @Test
    public void testVod() throws ClientException {
        //1.创建vod客户端
        DefaultAcsClient defaultAcsClient = CreateVodClient.initVodClient("LTAI5tReEtwic7ZVUiZJEsMV", "Wf6NGIoDhaUwSr43Amcte7t4fUJRsC");

        //2.根据视频id获取播放地址的信息，封装成response对象返回
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("f4b8e9d0add271edbfcd0675a0ec0102");
        GetPlayInfoResponse response = defaultAcsClient.getAcsResponse(request);

        //3.从response对象中获取url
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    @Test
    //测试视频上传功能
    public void testUpload() throws ClientException {
        String key = "LTAI5tReEtwic7ZVUiZJEsMV";
        String secret = "Wf6NGIoDhaUwSr43Amcte7t4fUJRsC";
        String title = "实况足球";
        String fileName = "E:\\视频\\bilibil\\202302012138.mp4";
        UploadVideoRequest request = new UploadVideoRequest(key, secret, title, fileName);
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId = " + response.getRequestId() + "\n");
        if(response.isSuccess()){
            System.out.print("VideoId = " + response.getVideoId() + "\n");
        }else {
            System.out.print("VideoId = " + response.getVideoId() + "\n");
            System.out.println("ErrorCode = " + response.getCode() + "\n");
            System.out.println("ErrorMessage = " + response.getMessage() + "\n");
        }

    }
}
