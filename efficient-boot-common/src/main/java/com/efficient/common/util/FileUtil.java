package com.efficient.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author TMW
 * @since 2022/5/24 14:48
 */
public class FileUtil {
    public static String DOWNLOAD_PATH = "download";
    public static String UPDATE_PATH = "upload";

    /**
     * 根据绝对路径获取服务器相对路径
     *
     * @param absolutePath
     * @return
     */
    public static String getRelativePath(String absolutePath) {
        int indexOf = absolutePath.lastIndexOf(DOWNLOAD_PATH);
        if (indexOf < 0) {
            indexOf = absolutePath.lastIndexOf(UPDATE_PATH);
        }
        if (indexOf < 0) {
            return absolutePath;
        }
        return absolutePath.substring(indexOf - 1);
    }

    /**
     * 文件名称添加时间戳
     * @param title
     * @param suffix
     * @return
     */
    public static String renameDateTime(String title, String suffix) {
        // 后缀
        String fileName = title + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "." + suffix;
        return fileName.replaceAll(" ", "");
    }

}
