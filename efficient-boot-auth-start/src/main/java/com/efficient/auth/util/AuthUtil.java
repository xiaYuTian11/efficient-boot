package com.efficient.auth.util;

import cn.hutool.core.util.StrUtil;
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
        if (authProperties.getLogin().isPasswordEncrypt()) {
            password = new String(Base64.getDecoder().decode(password.getBytes(StandardCharsets.UTF_8)));
        }
        final boolean enableSalt = authProperties.getLogin().isEnableSalt();
        if (enableSalt) {
            return SecureUtil.md5(password + authProperties.getLogin().getSaltValue());
        }
        return SecureUtil.md5(password);
    }

    /**
     *
     * @param cryptPassword 密文
     * @param password 明文
     * @return
     */
    public boolean checkEncrypt(String cryptPassword, String password) {
        String encrypt = this.encrypt(password);
        return StrUtil.equals(cryptPassword, encrypt);
    }

    /**
     * 生成密码
     * @param password
     * @return
     */
    public String createPassword(String password) {
        final boolean enableSalt = authProperties.getLogin().isEnableSalt();
        if (enableSalt) {
            return SecureUtil.md5(password + authProperties.getLogin().getSaltValue());
        }
        return SecureUtil.md5(password);
    }

}
