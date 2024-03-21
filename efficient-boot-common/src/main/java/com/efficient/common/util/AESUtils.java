package com.efficient.common.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加解密工具类
 *
 * @author TMW
 * @since 2023/6/8 16:55
 */
@Slf4j
public class AESUtils {
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
    /**
     * 128-bit
     */
    private static final String ENCRYPT_KEY = "http://tanmw.top";

    /**
     * AES加密
     */
    public static String encrypt(String data) {
        if (StrUtil.isBlank(data)) {
            return data;
        }
        try {
            Cipher cipher = getCipher(ENCRYPT_KEY, Cipher.ENCRYPT_MODE);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("数据加解密异常:" + data, e);
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
    public static byte[] decrypt(byte[] data) {
        if (ArrayUtil.isEmpty(data)) {
            return data;
        }
        try {
            Cipher cipher = getCipher(ENCRYPT_KEY, Cipher.DECRYPT_MODE);
            return cipher.doFinal(Base64.getDecoder().decode(data));
        } catch (Exception e) {
            throw new RuntimeException("数据加解密异常:" + new String(data), e);
        }
    }

    /**
     * AES解密
     */
    public static String decrypt(String data) {
        if (StrUtil.isBlank(data)) {
            return data;
        }
        try {
            Cipher cipher = getCipher(ENCRYPT_KEY, Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            throw new RuntimeException("数据加解密异常:" + data, e);
        }
    }
}
