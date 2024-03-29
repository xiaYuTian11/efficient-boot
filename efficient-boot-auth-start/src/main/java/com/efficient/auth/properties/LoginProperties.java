package com.efficient.auth.properties;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/1/22 16:36
 */
@Data
public class LoginProperties {
    /**
     * 是否启用验证码
     */
    private boolean captcha = false;
    /**
     * 验证码规则
     */
    private String captchaRule = "23456789abcdefghjkmnpqrstuvwxyz";
    /**
     * jwt secret
     */
    private String secret = "qwertyuiop0987654321";
    /**
     * 重试次数
     */
    private int retryCount = -1;
    /**
     * 重试时间，分钟
     */
    private int retryTime = 30;
    /**
     * 锁定时间，分钟
     */
    private int lockTime = 30;
    /**
     * 同一账号最大在线人数
     */
    private int maxOnline = -1;
    /**
     * 最大允许存活时间
     */
    private int tokenLive = 3600;
    /**
     * 密码是否加密传输
     */
    private boolean passwordEncrypt = false;
    /**
     * 密码是否加盐
     */
    private boolean enableSalt = true;
    /**
     * 盐值
     */
    private String saltValue = "1809";

}
