package com.efficient.param.encrypt.util;

import com.efficient.param.encrypt.properties.ParamEncryptProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class AESUtils {
    @Autowired
    private ParamEncryptProperties properties;
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

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
     * AES加密
     */
    public String encrypt(byte[] data) throws Exception {
        Cipher cipher = getCipher(properties.getEncryptKey(), Cipher.ENCRYPT_MODE);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    /**
     * AES解密
     */
    public byte[] decrypt(byte[] data) throws Exception {
        Cipher cipher = getCipher(properties.getEncryptKey(), Cipher.DECRYPT_MODE);
        return cipher.doFinal(Base64.getDecoder().decode(data));
    }
}
