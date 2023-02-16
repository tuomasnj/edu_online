package com.onlineSchool.vodService.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "aliyun.vod.file")
@Component
public class ConstantPropertyUtils implements InitializingBean {
    private String keyid;

    private String keysecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
       ACCESS_KEY_ID = keyid;

       ACCESS_KEY_SECRET = keysecret;
    }
}
