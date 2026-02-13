package com.yuchang.sdkhub.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SDK 版本信息 VO
 * 用于返回 SDK 版本的详细信息
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdkVersionVO {

    /**
     * SDK 提供者
     */
    private String vendor;

    /**
     * 是否当前使用
     */
    private Boolean used;

    /**
     * 版本号
     */
    private String version;

    /**
     * 发行版标识（如 zulu, amzn）
     */
    private String dist;

    /**
     * 状态（local only, installed 等）
     */
    private String status;

    /**
     * 完整标识符
     */
    private String identifier;
}
