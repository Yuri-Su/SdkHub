package com.yuchang.sdkhub.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SdkHub 配置属性类
 * 用于读取 application.yaml 中的配置
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@ConfigurationProperties(prefix = "sdkhub")
@Data
public class SdkHubProperties {

    /**
     * SDK 安装路径
     */
    private String sdkPath = "~/.sdkhub";

    /**
     * 缓存路径
     */
    private String cachePath = "~/.sdkhub/cache";

    /**
     * 是否自动检查更新
     */
    private boolean autoCheckUpdate = true;

}
