package com.efficient.data.security.db.crypt;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class DbAES implements Crypt {
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static String dbEncryptKey;
    public static boolean dbEncryptEnable;
    public static String dbEncryptModelPath;

    /**
     * AES加密
     */
    public String encrypt(String data) {
        if (!dbEncryptEnable || StrUtil.isBlank(data)) {
            return data;
        }
        try {
            Cipher cipher = getCipher(dbEncryptKey, Cipher.ENCRYPT_MODE);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("DB层数据加解密异常:" + data, e);
            return data;
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
    public String decrypt(String data) {
        if (!dbEncryptEnable || StrUtil.isBlank(data)) {
            return data;
        }
        try {
            Cipher cipher = getCipher(dbEncryptKey, Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            log.error("DB层数据加解密异常:" + data, e);
            return data;
        }
    }

    @Value("${com.efficient.data.dbEncryptKey:http://tanmw.top}")
    private void setDbEncryptKey(String dbEncryptKey) {
        DbAES.dbEncryptKey = dbEncryptKey;
    }

    @Value("${com.efficient.data.dbEncryptEnable:false}")
    private void setDbEncryptEnable(boolean dbEncryptEnable) {
        DbAES.dbEncryptEnable = dbEncryptEnable;
    }

    @Value("${com.efficient.data.dbEncryptModelPath:top.tanmw.demo.model}")
    private void setDbEncryptModelPath(String dbEncryptModelPath) {
        DbAES.dbEncryptModelPath = dbEncryptModelPath;
    }
}
