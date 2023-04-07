package com.efficient.file.config;

import com.efficient.file.api.FileService;
import com.efficient.file.properties.FileProperties;
import com.efficient.file.service.DbFileServiceImpl;
import com.efficient.file.service.LocalFileServiceImpl;
import com.efficient.file.service.MinioFileServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件相关操作
 *
 * @author TMW
 * @since 2022/8/26 10:07
 */
@Configuration
@EnableConfigurationProperties(FileProperties.class)
@MapperScan(basePackages = {"com.efficient.file.dao"})
public class FileConfig {
    @Autowired
    private FileProperties fileProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "com.efficient.file.active", havingValue = "local", matchIfMissing = true)
    public FileService localFile() {
        return new LocalFileServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "com.efficient.file.active", havingValue = "db")
    public FileService dbFile() {
        return new DbFileServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "com.efficient.file.active", havingValue = "minio")
    public FileService minioFile() {
        return new MinioFileServiceImpl();
    }
}
