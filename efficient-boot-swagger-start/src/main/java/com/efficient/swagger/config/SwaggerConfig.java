package com.efficient.swagger.config;

import com.efficient.swagger.properties.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * private final List<String> excludePaths = Arrays.asList("/swagger**", "/webjars/springfox-swagger-ui/**");
 *
 * @author TMW
 * @Override public void addInterceptors(InterceptorRegistry registry) {
 * registry.addInterceptor(permissionInterceptor).addPathPatterns("/**")
 * .excludePathPatterns(excludePaths);
 * }
 * <p>
 * swagger 配置类
 * @since 2022/3/1 14:24
 */
@Configuration
// @EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
// @ConditionalOnProperty(name = "com.efficient.swagger.enable", havingValue = "true")
public class SwaggerConfig {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnMissingBean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerProperties.getEnable())
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
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
