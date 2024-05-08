package com.efficient.swagger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author TMW
 * @date 2023/9/12
 */
@Configuration
@Slf4j
public class SwaggerWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 使用 AntPathMatcher
        configurer.setPathMatcher(new AntPathMatcher());
    }
}
