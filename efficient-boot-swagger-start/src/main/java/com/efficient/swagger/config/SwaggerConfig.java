package com.efficient.swagger.config;

import com.efficient.swagger.properties.SwaggerProperties;
import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

/**
 *
 * private final List<String> excludePaths = Arrays.asList("/swagger**", "/webjars/springfox-swagger-ui/**");
 *
 *     @Override
 *     public void addInterceptors(InterceptorRegistry registry) {
 *         registry.addInterceptor(permissionInterceptor).addPathPatterns("/**")
 *                 .excludePathPatterns(excludePaths);
 *     }
 *
 * swagger 配置类
 *
 * @author TMW
 * @since 2022/3/1 14:24
 */
@Configuration
@ConditionalOnClass(ApiInfo.class)
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "com.efficient.swagger", value = "enabled", havingValue = "true")
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerProperties.getEnable())
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .version(swaggerProperties.getVersion())
                .build();
    }
}
