package com.efficient.logs.api;

/**
 * 自定义函数接口
 *
 * @author TMW
 * @since 2024/7/3 17:02
 */
public interface LogFunction {

    /**
     * 最终执行的方法
     *
     * @param param 参数
     * @return 执行结果
     */
    String apply(Object param);
}
