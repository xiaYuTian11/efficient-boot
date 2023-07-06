package com.efficient.auth.interceptor;

import cn.hutool.core.util.StrUtil;
import com.efficient.auth.constant.AuthConstant;
import com.efficient.auth.constant.AuthResultEnum;
import com.efficient.auth.permission.Permission;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.auth.util.JwtUtil;
import com.efficient.cache.api.CacheUtil;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;
import com.efficient.common.result.ResultConstant;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.JackSonUtil;
import com.efficient.common.util.RenderJson;
import lombok.extern.slf4j.Slf4j;
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
 * 权限拦截器
 *
 * @author TMW
 * @date 2021/3/4 21:19
 */
@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private JwtUtil jwtUtil;
    // @Autowired
    private static PermissionCheck permissionCheck;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method;
        try {
            method = (HandlerMethod) handler;
        } catch (ClassCastException e) {
            // log.error(e.getMessage(), e);
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
        String token = request.getHeader(AuthConstant.TOKEN);
        // header中没有token
        if (StrUtil.isBlank(token)) {
            RenderJson.returnJson(response, AuthResultEnum.NOT_LOGIN);
            return false;
        }
        // 根据token查询
        String jwtToken = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token);
        String subject = jwtUtil.validateToken(jwtToken);
        if (StrUtil.isBlank(subject)) {
            RenderJson.returnJson(response, AuthResultEnum.NOT_LOGIN);
            return false;
        }
        if (jwtUtil.isNeedUpdate(jwtToken)) {
            jwtToken = jwtUtil.createToken(subject);
            cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token, jwtToken);
        }
        UserTicket userTicket = JackSonUtil.toObject(subject, UserTicket.class);
        if (userTicket == null) {
            RenderJson.returnJson(response, AuthResultEnum.NOT_LOGIN);
            return false;
        }
        // 刷新用户信息保留时间
        cacheUtil.refresh(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token, authProperties.getTokenLive());
        cacheUtil.refresh(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userTicket.getUserId(), authProperties.getTokenLive());
        // 权限校验
        if (Objects.isNull(permissionCheck)) {
            PermissionInterceptor.permissionCheck = new DefaultPermissionCheck();
        }
        final boolean checkPermission = permissionCheck.checkPermission(permission, userTicket);
        if (!checkPermission) {
            RenderJson.returnJson(response, ResultEnum.NOT_PERMISSION);
            return false;
        }
        // 放入上下文
        RequestHolder.set(userTicket);
        // RequestHolder.set(request);
        return true;
    }

    // public boolean checkPermission(Permission permission, UserTicket userTicket) {
    //     if (Objects.nonNull(permissionCheck)) {
    //         return permissionCheck.checkPermission(permission, userTicket);
    //     }
    //     List<String> currMenus = userTicket.getPermissionList();
    //     final String[] menuEnums = permission.value();
    //     if (menuEnums.length <= 0) {
    //         return true;
    //     }
    //     if (CollUtil.isEmpty(currMenus)) {
    //         return false;
    //     }
    //     final MenuRelation relation = permission.relation();
    //     if (Objects.equals(relation, MenuRelation.OR)) {
    //         return Arrays.stream(menuEnums).anyMatch(currMenus::contains);
    //     } else {
    //         return Arrays.stream(menuEnums).allMatch(currMenus::contains);
    //     }
    // }

    public static void init(PermissionCheck permissionCheck) {
        PermissionInterceptor.permissionCheck = permissionCheck;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
