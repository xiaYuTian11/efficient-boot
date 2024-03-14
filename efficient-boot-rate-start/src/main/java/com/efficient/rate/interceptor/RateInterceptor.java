package com.efficient.rate.interceptor;

import cn.hutool.core.util.StrUtil;
import com.efficient.cache.api.CacheUtil;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.RenderJson;
import com.efficient.common.util.WebUtil;
import com.efficient.rate.annotation.RateLimit;
import com.efficient.rate.constant.RateConstant;
import com.efficient.rate.properties.RateProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 幂等性校验
 *
 * @author TMW
 * @since 2023/1/16 16:16
 */
@Component
@Slf4j
public class RateInterceptor implements HandlerInterceptor {
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private RateProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean enable = properties.isEnable();
        if (!enable) {
            return true;
        }
        String servletPath = request.getServletPath();
        String token = request.getHeader(CommonConstant.TOKEN);
        String ip = WebUtil.getIP(request);
        if (StrUtil.isBlank(token)) {
            token = "not_token";
        }
        StringBuilder sb = new StringBuilder(token);
        String str = sb.append("_").append(ip).append("_").append(servletPath).toString();
        HandlerMethod method;
        try {
            method = (HandlerMethod) handler;
        } catch (ClassCastException e) {
            RenderJson.returnJson(response, ResultEnum.ERROR_PATH);
            return false;
        }
        boolean global = properties.isGlobal();
        RateLimit idempotence = method.getMethodAnnotation(RateLimit.class);
        long expireTime;
        if (global) {
            expireTime = properties.getExpireTime();
        } else if (Objects.nonNull(idempotence)) {
            expireTime = idempotence.expireTime();
        } else {
            return true;
        }
        if (expireTime <= 0) {
            return true;
        }

        Long obj = cacheUtil.get(RateConstant.RATE_CACHE, str);
        long currentTimeMillis = System.currentTimeMillis();
        if (Objects.nonNull(obj)) {
            if (currentTimeMillis - obj <= (expireTime * 1000)) {
                cacheUtil.put(RateConstant.RATE_CACHE, str, currentTimeMillis, (int) (expireTime));
                RenderJson.returnJson(response, ResultEnum.NOT_IDEMPOTENCE);
                return false;
            }
        }
        cacheUtil.put(RateConstant.RATE_CACHE, str, currentTimeMillis, (int) (expireTime));
        return true;
    }

}
