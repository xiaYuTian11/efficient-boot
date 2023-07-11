package com.efficient.logs.sql.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 真实SQL日志的配置集合
 *
 * @author TMW
 * @since 2022/3/2 17:43
 */
@ConfigurationProperties(prefix = "com.efficient.logs.sql")
@Getter
@Setter
public class ActualSqlProperties {

    /**
     * sql日志等级
     */
    private String level = "debug";
    /**
     * dao包的路径
     */
    private String daoPackage = "com.efficient";

    /**
     * 是否展示方法名
     */
    public boolean showMethod = false;

    /**
     * 是否展示SQL
     */
    public boolean showSql = true;

    /**
     * 是否展示执行耗时
     */
    public boolean showElapsed = false;

    /**
     * 是否展示结果行数
     */
    public boolean showRows = false;

}
