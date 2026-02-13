package com.yuchang.sdkhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SDK 版本信息 DTO
 * 封装 SDK 版本的详细信息，包括版本号、安装状态等
 * ================================================================================
 * Available Java Versions for macOS ARM 64bit
 * ================================================================================
 * Vendor        | Use | Version      | Dist    | Status     | Identifier
 * --------------------------------------------------------------------------------
 * Corretto      |     | 25.0.2       | amzn    |            | 25.0.2-amzn
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdkVersionDTO {

    /**
     * SDK 提供者
     */
    private String vendor;

    /**
     * 使用标记 >>> 表示当前版本）
     */
    private String use;

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
