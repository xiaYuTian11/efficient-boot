package com.efficient.rate.config;

import cn.hutool.core.collection.CollUtil;
import com.efficient.rate.interceptor.RateInterceptor;
import com.efficient.rate.properties.RateProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author TMW
 * @since 2024/4/29 11:06
 */
@Configuration
@Slf4j
public class RateWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private RateInterceptor rateInterceptor;
    @Autowired
    private RateProperties rateProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> methodList = rateProperties.getMethodList();
        List<String> excludeApiList = rateProperties.getExcludeApiList();
        if (CollUtil.isEmpty(excludeApiList)) {
            registry.addInterceptor(rateInterceptor).addPathPatterns(methodList);
        } else {
            registry.addInterceptor(rateInterceptor).addPathPatterns(methodList)
                    .excludePathPatterns(excludeApiList);
        }
    }
}
