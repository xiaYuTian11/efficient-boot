package com.efficient.data.security.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.common.exception.DataSecurityException;
import com.efficient.data.security.properties.DataSecurityProperties;
import com.efficient.data.security.util.AESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TMW
 * @since 2023/7/5 15:39
 */
@ConditionalOnProperty(name = "com.efficient.security.api.requestEnable", havingValue = "true")
@Component
public class ReplaceStreamFilter implements Filter {
    @Autowired
    WebApplicationContext applicationContext;
    private Map<String, Method> METHOD_MAP;
    @Autowired
    private AESUtils aesUtils;
    @Autowired
    private DataSecurityProperties properties;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        initMethod();
        String contentType = "multipart/form-data";
        String reqContentType = servletRequest.getContentType();
        ServletRequest requestWrapper;
        if (StrUtil.isNotBlank(reqContentType) && reqContentType.contains(contentType)) {
            requestWrapper = servletRequest;
            filterChain.doFilter(requestWrapper, servletResponse);
        } else {
            try {
                requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest, aesUtils, properties, METHOD_MAP.get(((HttpServletRequest) servletRequest).getRequestURI()));
                filterChain.doFilter(requestWrapper, servletResponse);
            } catch (Exception e) {
                handlerExceptionResolver.resolveException((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, null, new DataSecurityException(e));
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void initMethod() {
        if (CollUtil.isEmpty(METHOD_MAP)) {
            synchronized (this) {
                if (CollUtil.isEmpty(METHOD_MAP)) {
                    METHOD_MAP = new HashMap<>();
                    RequestMappingHandlerMapping mappingHandlerMapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
                    Map<RequestMappingInfo, HandlerMethod> handlerMethods = mappingHandlerMapping.getHandlerMethods();

                    handlerMethods.forEach((key, value) -> {
                        PatternsRequestCondition p = key.getPatternsCondition();
                        String reqUrl = null;
                        for (String url : p.getPatterns()) {
                            reqUrl = url;
                        }
                        METHOD_MAP.put(reqUrl, value.getMethod());
                    });

                }
            }
        }
    }
}
