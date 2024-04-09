package com.efficient.ykz.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.efficient.ykz.api.YkzUserCenterHandleService;
import com.efficient.ykz.exception.YkzException;
import com.efficient.ykz.properties.YkzApi;
import com.efficient.ykz.properties.YkzProperties;
import com.efficient.ykz.service.YkzUserCenterHandleDefaultService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2024/1/4 15:27
 */
@Configuration
@EnableConfigurationProperties(YkzProperties.class)
@Slf4j
@MapperScan(basePackages = {"com.efficient.ykz.dao"})
public class YkzConfig {
    @Autowired
    private YkzProperties ykzProperties;

    @Bean
    public ExecutableClient init() {
        ExecutableClient executableClient;
        log.info("初始化YKZ  executableClient start!");
        executableClient = ExecutableClient.getInstance();
        YkzApi ykzApi = ykzProperties.getYkzApi();
        // DomainName不同环境对应不同域名，示例为sass域名
        executableClient.setDomainName(ykzApi.getDomainName());
        executableClient.setProtocal(ykzApi.getProtocal());
        //应用App Key
        String appkey = ykzApi.getAppkey();
        if (StrUtil.isBlank(appkey)) {
            // throw new YkzException("请检查YKZ 配置是否正确");
            log.error("ExecutableClient init fail!");
            log.error("请检查YKZ 配置是否正确");
            return null;
        }
        executableClient.setAccessKey(appkey);
        //应用App Secret
        String appsecret = ykzApi.getAppsecret();
        if (StrUtil.isBlank(appsecret)) {
            // throw new YkzException("请检查YKZ 配置是否正确");
            log.error("ExecutableClient init fail!");
            log.error("请检查YKZ 配置是否正确");
            return null;
        }
        executableClient.setSecretKey(appsecret);
        executableClient.init();
        log.info("初始化YKZ  executableClient  success!");
        return executableClient;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "com.efficient.ykz.userCenter.handle", havingValue = "default", matchIfMissing = true)
    public YkzUserCenterHandleService defaultHandle() {
        return new YkzUserCenterHandleDefaultService();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "com.efficient.ykz.userCenter.handle", havingValue = "custom", matchIfMissing = true)
    public YkzUserCenterHandleService customHandle() throws Exception {
        String handleClassName = ykzProperties.getUserCenter().getHandleClassName();
        if (StrUtil.isBlank(handleClassName)) {
            throw new YkzException("当com.efficient.ykz.userCenter.handle配置为custom，handleClassName 必填");
        }
        Class<?> aClass = Class.forName(handleClassName);
        Object object = aClass.newInstance();
        if (object instanceof YkzUserCenterHandleService) {
            return (YkzUserCenterHandleService) object;
        }
        throw new YkzException("com.efficient.ykz.userCenter.handleClassName 必须实现YkzUserCenterHandleService");
    }
}
