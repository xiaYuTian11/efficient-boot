package com.efficient.idempotence.interceptor;

import cn.hutool.core.util.StrUtil;
import com.efficient.cache.api.CacheUtil;
import com.efficient.common.result.Result;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.JackSonUtil;
import com.efficient.common.util.WebUtil;
import com.efficient.idempotence.annotation.Idempotence;
import com.efficient.idempotence.constant.IdempotenceConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * 幂等性校验
 *
 * @author TMW
 * @since 2023/1/16 16:16
 */
@Component
public class IdempotenceInterceptor implements HandlerInterceptor {
    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String token = request.getHeader(IdempotenceConstant.TOKEN);
        String ip = WebUtil.getIP(request);
        if (StrUtil.isBlank(token)) {
            token = "not_token";
        }
        StringBuilder sb = new StringBuilder(token);
        String str = sb.append("_").append(ip).append("_").append(requestURI).toString();
        HandlerMethod method;
        try {
            method = (HandlerMethod) handler;
        } catch (ClassCastException e) {
            this.returnJson(response, ResultEnum.ERROR_PATH);
            return false;
        }
        Idempotence idempotence = method.getMethodAnnotation(Idempotence.class);
        if (Objects.nonNull(idempotence)) {
            Long obj = cacheUtil.get(IdempotenceConstant.IDEMPOTENCE_CACHE_PRE, str);
            long currentTimeMillis = System.currentTimeMillis();
            if (Objects.nonNull(obj)) {
                if (currentTimeMillis - obj <= idempotence.expireTime()) {
                    this.returnJson(response, ResultEnum.NOT_IDEMPOTENCE);
                    cacheUtil.put(IdempotenceConstant.IDEMPOTENCE_CACHE_PRE, str, currentTimeMillis, (int) idempotence.expireTime() / 1000);
                    return false;
                }
            }
            cacheUtil.put(IdempotenceConstant.IDEMPOTENCE_CACHE_PRE, str, currentTimeMillis, (int) idempotence.expireTime() / 1000);
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, ResultEnum resultEnum) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.append(JackSonUtil.toJson(Result.build(resultEnum)));
        out.close();
    }
}
