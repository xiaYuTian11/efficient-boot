package com.efficient.logs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/9/5 15:01
 */
@ConfigurationProperties("com.efficient.log")
@Data
public class LogsProperties {
    /**
     * 是否存储数据库
     */
    private boolean db = true;
    /**
     * 日志等级
     */
    private String level = "info";
    /**
     * sql日志等级
     */
    private String sqlLevel = "debug";
    /**
     * dao包的路径
     */
    private String daoPackage = "com.efficient";
    /**
     * 日志名称
     */
    private String name = "log";
    /**
     * 日志存储路径
     */
    private String path = "/efficient/logs/";

}
