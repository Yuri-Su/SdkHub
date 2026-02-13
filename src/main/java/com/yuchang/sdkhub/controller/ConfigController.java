package com.yuchang.sdkhub.controller;

import com.yuchang.sdkhub.config.SdkHubProperties;
import com.yuchang.sdkhub.vo.ConfigVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 配置管理控制器
 * 提供 SdkHubProperties 配置的查询和动态更新接口
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
@Slf4j
public class ConfigController {

    private final SdkHubProperties sdkHubProperties;

    /**
     * 获取所有配置项
     *
     * @return 配置信息
     */
    @GetMapping
    public ResponseEntity<ConfigVO> getConfig() {
        ConfigVO configVO = ConfigVO.builder()
                .sdkPath(sdkHubProperties.getSdkPath())
                .autoCheckUpdate(sdkHubProperties.isAutoCheckUpdate())
                .build();
        return ResponseEntity.ok(configVO);
    }

    /**
     * 更新 SDK 安装路径
     *
     * @param sdkPath 新的 SDK 路径
     * @return 更新结果
     */
    @PutMapping("/sdk-path")
    public ResponseEntity<ConfigVO> updateSdkPath(@RequestParam("sdkPath") String sdkPath) {
        String oldPath = sdkHubProperties.getSdkPath();
        sdkHubProperties.setSdkPath(sdkPath);
        log.info("SDK 路径已更新: {} -> {}", oldPath, sdkPath);
        ConfigVO result = ConfigVO.builder()
                .autoCheckUpdate(sdkHubProperties.isAutoCheckUpdate())
                .sdkPath(sdkHubProperties.getSdkPath())
                .build();
        return ResponseEntity.ok(result);
    }

}
