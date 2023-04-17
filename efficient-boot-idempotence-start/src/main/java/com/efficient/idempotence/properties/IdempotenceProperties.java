package com.efficient.idempotence.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/9/5 15:01
 */
@ConfigurationProperties("com.efficient.idempotence")
@Data
public class IdempotenceProperties {
    /**
     * 是否启用全局幂等性校验,启用后所有接口都会校验幂等性
     */
    private boolean global = false;
    /**
     * 全局幂等性校验间隔时间，设置后，com.efficient.idempotence.annotation.Idempotence的过期时间将会失效,最低一秒钟
     */
    private long expireTime = 1000;

}
