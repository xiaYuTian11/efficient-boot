package com.efficient.configs.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author TMW
 * @since 2024/7/17 14:49
 */
@Configuration
@ConditionalOnProperty(name = "com.efficient.configs.webSocket.enable", havingValue = "true")
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
