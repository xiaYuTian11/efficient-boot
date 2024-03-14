package com.efficient.auth.interceptor;

import cn.hutool.core.util.StrUtil;
import com.efficient.auth.constant.AuthConstant;
import com.efficient.auth.constant.AuthResultEnum;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.auth.util.JwtUtil;
import com.efficient.cache.api.CacheUtil;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.RenderJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 权限拦截器
 *
 * @author TMW
 * @date 2021/3/4 21:19
 */
@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private PermissionCheck permissionCheck;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        log.info(servletPath);
        HandlerMethod method;
        try {
            method = (HandlerMethod) handler;
        } catch (ClassCastException e) {
            if (Objects.nonNull(servletPath) && StrUtil.startWithAny(servletPath, "/")) {
                return true;
            }
            log.warn(AuthResultEnum.REQUEST_PATH_ERROR.getMsg());
            RenderJson.returnJson(response, AuthResultEnum.REQUEST_PATH_ERROR);
            return false;
        }
        // 用户权限注解
        Permission permission = method.getMethodAnnotation(Permission.class);
        if (Objects.isNull(permission)) {
            permission = method.getBeanType().getAnnotation(Permission.class);
        }

        if (Objects.isNull(permission)) {
            return true;
        }
        List<String> tokenGet = authProperties.getTokenGet();
        String token = null;
        if (StrUtil.startWithAny(servletPath, tokenGet.toArray(new String[0]))) {
            token = request.getParameter(CommonConstant.TOKEN);
        } else {
            token = request.getHeader(CommonConstant.TOKEN);
        }

        // header中没有token
        if (StrUtil.isBlank(token)) {
            log.warn(AuthResultEnum.NOT_LOGIN.getMsg());
            RenderJson.returnJson(response, AuthResultEnum.NOT_LOGIN);
            return false;
        }
        // 根据token查询
        String jwtToken = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.TOKEN_CACHE + token);
        UserTicket userTicket = jwtUtil.validateToken(jwtToken, authProperties.getUserTicketClass());
        if (Objects.isNull(userTicket)) {
            log.warn(AuthResultEnum.NOT_LOGIN.getMsg());
            RenderJson.returnJson(response, AuthResultEnum.NOT_LOGIN);
            return false;
        }
        int tokenLive = authProperties.getLogin().getTokenLive();
        if (jwtUtil.isNeedUpdate(jwtToken)) {
            jwtToken = jwtUtil.createToken(userTicket);
            cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.TOKEN_CACHE + token, jwtToken, tokenLive);
        }

        // 刷新用户信息保留时间
        cacheUtil.refresh(AuthConstant.AUTH_CACHE, AuthConstant.TOKEN_CACHE + token, tokenLive);
        cacheUtil.refresh(AuthConstant.AUTH_CACHE, AuthConstant.ON_LINE_USER_CACHE + userTicket.getUserId(), tokenLive);
        // 权限校验
        final boolean checkPermission = permissionCheck.checkPermission(permission, userTicket);
        if (!checkPermission) {
            log.warn(ResultEnum.NOT_PERMISSION.getMsg());
            RenderJson.returnJson(response, ResultEnum.NOT_PERMISSION);
            return false;
        }
        // 放入上下文
        RequestHolder.set(userTicket);
        RequestHolder.setCurrSystemId(request.getHeader(authProperties.getSystemIdField()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
