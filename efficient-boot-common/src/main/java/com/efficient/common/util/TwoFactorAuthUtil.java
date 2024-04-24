package com.efficient.common.util;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

/**
 * 2FA 工具类
 *
 * @author TMW
 * @since 2024/4/24 14:40
 */
public class TwoFactorAuthUtil {
    private static GoogleAuthenticator googleAuthenticator;

    static {
        GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder configBuilder = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();
        // 配置为默认值
        TwoFactorAuthUtil.googleAuthenticator = new GoogleAuthenticator(configBuilder.build());
    }

    public static void main(String[] args) {
        // 生成密钥
        GoogleAuthenticatorKey key = TwoFactorAuthUtil.generateSecretKey();
        String secretKey = key.getKey();

        // 打印密钥和二维码 URL（可用于在客户端中扫描）
        System.out.println("Secret Key: " + secretKey);

        // 生成一次性密码
        int otp = TwoFactorAuthUtil.generateOneTimePassword(secretKey);
        System.out.println("Generated One-Time Password: " + otp);

        // 验证一次性密码
        boolean isValid = TwoFactorAuthUtil.verifyCode(secretKey, otp);
        System.out.println("Is the code valid? " + isValid);
    }

    /**
     * 生成密钥
     *
     * @return 密钥
     */
    public static GoogleAuthenticatorKey generateSecretKey() {
        return googleAuthenticator.createCredentials();
    }

    /**
     * 生成一次性密码
     *
     * @param secretKey 密钥
     * @return 一次性密码
     */
    public static int generateOneTimePassword(String secretKey) {
        // 使用给定的密钥生成当前一次性密码
        return googleAuthenticator.getTotpPassword(secretKey);
    }

    /**
     * 验证一次性密码
     *
     * @param secretKey 密钥
     * @param code      一次性密码
     * @return 是否通过
     */
    public static boolean verifyCode(String secretKey, int code) {
        return googleAuthenticator.authorize(secretKey, code);
    }
}
