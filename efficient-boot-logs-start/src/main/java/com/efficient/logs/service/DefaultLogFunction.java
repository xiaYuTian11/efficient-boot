package com.efficient.logs.service;

import com.efficient.logs.api.LogFunction;
import org.springframework.stereotype.Component;

/**
 * 默认函数接口
 *
 * @author TMW
 * @since 2024/7/3 17:02
 */
@Component
public class DefaultLogFunction implements LogFunction {

    @Override
    public String apply(Object value) {
        return null;
    }
}
