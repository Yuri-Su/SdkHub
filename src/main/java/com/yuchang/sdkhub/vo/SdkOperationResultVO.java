package com.yuchang.sdkhub.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SDK 操作结果 VO
 * 用于返回 SDK 安装、卸载、切换等操作的结果
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdkOperationResultVO {

    /**
     * 操作是否成功
     */
    private Boolean success;

    /**
     * 操作结果消息
     */
    private String message;

    /**
     * SDK ID
     */
    private String sdkId;

    /**
     * 版本号
     */
    private String version;
}
