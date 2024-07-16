package com.efficient.elasticsearch.config;

import com.efficient.elasticsearch.properties.ElasticSearchProperties;
import com.efficient.elasticsearch.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TMW
 * @since 2024/5/15 9:08
 */
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)

@Slf4j
public class ElasticSearchConfig {
    @Autowired
    private ElasticSearchProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "com.efficient.elasticsearch.enable", havingValue = "true", matchIfMissing = true)
    public ElasticSearchService createElasticSearchService() {
        ElasticSearchService elasticSearchService = new ElasticSearchService();
        elasticSearchService.init(properties);
        log.info("load ElasticSearch  Service success !");
        return elasticSearchService;
    }
}
