package com.yuchang.sdkhub.util;

import cn.hutool.core.bean.copier.CopyOptions;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author yuchang
 * @version 1.0
 * @description Java Bean工具类
 * @date 2026/2/13 01:32:47
 */
@UtilityClass
public class BeanUtil {

    /**
     * 复制对象属性并允许对结果进行自定义操作
     *
     * @param source      源对象
     * @param targetClass 目标类
     * @param <T>         目标类型
     * @return 复制后的目标对象
     */
    public <T> T copyProperties(Object source, Class<T> targetClass) {
        return cn.hutool.core.bean.BeanUtil.copyProperties(source, targetClass);
    }

    /**
     * 复制对象属性并允许对结果进行自定义操作
     *
     * @param source      源对象
     * @param targetClass 目标类
     * @param consumer    自定义操作
     * @param <S>         源类型
     * @param <T>         目标类型
     * @return 复制后的目标对象
     */
    public <S, T> T copyProperties(S source, Class<T> targetClass, BiConsumer<S, T> consumer) {
        T target = copyProperties(source, targetClass);
        // 额外的自定义处理
        if (consumer != null) {
            consumer.accept(source, target);
        }
        return target;
    }

    /**
     * 将集合中的对象复制为指定类型的列表
     *
     * @param collection 源集合
     * @param targetType 目标类型
     * @param <T>        目标类型
     * @return 复制后的目标对象列表
     */
    public <T> List<T> copyToList(Collection<?> collection, Class<T> targetType) {
        return cn.hutool.core.bean.BeanUtil.copyToList(collection, targetType, CopyOptions.create());
    }

    /**
     * 将集合中的对象复制为指定类型的列表，并允许对结果进行自定义操作
     *
     * @param sources     源集合
     * @param targetClass 目标类型
     * @param consumer    自定义操作
     * @param <S>         源类型
     * @param <T>         目标类型
     * @return 复制后的目标对象列表
     */
    public <S, T> List<T> copyToList(List<S> sources, Class<T> targetClass, BiConsumer<S, T> consumer) {
        return sources.stream().map(source -> copyProperties(source, targetClass, consumer)).toList();
    }

}
