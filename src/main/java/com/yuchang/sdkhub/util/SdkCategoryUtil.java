package com.yuchang.sdkhub.util;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuchang
 * @version 1.0
 * @description SDK类别工具类
 * @date 2026/2/13 01:08:37
 */
@UtilityClass
public class SdkCategoryUtil {

    // 分类匹配规则：键为分类名称，值为需要匹配的关键词列表
    private static final Map<String, String[]> CATEGORY_RULES;

    static {
        CATEGORY_RULES = new HashMap<>();
        CATEGORY_RULES.put("java", new String[]{"java", "jdk", "jvm"});
        CATEGORY_RULES.put("languages", new String[]{"kotlin", "scala", "groovy", "clojure", "jruby", "jython"});
        CATEGORY_RULES.put("buildTools", new String[]{"gradle", "maven", "ant", "sbt", "bazel", "bld"});
        CATEGORY_RULES.put("frameworks", new String[]{"spring", "micronaut", "quarkus", "vert.x", "helidon"});
        CATEGORY_RULES.put("databases", new String[]{"database", "sql", "mongo", "redis", "cassandra"});
        CATEGORY_RULES.put("messaging", new String[]{"mq", "kafka", "activemq", "rabbitmq", "pulsar"});
        CATEGORY_RULES.put("container", new String[]{"docker", "kubernetes", "k8s", "helm", "kubectl"});
        CATEGORY_RULES.put("cloud", new String[]{"aws", "azure", "gcp", "cloud", "terraform"});
    }

    /**
     * 根据 SDK 信息推断分类 (JDK 21 Switch 表达式版本)
     *
     * @param sdkId       SDK ID（允许为空）
     * @param name        SDK 名称（允许为空）
     * @param description SDK 描述（允许为空）
     * @return 分类名称，匹配不到时返回 "others"
     */
    public static String inferCategory(String sdkId, String name, String description) {
        // 处理空值，避免空指针异常
        String sdkIdStr = sdkId == null ? "" : sdkId;
        String nameStr = name == null ? "" : name;
        String descStr = description == null ? "" : description;
        String lowerContent = (sdkIdStr + " " + nameStr + " " + descStr).toLowerCase();
        // 使用 Switch 表达式进行分类匹配
        return switch (lowerContent) {
            case String s when containsAny(s, CATEGORY_RULES.get("java")) -> "java";
            case String s when containsAny(s, CATEGORY_RULES.get("languages")) -> "languages";
            case String s when containsAny(s, CATEGORY_RULES.get("buildTools")) -> "buildTools";
            case String s when containsAny(s, CATEGORY_RULES.get("frameworks")) -> "frameworks";
            case String s when containsAny(s, CATEGORY_RULES.get("databases")) -> "databases";
            case String s when containsAny(s, CATEGORY_RULES.get("messaging")) -> "messaging";
            case String s when containsAny(s, CATEGORY_RULES.get("container")) -> "container";
            case String s when containsAny(s, CATEGORY_RULES.get("cloud")) -> "cloud";
            default -> "others";
        };
    }

    /**
     * 判断字符串是否包含指定数组中的任意一个关键词
     *
     * @param content  待检查的字符串
     * @param keywords 关键词数组
     * @return 包含任意一个关键词返回 true，否则返回 false
     */
    private static boolean containsAny(String content, String[] keywords) {
        return StrUtil.containsAny(content, keywords);
    }
}
