package com.efficient.configs.config;

import cn.hutool.core.collection.CollUtil;
import com.efficient.auth.interceptor.PermissionInterceptor;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.logs.interceptor.RequestHolderInterceptor;
import com.efficient.rate.interceptor.RateInterceptor;
import com.efficient.rate.properties.RateProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 * @author TMW
 * @date 2023/9/12
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private RequestHolderInterceptor requestHolderInterceptor;
    @Autowired
    private PermissionInterceptor permissionInterceptor;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private RateInterceptor rateInterceptor;
    @Autowired
    private RateProperties rateProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHolderInterceptor).addPathPatterns("/**");
        List<String> whiteList = authProperties.getWhiteList();
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**")
                .excludePathPatterns(whiteList);
        List<String> methodList = rateProperties.getMethodList();
        List<String> excludeApiList = rateProperties.getExcludeApiList();
        if (CollUtil.isEmpty(excludeApiList)) {
            registry.addInterceptor(rateInterceptor).addPathPatterns(methodList);
        } else {
            registry.addInterceptor(rateInterceptor).addPathPatterns(methodList)
                    .excludePathPatterns(excludeApiList);
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept",
                        "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3600);
    }

}
