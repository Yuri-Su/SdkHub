package com.yuchang.sdkhub.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yuchang.sdkhub.config.SdkHubProperties;
import com.yuchang.sdkhub.dto.CommandResultDTO;
import com.yuchang.sdkhub.dto.InstalledSdkDTO;
import com.yuchang.sdkhub.dto.SdkInfoDTO;
import com.yuchang.sdkhub.dto.SdkVersionDTO;
import com.yuchang.sdkhub.util.SdkCategoryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * SDKMAN 服务层
 * 封装 SDKMAN 命令调用，提供 SDK 管理功能
 *
 * @author SdkHub Team
 * @version 0.1.0
 * @since 0.1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SdkmanService {

    private final SdkHubProperties properties;

    /**
     * 获取 SDKMAN 安装路径
     *
     * @return SDKMAN 根目录路径
     */
    public String getSdkmanPath() {
        String sdkmanPath = properties.getSdkPath();
        if (sdkmanPath == null || sdkmanPath.isEmpty()) {
            // 默认路径
            String home = System.getProperty("user.home");
            sdkmanPath = home + "/.sdkman";
        }
        // 处理 ~ 符号
        if (sdkmanPath.startsWith("~")) {
            String home = System.getProperty("user.home");
            sdkmanPath = home + sdkmanPath.substring(1);
        }
        return sdkmanPath;
    }

    /**
     * 检查 SDKMAN 是否已安装
     *
     * @return 是否已安装
     */
    public boolean isSdkmanInstalled() {
        Path sdkmanPath = Paths.get(getSdkmanPath());
        return Files.exists(sdkmanPath) && Files.isDirectory(sdkmanPath);
    }

    /**
     * 执行 SDKMAN 命令
     *
     * @param args 命令参数
     * @return 命令执行结果
     */
    public CommandResultDTO executeSdkCommand(String... args) {
        List<String> command = new ArrayList<>();
        command.add("bash");
        command.add("-c");
        // 构建命令字符串
        StringBuilder cmdBuilder = new StringBuilder();
        cmdBuilder.append("source ").append(getSdkmanPath()).append("/bin/sdkman-init.sh && ");
        cmdBuilder.append("sdk");
        for (String arg : args) {
            cmdBuilder.append(" ").append(arg);
        }
        command.add(cmdBuilder.toString());
        return executeCommand(command);
    }

    /**
     * 获取所有可用的 SDK 列表
     *
     * @return SDK 列表
     */
    public List<SdkInfoDTO> getAvailableSdks() {
        log.info("Getting available SDKs from SDKMAN");

        // 如果 SDKMAN 未安装，返回空列表
        if (!isSdkmanInstalled()) {
            log.warn("SDKMAN not installed, returning empty list");
            return List.of();
        }
        CommandResultDTO result = executeSdkCommand("list");
        if (!result.getSuccess()) {
            log.error("Failed to get SDK list: {}", result.getError());
            return List.of();
        }

        return parseSdkList(result.getOutput());
    }

    /**
     * 获取指定 SDK 的所有可用版本
     *
     * @param sdkId SDK ID
     * @return 版本列表
     */
    public List<SdkVersionDTO> getSdkVersions(String sdkId) {
        log.info("Getting versions for SDK: {}", sdkId);

        if (!isSdkmanInstalled()) {
            return new ArrayList<>();
        }

        CommandResultDTO result = executeSdkCommand("list", sdkId);

        if (!result.getSuccess()) {
            log.error("Failed to get versions: {}", result.getError());
            return new ArrayList<>();
        }

        return parseVersionList(result.getOutput(), sdkId);
    }

    /**
     * 安装指定版本的 SDK
     *
     * @param sdkId   SDK ID
     * @param version 版本号
     * @return 安装结果
     */
    public boolean installSdk(String sdkId, String version) {
        log.info("Installing SDK: {} version {}", sdkId, version);
        if (!isSdkmanInstalled()) {
            log.error("SDKMAN not installed");
            return false;
        }
        CommandResultDTO result = executeSdkCommand("install", sdkId, version);
        return result.getSuccess();
    }

    /**
     * 卸载指定版本的 SDK
     *
     * @param sdkId   SDK ID
     * @param version 版本号
     * @return 卸载结果
     */
    public boolean uninstallSdk(String sdkId, String version) {
        log.info("Uninstalling SDK: {} version {}", sdkId, version);

        if (!isSdkmanInstalled()) {
            log.error("SDKMAN not installed");
            return false;
        }

        CommandResultDTO result = executeSdkCommand("uninstall", sdkId, version);
        return result.getSuccess();
    }

    /**
     * 切换到指定版本的 SDK
     *
     * @param sdkId   SDK ID
     * @param version 版本号
     * @return 切换结果
     */
    public boolean useSdk(String sdkId, String version) {
        log.info("Switching SDK: {} to version {}", sdkId, version);
        if (!isSdkmanInstalled()) {
            log.error("SDKMAN not installed");
            return false;
        }
        CommandResultDTO result = executeSdkCommand("use", sdkId, version);
        return result.getSuccess();
    }

    /**
     * 设置默认 SDK 版本
     *
     * @param sdkId   SDK ID
     * @param version 版本号
     * @return 设置结果
     */
    public boolean setDefaultSdk(String sdkId, String version) {
        log.info("Setting default SDK: {} version {}", sdkId, version);

        if (!isSdkmanInstalled()) {
            log.error("SDKMAN not installed");
            return false;
        }

        CommandResultDTO result = executeSdkCommand("default", sdkId, version);
        return result.getSuccess();
    }

    /**
     * 获取已安装的 SDK 列表
     *
     * @return 已安装的 SDK 列表
     */
    public List<InstalledSdkDTO> getInstalledSdks() {
        log.info("Getting installed SDKs");

        if (!isSdkmanInstalled()) {
            return List.of();
        }

        CommandResultDTO result = executeSdkCommand("current");

        if (!result.getSuccess()) {
            log.error("Failed to get installed SDKs: {}", result.getError());
            return List.of();
        }
        return parseInstalledSdks(result.getOutput());
    }

    /**
     * 获取当前使用的 SDK 版本
     *
     * @param sdkId SDK ID
     * @return 当前版本
     */
    public String getCurrentVersion(String sdkId) {
        log.info("Getting current version for SDK: {}", sdkId);
        if (!isSdkmanInstalled()) {
            return null;
        }
        CommandResultDTO result = executeSdkCommand("current", sdkId);
        if (!result.getSuccess()) {
            return null;
        }
        // 解析输出获取当前版本
        String output = result.getOutput();
        // 格式: Using java version 21.0.2
        if (output.contains("version")) {
            String[] parts = output.split("version");
            if (parts.length > 1) {
                return parts[1].trim();
            }
        }

        return null;
    }

    /**
     * 执行系统命令
     *
     * @param command 命令列表
     * @return 命令执行结果
     */
    private CommandResultDTO executeCommand(List<String> command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            // 读取输出
            String output;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                output = reader.lines().collect(Collectors.joining("\n"));
            }
            // 等待进程完成
            boolean success = process.waitFor() == 0;
            return CommandResultDTO.builder().success(success).output(output).error("").build();
        } catch (IOException | InterruptedException e) {
            log.error("Command execution failed", e);
            return CommandResultDTO.builder().success(false).output("").error(e.getMessage()).build();
        }
    }

    /**
     * 解析 SDKMAN list 命令输出
     * 格式示例：
     * --------------------------------------------------------------------------------
     * Apache ActiveMQ (Classic) (5.17.1)                  https://activemq.apache.org/
     * <p>
     * Apache ActiveMQ® is a popular open source, multi-protocol...
     * <p>
     * $ sdk install activemq
     * --------------------------------------------------------------------------------
     *
     * @param output SDKMAN list 命令输出
     * @return SDK 信息列表
     */
    private List<SdkInfoDTO> parseSdkList(String output) {
        if (StrUtil.isBlank(output)) {
            return List.of();
        }
        List<SdkInfoDTO> sdks = new ArrayList<>();
        // 按分隔符分割每个候选者
        String[] sections = output.split("-{80,}");

        for (String section : sections) {
            SdkInfoDTO sdk = parseSdkSection(section.trim());
            if (sdk != null) {
                sdks.add(sdk);
            }
        }

        return sdks;
    }

    /**
     * 解析单个 SDK 区块
     *
     * @param section SDK 区块文本
     * @return SDK 信息 DTO
     */
    private SdkInfoDTO parseSdkSection(String section) {
        if (StrUtil.isBlank(section) || section.contains("Available Candidates") || section.contains("q-quit")) {
            return null;
        }
        List<String> lines = StrUtil.split(section, '\n');
        if (CollUtil.size(lines) < 2) {
            return null;
        }
        // 解析第一行：名称、版本、官网
        // 格式：Apache ActiveMQ (Classic) (5.17.1)                  https://activemq.apache.org/
        String headerLine = StrUtil.trim(CollUtil.getFirst(lines));
        String name;
        String version = "";
        String website = "";
        // 提取官网链接
        int backtickEnd = StrUtil.lastIndexOfIgnoreCase(headerLine, ")");
        if (backtickEnd != -1) {
            website = StrUtil.trim(StrUtil.sub(headerLine, backtickEnd + 1, -1));
            // Apache ActiveMQ (Classic) (5.17.1)
            headerLine = StrUtil.trim(StrUtil.sub(headerLine, 0, backtickEnd + 1));
        }
        // 提取版本号（括号中的内容）
        int versionStart = StrUtil.lastIndexOfIgnoreCase(headerLine, "(");
        if (versionStart != -1) {
            version = StrUtil.trim(StrUtil.sub(headerLine, versionStart + 1, -1));
            name = StrUtil.trim(StrUtil.sub(headerLine, 0, versionStart + 1));
        } else {
            name = headerLine;
        }

        // 提取 ID（从安装命令中）
        String sdkId = extractSdkId(section);
        if (sdkId.isEmpty()) {
            // 如果无法提取，使用名称的小写简化形式
            sdkId = name.toLowerCase().replaceAll("[^a-z0-9]", "").replaceAll("\\s+", "");
        }
        // 提取描述（多行）
        String description = lines.stream().skip(1).map(String::trim).filter(StrUtil::isNotBlank).filter(line -> !StrUtil.containsIgnoreCase(line, "$ sdk install")).collect(Collectors.joining(" "));
        // 推断分类
        String category = SdkCategoryUtil.inferCategory(sdkId, name, description);
        return SdkInfoDTO.builder().id(sdkId).name(name).vendor("").description(description).category(category).website(website).latestVersion(version).build();
    }

    /**
     * 从区块中提取 SDK ID
     *
     * @param section SDK 区块文本
     * @return SDK ID
     */
    private String extractSdkId(String section) {
        // 查找安装命令行：$ sdk install activemq
        int installIndex = section.indexOf("$ sdk install ");
        if (installIndex != -1) {
            String afterInstall = section.substring(installIndex + "$ sdk install ".length());
            // 提取到行尾或空格
            int endIndex = afterInstall.indexOf('\n');
            if (endIndex == -1) {
                endIndex = afterInstall.length();
            }
            return afterInstall.substring(0, endIndex).trim();
        }
        return "";
    }

    /**
     * 解析版本列表
     * ================================================================================
     * Available Java Versions for macOS ARM 64bit
     * ================================================================================
     * Vendor        | Use | Version      | Dist    | Status     | Identifier
     * --------------------------------------------------------------------------------
     * Corretto      |     | 25.0.2       | amzn    |            | 25.0.2-amzn
     *
     * @param output SDKMAN list 命令输出
     * @param sdkId  SDK ID
     * @return 版本列表
     */
    private List<SdkVersionDTO> parseVersionList(String output, String sdkId) {
        if (StrUtil.isBlank(output)) {
            return List.of();
        }
        List<SdkVersionDTO> versions = new ArrayList<>();
        List<String> lines = StrUtil.split(output, '\n');
        String currentVendor = "";

        for (String line : lines) {
            String trimmed = line.trim();
            // 跳过空行、分隔线和表头
            if (trimmed.isEmpty() ||
                    trimmed.startsWith("=") ||
                    trimmed.startsWith("-") ||
                    trimmed.contains("Vendor") && trimmed.contains("Version")) {
                continue;
            }
            // 跳过非数据行（如 "Available Java Versions..."）
            if (!trimmed.contains("|")) {
                continue;
            }

            SdkVersionDTO version = parseVersionLine(line, currentVendor);
            if (version != null) {
                versions.add(version);
                // 更新当前供应商（用于后续空供应商行的继承）
                if (version.getVendor() != null && !version.getVendor().isEmpty()) {
                    currentVendor = version.getVendor();
                }
            }
        }

        return versions;
    }

    /**
     * 解析单行版本信息
     */
    private SdkVersionDTO parseVersionLine(String line, String inheritedVendor) {
        // 按 | 分割，保留所有列（包括空列）
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) {
            return null;
        }

        // 提取各列并清理空白
        String vendor = parts[0].trim();
        String use = parts[1].trim();
        String version = parts[2].trim();
        String dist = parts[3].trim();
        String status = parts[4].trim();
        String identifier = parts[5].trim();

        // 如果供应商为空，使用继承的供应商（SDKMAN 输出中同一供应商的后续行可能省略供应商名）
        if (vendor.isEmpty()) {
            vendor = inheritedVendor;
        }

        // 如果版本号为空，跳过（可能是格式异常行）
        if (version.isEmpty()) {
            return null;
        }

        return SdkVersionDTO.builder()
                .vendor(vendor)
                .use(use)
                .version(version)
                .dist(dist)
                .status(status)  // 注意：DTO 中的 dist 字段实际对应 Status 列，这里可能需要调整字段名
                .identifier(identifier)
                .build();
    }

    /**
     * 解析已安装的 SDK 列表
     * [1;31m Some functionality is disabled or only partially available.[0m
     * [1;31m If this persists, please enable the offline mode:[0m
     * [1;31m[0m
     * [1;31m   $ sdk offline[0m
     * [1;31m[0m
     * [1;31m================================================================================[0m
     * <p>
     * <p>
     * Using:
     * <p>
     * java: 21.0.5-zulu
     * maven: 3.6.3
     *
     * @param output SDKMAN current 命令输出
     * @return 已安装的 SDK 列表
     */
    private List<InstalledSdkDTO> parseInstalledSdks(String output) {
        List<InstalledSdkDTO> sdks = new ArrayList<>();
        if (output == null || output.isEmpty()) {
            return sdks;
        }
        // 正则表达式：
        // ^\s*               -> 匹配行首空白
        // ([\w-]+)           -> 捕获组1：SDK名称 (字母数字下划线横杠，如 java, maven)
        // :\s*               -> 匹配冒号及后续空白
        // (.+?)              -> 捕获组2：版本号 (非贪婪匹配)
        // \s*$               -> 匹配行尾空白
        Pattern pattern = Pattern.compile("^\\s*([\\w-]+):\\s*(.+?)\\s*$");
        // 按行分割输出
        List<String> lines = StrUtil.split(output, '\n');
        for (String line : lines) {
            // 1. 去除 ANSI 颜色转义字符 (如 [1;31m 等)，防止干扰正则匹配
            String cleanLine = line.replaceAll("\u001B\\[[;\\d]*m", "").trim();
            // 2. 跳过无效行
            // 空行
            if (StrUtil.isBlank(cleanLine)) {
                continue;
            }
            // 警告/提示行
            if (StrUtil.containsAny(cleanLine, "Some functionality is disabled", "If this persists", "$ sdk offline")
                    || StrUtil.startWith(cleanLine, "==")) {
                continue;
            }
            // 3. 正则匹配提取
            Matcher matcher = pattern.matcher(cleanLine);
            if (matcher.find()) {
                String name = matcher.group(1);
                String version = matcher.group(2);
                // 注：此处 name 既是标识也是展示名，SDKMAN 输出中默认使用的即是当前版本，故设为 true
                InstalledSdkDTO dto = InstalledSdkDTO.builder()
                        .id(name)           // 设置 ID，通常与 name 相同
                        .name(name)         // SDK 名称
                        .version(version)   // 版本号
                        .current(true)      // 既然出现在 'current' 命令输出中，默认为当前使用版本
                        .build();
                sdks.add(dto);
            }
        }
        return sdks;
    }
}
