package com.yuchang.sdkhub.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SDK 当前版本信息 VO
 * 用于返回指定 SDK 的当前使用版本信息
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdkCurrentVersionVO {

    /**
     * SDK ID
     */
    private String sdkId;

    /**
     * 当前版本号
     */
    private String version;

    /**
     * 是否已安装
     */
    private Boolean installed;
}
