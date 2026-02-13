package com.yuchang.sdkhub.controller;

import cn.hutool.core.util.StrUtil;
import com.yuchang.sdkhub.dto.InstalledSdkDTO;
import com.yuchang.sdkhub.dto.SdkInfoDTO;
import com.yuchang.sdkhub.dto.SdkVersionDTO;
import com.yuchang.sdkhub.service.SdkmanService;
import com.yuchang.sdkhub.util.BeanUtil;
import com.yuchang.sdkhub.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SDK 管理控制器
 * 提供 SDK 列表查询、安装、卸载、版本切换等 REST API 接口
 * 集成 SDKMAN 命令调用
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@RestController
@RequestMapping("/api/sdks")
@RequiredArgsConstructor
public class SdkController {

    private final SdkmanService sdkmanService;

    /**
     * 获取所有可用的 SDK 列表
     *
     * @return SDK 列表
     */
    @GetMapping
    public ResponseEntity<List<SdkVO>> getAllSdks() {
        List<SdkInfoDTO> sdks = sdkmanService.getAvailableSdks();
        List<SdkVO> result = BeanUtil.copyToList(sdks, SdkVO.class);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取指定 SDK 的所有可用版本
     *
     * @param sdkId SDK ID
     * @return 版本列表
     */
    @GetMapping("/{sdkId}/versions")
    public ResponseEntity<List<SdkVersionVO>> getSdkVersions(@PathVariable String sdkId) {
        List<SdkVersionDTO> versions = sdkmanService.getSdkVersions(sdkId);
        List<SdkVersionVO> result = BeanUtil.copyToList(versions, SdkVersionVO.class, (v, vo) -> {
                    String use = v.getUse();
                    if (StrUtil.equals(use, ">>>")) {
                        vo.setUsed(Boolean.TRUE);
                    }
                }
        );
        return ResponseEntity.ok(result);
    }

    /**
     * 安装指定版本的 SDK
     *
     * @param sdkId SDK ID
     * @return 安装结果
     */
    @PostMapping("/{sdkId}/install")
    public ResponseEntity<SdkOperationResultVO> installSdk(
            @PathVariable String sdkId,
            @RequestParam("version") String version) {
        boolean success = sdkmanService.installSdk(sdkId, version);

        SdkOperationResultVO result = SdkOperationResultVO.builder()
                .success(success)
                .message(success ? "Installation completed" : "Installation failed")
                .sdkId(sdkId)
                .version(version)
                .build();

        return ResponseEntity.ok(result);
    }

    /**
     * 卸载指定版本的 SDK
     *
     * @param sdkId SDK ID
     * @return 卸载结果
     */
    @PostMapping("/{sdkId}/uninstall")
    public ResponseEntity<SdkOperationResultVO> uninstallSdk(
            @PathVariable String sdkId,
            @RequestBody String version) {
        boolean success = sdkmanService.uninstallSdk(sdkId, version);
        SdkOperationResultVO result = SdkOperationResultVO.builder()
                .success(success)
                .message(success ? "Uninstallation completed" : "Uninstallation failed")
                .sdkId(sdkId)
                .version(version)
                .build();
        return ResponseEntity.ok(result);
    }

    /**
     * 切换到指定版本的 SDK（临时使用）
     *
     * @param sdkId   SDK ID
     * @param request 包含版本号的请求体
     * @return 切换结果
     */
    @PostMapping("/{sdkId}/use")
    public ResponseEntity<SdkOperationResultVO> useSdk(
            @PathVariable String sdkId,
            @RequestBody java.util.Map<String, String> request) {
        String version = request.get("version");
        boolean success = sdkmanService.useSdk(sdkId, version);
        SdkOperationResultVO result = SdkOperationResultVO.builder()
                .success(success)
                .message(success ? "Version switched" : "Switch failed")
                .sdkId(sdkId)
                .version(version)
                .build();
        return ResponseEntity.ok(result);
    }

    /**
     * 设置默认 SDK 版本
     *
     * @param sdkId   SDK ID
     * @param version 包含版本号的请求体
     * @return 设置结果
     */
    @PostMapping("/{sdkId}/default")
    public ResponseEntity<SdkOperationResultVO> setDefaultSdk(
            @PathVariable String sdkId,
            @RequestParam("version") String version) {
        boolean success = sdkmanService.setDefaultSdk(sdkId, version);

        SdkOperationResultVO result = SdkOperationResultVO.builder()
                .success(success)
                .message(success ? "Default version set" : "Failed to set default")
                .sdkId(sdkId)
                .version(version)
                .build();

        return ResponseEntity.ok(result);
    }

    /**
     * 获取已安装的 SDK 列表
     *
     * @return 已安装的 SDK 列表
     */
    @GetMapping("/installed")
    public ResponseEntity<List<InstalledSdkVO>> getInstalledSdks() {
        List<InstalledSdkDTO> installedSdks = sdkmanService.getInstalledSdks();
        List<InstalledSdkVO> result = BeanUtil.copyToList(installedSdks, InstalledSdkVO.class);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取当前使用的 SDK 版本
     *
     * @param sdkId SDK ID
     * @return 当前版本信息
     */
    @GetMapping("/{sdkId}/current")
    public ResponseEntity<SdkCurrentVersionVO> getCurrentVersion(@PathVariable String sdkId) {

        String version = sdkmanService.getCurrentVersion(sdkId);

        SdkCurrentVersionVO result = SdkCurrentVersionVO.builder()
                .sdkId(sdkId)
                .version(version)
                .installed(version != null)
                .build();

        return ResponseEntity.ok(result);
    }

    /**
     * 检查 SDKMAN 状态
     *
     * @return SDKMAN 安装状态
     */
    @GetMapping("/status")
    public ResponseEntity<SdkmanStatusVO> getSdkmanStatus() {

        boolean installed = sdkmanService.isSdkmanInstalled();
        String path = sdkmanService.getSdkmanPath();

        SdkmanStatusVO result = SdkmanStatusVO.builder()
                .installed(installed)
                .path(path)
                .message(installed ? "SDKMAN is installed" : "SDKMAN not found")
                .build();

        return ResponseEntity.ok(result);
    }
}
