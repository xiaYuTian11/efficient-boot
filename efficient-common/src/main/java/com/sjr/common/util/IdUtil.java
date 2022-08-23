package com.sjr.common.util;

/**
 * ID生成工具
 *
 * @author TMW
 * @since 2022/4/29 9:08
 */
public class IdUtil {

    public static String uuid() {
        return cn.hutool.core.util.IdUtil.simpleUUID();
    }
}
