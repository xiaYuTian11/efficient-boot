package com.efficient.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author TMW
 * @since 2024/3/8 14:12
 */
public class ExceptionUtil {

    public static String writer(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
