package com.efficient.logs.service;

import com.efficient.logs.api.LogFunction;
import com.efficient.logs.api.LogFunctionService;
import com.efficient.logs.handle.LogFunctionFactory;

/**
 * 自定义函数的默认实现，增加一层是为了屏蔽底层与上层直接接触
 *
 * @author TMW
 * @since 2024/7/3 17:02
 */
public class DefaultLogFunctionServiceImpl implements LogFunctionService {

    private final LogFunctionFactory logFunctionFactory;

    public DefaultLogFunctionServiceImpl(LogFunctionFactory customFunctionFactory) {
        this.logFunctionFactory = customFunctionFactory;
    }

    @Override
    public String apply(String functionName, Object value) {
        LogFunction function = logFunctionFactory.getFunction(functionName);
        if (function == null) {
            return value.toString();
        }
        return function.apply(value);
    }

    @Override
    public boolean executeBefore(String functionName) {
        LogFunction function = logFunctionFactory.getFunction(functionName);
        return function != null;
    }
}
