package com.efficient.auth.config;

import com.efficient.auth.interceptor.PermissionInterceptor;
import com.efficient.auth.properties.AuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author TMW
 * @since 2024/4/29 10:58
 */
@Configuration
@Slf4j
public class AuthWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private PermissionInterceptor permissionInterceptor;
    @Autowired
    private AuthProperties authProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> whiteList = authProperties.getWhiteList();
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**")
                .excludePathPatterns(whiteList);
    }
}
