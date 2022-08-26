package com.efficient.file.util;

import cn.hutool.core.util.StrUtil;

import static com.efficient.file.constant.FileConstant.*;

/**
 * @author TMW
 * @since 2022/8/26 17:22
 */
public class PathUtil {
    public static String getFileUrlFolder(String suffix) {
        StringBuilder sb = new StringBuilder();
        // 上传图片格式文件
        if (StrUtil.equalsAnyIgnoreCase(suffix, ".xbm", ".tif", ".pjp", ".jfif", ".webp", ".pjpeg", ".avif", ".ico", ".tiff", ".bmp", ".png", ".jpeg", ".svgz", ".jpg", ".gif", ".svg")) {
            sb.append(FOLDER_PIC);
        } else {
            // 上传附件
            sb.append(FOLDER_FILE);
            if (StrUtil.equalsAnyIgnoreCase(suffix, ".doc", ".docx")) {
                sb.append(FOLDER_WORD);
            } else if (StrUtil.equalsAnyIgnoreCase(suffix, ".xls", ".xlsx")) {
                sb.append(FOLDER_EXCEL);
            } else if (StrUtil.equalsAnyIgnoreCase(suffix, ".pdf")) {
                sb.append(FOLDER_PDF);
            }
        }
        return sb.toString();
    }
}
