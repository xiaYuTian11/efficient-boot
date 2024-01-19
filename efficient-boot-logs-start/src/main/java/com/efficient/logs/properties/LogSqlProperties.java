package com.efficient.logs.properties;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/19 15:47
 */
@Data
public class LogSqlProperties {
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
    private boolean showMethod = false;
    /**
     * 是否展示SQL
     */
    private boolean showSql = true;
    /**
     * 是否展示执行耗时
     */
    private boolean showElapsed = false;
    /**
     * 是否展示结果行数
     */
    private boolean showRows = false;
}
