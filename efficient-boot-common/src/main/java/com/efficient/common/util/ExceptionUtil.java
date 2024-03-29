package com.efficient.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 异常工具类
 *
 * @author TMW
 * @since 2024/3/8 14:12
 */
public class ExceptionUtil {

    /**
     * 将异常堆栈跟踪转换为字符串。
     *
     * @param throwable 异常对象
     * @return 异常堆栈跟踪的字符串表示
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * 获取异常链中的根异常。
     *
     * @param throwable 异常对象
     * @return 链中的根异常
     */
    public static Throwable getRootCause(Throwable throwable) {
        List<Throwable> list = getThrowableList(throwable);
        return list.size() < 2 ? throwable : list.get(list.size() - 1);
    }

    /**
     * 获取异常链中所有的异常。
     *
     * @param throwable 异常对象
     * @return 异常链中所有的异常列表
     */
    public static List<Throwable> getThrowableList(Throwable throwable) {
        List<Throwable> throwables = new ArrayList<>();
        while (throwable != null && !throwables.contains(throwable)) {
            throwables.add(throwable);
            throwable = throwable.getCause();
        }
        return throwables;
    }

    /**
     * 检查异常链中是否包含某种类型的异常。
     *
     * @param throwable 异常对象
     * @param clazz     要检查的异常类型
     * @return 如果包含该类型的异常，则返回true
     */
    public static boolean containsType(Throwable throwable, Class<?> clazz) {
        if (throwable == null || clazz == null) {
            return false;
        }
        Throwable cause = throwable;
        while (cause != null) {
            if (clazz.isInstance(cause)) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 获取异常的消息，如果消息为空，则返回异常的类名。
     *
     * @param throwable 异常对象
     * @return 异常消息或异常类名
     */
    public static String getMessage(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        String message = throwable.getMessage();
        return (message != null) ? message : throwable.getClass().getSimpleName();
    }
}
