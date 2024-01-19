package com.efficient.logs.interceptor;

import com.efficient.common.auth.RequestHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 放入请求
 *
 * @author TMW
 * @since 2023/1/18 13:56
 */
@Component
public class RequestHolderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestHolder.set(request);
        return true;
    }
}
