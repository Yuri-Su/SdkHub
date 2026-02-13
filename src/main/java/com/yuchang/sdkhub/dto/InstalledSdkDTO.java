package com.yuchang.sdkhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 已安装 SDK 信息 DTO
 * 封装已安装 SDK 的详细信息，包括版本、安装路径等
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstalledSdkDTO {

    /**
     * SDK ID
     */
    private String id;

    /**
     * SDK 名称
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 是否为当前使用版本
     */
    private Boolean current;
}
