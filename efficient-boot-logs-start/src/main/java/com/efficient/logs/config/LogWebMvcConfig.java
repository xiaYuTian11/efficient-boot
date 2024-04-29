package com.efficient.logs.config;

import com.efficient.logs.interceptor.RequestHolderInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author TMW
 * @date 2023/9/12
 */
@Configuration
@Slf4j
public class LogWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private RequestHolderInterceptor requestHolderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHolderInterceptor).addPathPatterns("/**");
    }

}
