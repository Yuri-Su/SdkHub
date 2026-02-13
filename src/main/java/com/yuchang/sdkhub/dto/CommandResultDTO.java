package com.yuchang.sdkhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 命令执行结果 DTO
 * 封装命令执行的输出、错误信息和执行状态
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandResultDTO {

    /**
     * 命令是否执行成功
     */
    private Boolean success;

    /**
     * 命令标准输出
     */
    private String output;

    /**
     * 命令错误输出
     */
    private String error;

}
