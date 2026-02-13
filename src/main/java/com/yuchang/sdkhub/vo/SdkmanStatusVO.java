package com.yuchang.sdkhub.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SDKMAN 状态 VO
 * 用于返回 SDKMAN 的安装状态信息
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdkmanStatusVO {

    /**
     * 是否已安装
     */
    private Boolean installed;

    /**
     * 安装路径
     */
    private String path;

    /**
     * 状态消息
     */
    private String message;
}
