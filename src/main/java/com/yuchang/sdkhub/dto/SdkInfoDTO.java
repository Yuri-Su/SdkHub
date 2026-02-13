package com.yuchang.sdkhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SDK 信息 DTO
 * 封装 SDK 的基本信息，包括 ID、名称、供应商、描述等
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdkInfoDTO {

    /**
     * SDK ID
     */
    private String id;

    /**
     * SDK 名称
     */
    private String name;

    /**
     * 供应商
     */
    private String vendor;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 分类
     */
    private String category;

    /**
     * 官方网站
     */
    private String website;

    /**
     * 最新版本
     */
    private String latestVersion;
}
