package com.efficient.rate.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TMW
 * @since 2022/9/5 15:01
 */
@ConfigurationProperties("com.efficient.rate")
@Data
public class RateProperties {
    /**
     * 是否启用
     */
    private boolean enable = true;
    /**
     * 是否启用全局幂等性校验,启用后所有接口都会校验幂等性
     */
    private boolean global = false;
    /**
     * 全局幂等性校验间隔时间，设置后，com.efficient.rate.annotation.RateLimit的过期时间将会失效,最低一秒钟
     */
    private long expireTime = 1;
    /**
     * 拦截方法名称
     */
    private List<String> methodList = new ArrayList<String>() {

        private static final long serialVersionUID = -1943289260107007315L;

        {
            add("/**/save*");
            add("/**/add*");
            add("/**/insert*");
        }
    };

    /**
     * 排除拦截方法名称，针对特定包含在methodList中能匹配到的
     */
    private List<String> excludeApiList;
}
