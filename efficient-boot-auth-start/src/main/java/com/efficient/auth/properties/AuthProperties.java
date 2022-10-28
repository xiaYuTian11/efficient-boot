package com.efficient.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TMW
 * @since 2022/10/28 11:45
 */
@ConfigurationProperties("com.efficient.auth")
@Data
public class AuthProperties {
    /**
     * 是否启用验证码
     */
    private boolean captcha;
    /**
     * 登录重试次数
     */
    private int retryCount;
    /**
     * 锁定时间，分钟
     */
    private int lockTime;
    /**
     * 同一账号最大在线人数
     */
    private int maxOnline;
    /**
     * 最大允许存活时间
     */
    private int tokenLive;
    /**
     * 密码是否加密传输
     */
    private boolean passwordEncrypt;
    /**
     * 密码是否加盐
     */
    private boolean enableSalt;
    /**
     * 盐值
     */
    private String saltValue;

}
