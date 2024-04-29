package com.efficient.swagger.service;

import com.efficient.common.api.StartedEventServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author TMW
 * @since 2024/4/29 11:14
 */
@Slf4j
@ConditionalOnProperty(name = "com.efficient.swagger.enable", havingValue = "true")
@Service
public class SwaggerStartedEvent implements StartedEventServer {
    @Value("${server.port:8080}")
    private Integer port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Override
    public void init() {
        String serverAddress = "http://localhost:" + port + contextPath + "/swagger-ui/index.html";
        log.info("swagger doc url: {}", serverAddress);
    }
}
