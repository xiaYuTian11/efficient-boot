package com.efficient.data.security.util;

import com.efficient.data.security.properties.DataSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加解密工具类
 *
 * @author TMW
 * @since 2023/6/8 16:55
 */
@Component
@Slf4j
public class AESUtils {
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
    @Autowired
    private DataSecurityProperties properties;

    /**
     * AES加密
     */
    public String encrypt(byte[] data) {
        try {
            Cipher cipher = getCipher(properties.getEncryptKey(), Cipher.ENCRYPT_MODE);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data));
        } catch (Exception e) {
            // log.error("数据加解密异常", e);
            // return new String(data, StandardCharsets.UTF_8);
            throw new RuntimeException("数据加解密异常", e);
        }
    }
    /**
     * AES加密
     */
    public String encrypt(String data) {
        try {
            Cipher cipher = getCipher(properties.getEncryptKey(), Cipher.ENCRYPT_MODE);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            // log.error("数据加解密异常", e);
            // return new String(data, StandardCharsets.UTF_8);
            throw new RuntimeException("数据加解密异常", e);
        }
    }

    /**
     * 获取 cipher
     */
    private static Cipher getCipher(String key, int model) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(model, secretKeySpec);
        return cipher;
    }

    /**
     * AES解密
     */
    public byte[] decrypt(byte[] data) {
        try {
            Cipher cipher = getCipher(properties.getEncryptKey(), Cipher.DECRYPT_MODE);
            return cipher.doFinal(Base64.getDecoder().decode(data));
        } catch (Exception e) {
            // log.error("数据加解密异常", e);
            // return data;
            throw new RuntimeException("数据加解密异常", e);
        }
    }

    /**
     * AES解密
     */
    public String decrypt(String data) {
        try {
            Cipher cipher = getCipher(properties.getEncryptKey(), Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            // log.error("数据加解密异常", e);
            // return data;
            throw new RuntimeException("数据加解密异常", e);
        }
    }
}
