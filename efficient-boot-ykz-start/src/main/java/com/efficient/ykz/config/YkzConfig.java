package com.efficient.ykz.config;

import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.efficient.ykz.properties.YkzApi;
import com.efficient.ykz.properties.YkzProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author TMW
 * @since 2024/1/4 15:27
 */
@Configuration
@EnableConfigurationProperties(YkzProperties.class)
@Slf4j
public class YkzConfig {
    private final AtomicReference<ExecutableClient> executableClientRef = new AtomicReference<>();
    @Autowired
    private YkzProperties ykzProperties;

    public ExecutableClient getExecutableClient() {
        ExecutableClient currentClient = executableClientRef.get();
        if (Objects.isNull(currentClient)) {
            synchronized (this) {
                currentClient = executableClientRef.get();
                if (Objects.isNull(currentClient)) {
                    currentClient = init();
                    executableClientRef.set(currentClient);
                }
            }
        }
        return currentClient;
    }

    public ExecutableClient init() {
        ExecutableClient executableClient;
        log.info("初始化渝快政 executableClient");
        executableClient = ExecutableClient.getInstance();
        YkzApi ykzApi = ykzProperties.getYkzApi();
        // DomainName不同环境对应不同域名，示例为sass域名
        executableClient.setDomainName(ykzApi.getDomainName());
        executableClient.setProtocal(ykzApi.getProtocal());
        //应用App Key
        executableClient.setAccessKey(ykzApi.getAppkey());
        //应用App Secret
        executableClient.setSecretKey(ykzApi.getAppsecret());
        executableClient.init();
        return executableClient;
    }
}
