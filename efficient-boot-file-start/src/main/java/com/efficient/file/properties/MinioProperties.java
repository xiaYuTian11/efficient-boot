package com.efficient.file.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author TMW
 * @since 2023/4/6 14:28
 */
@ConfigurationProperties(prefix = "minio")
@ConditionalOnProperty(name = "com.efficient.file.active", havingValue = "minio")
@Component
@Data
public class MinioProperties {

    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 默认bucketName
     */
    private String bucketName = "demo";
}
