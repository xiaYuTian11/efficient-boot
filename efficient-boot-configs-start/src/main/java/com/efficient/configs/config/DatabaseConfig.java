package com.efficient.configs.config;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author TMW
 * @since 2024/7/16 15:19
 */
@Component
@Slf4j
public class DatabaseConfig {
    @Autowired
    private DataSource dataSource;

    private String databaseProductName;

    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            this.databaseProductName = metaData.getDatabaseProductName();
        } catch (SQLException e) {
            log.error("获取数据库配置异常", e);
        }
    }

    public DbType getDbType() {
        return DbType.getDbType(databaseProductName);
    }

}
