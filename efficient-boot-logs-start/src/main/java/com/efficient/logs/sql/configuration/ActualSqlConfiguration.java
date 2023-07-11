package com.efficient.logs.sql.configuration;

import com.efficient.logs.sql.interceptor.ActualSqlInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * SQL日志功能启动类
 *
 * @author TMW
 * @since 2022/3/2 17:43
 */
@EnableConfigurationProperties(ActualSqlProperties.class)
@ConditionalOnClass(SqlSessionFactory.class)
@Configuration
@Lazy(false)
@Order
@ConditionalOnProperty(name = "com.efficient.logs.sql.showSql", havingValue = "true")
public class ActualSqlConfiguration {

    @Autowired
    ActualSqlProperties actualSqlProperties;

    @Autowired
    List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void registerInterceptor() {
        ActualSqlInterceptor interceptor = new ActualSqlInterceptor();
        Properties properties = new Properties();
        properties.put("", actualSqlProperties);
        interceptor.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            if (!isInterceptorExists(configuration, interceptor)) {
                configuration.addInterceptor(interceptor);
            }
        }
    }

    /**
     * 是否已经存在指定的拦截器
     *
     * @param configuration mybatis的配置
     * @param interceptor   指定拦截器
     * @return 存在/不存在
     */
    private static boolean isInterceptorExists(org.apache.ibatis.session.Configuration configuration, Interceptor interceptor) {
        try {
            return configuration.getInterceptors().contains(interceptor);
        } catch (Exception e) {
            return false;
        }
    }
}