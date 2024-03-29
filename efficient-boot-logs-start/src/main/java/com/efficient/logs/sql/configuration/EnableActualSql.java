package com.efficient.logs.sql.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用SQL日志功能
 *
 * @author TMW
 * @since 2022/3/2 17:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ActualSqlConfiguration.class)
public @interface EnableActualSql {

}
