package com.yuchang.sdkhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.yuchang.sdkhub.config.SdkHubProperties;

/**
 * SdkHub 应用程序入口
 * 启用配置属性支持
 *
 * @author SdkHub Team
 * @version 0.1.0
 */
@SpringBootApplication
@EnableConfigurationProperties(SdkHubProperties.class)
public class SdkHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdkHubApplication.class, args);
    }

}
