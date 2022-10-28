package com.efficient.auth.util;

import cn.hutool.crypto.SecureUtil;
import com.efficient.auth.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author TMW
 * @since 2022/10/28 15:06
 */
@Component
public class AuthUtil {
    private final static String SALT = "1089";
    @Autowired
    private AuthProperties authProperties;

    /**
     * 用户密码加密
     *
     * @param password
     * @return
     */
    public String encrypt(String password) {
        if (authProperties.isPasswordEncrypt()) {
            password = new String(Base64.getDecoder().decode(password.getBytes(StandardCharsets.UTF_8)));
        }
        final boolean enableSalt = authProperties.isEnableSalt();
        if (enableSalt) {
            return SecureUtil.md5(password + authProperties.getSaltValue());
        }
        return SecureUtil.md5(password);
    }

}
