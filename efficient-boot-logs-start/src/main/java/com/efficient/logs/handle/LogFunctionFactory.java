package com.efficient.logs.handle;

import com.efficient.logs.api.LogFunction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义函数工厂
 * @author TMW
 * @since 2024/7/3 17:02
 */
public class LogFunctionFactory {

    private static final Map<String, LogFunction> logFunctionMap = new ConcurrentHashMap<>();

    public LogFunctionFactory(List<LogFunction> logFunctions) {
        for (LogFunction logFunction : logFunctions) {
            logFunctionMap.put(logFunction.getClass().getSimpleName(), logFunction);
        }
    }

    /**
     * 通过函数名获取对应自定义函数
     *
     * @param functionName 函数名
     * @return 自定义函数
     */
    public LogFunction getFunction(String functionName) {
        return logFunctionMap.get(functionName);
    }

}
