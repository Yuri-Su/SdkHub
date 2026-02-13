package com.yuchang.sdkhub.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配置信息 VO
 * 用于返回和接收 SdkHubProperties 的配置信息
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigVO {

    /**
     * SDK 安装路径
     */
    private String sdkPath;

    /**
     * 是否自动检查更新
     */
    private boolean autoCheckUpdate;
}
