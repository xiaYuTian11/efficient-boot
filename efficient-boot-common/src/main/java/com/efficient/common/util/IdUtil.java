package com.efficient.common.util;

import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import com.efficient.common.entity.KeyPair;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * ID生成工具
 *
 * @author TMW
 * @since 2022/4/29 9:08
 */
public class IdUtil {

    // 设置一个基准时间，  2000-01-01 00:00:00
    private static final long TIMESTAMP_OFFSET = 946684800000L;
    private static final SecureRandom random = new SecureRandom();
    private static long sequence = 0L;

    public static String uuid() {
        return cn.hutool.core.util.IdUtil.simpleUUID();
    }

    /**
     * 生成唯一批次号，分布式可能重复
     *
     * @return
     */
    public static synchronized String generateBatchNumber() {
        long timestamp = System.currentTimeMillis() - TIMESTAMP_OFFSET;
        if (timestamp < 0) {
            throw new RuntimeException("System clock moved backwards. Refusing to generate batch number.");
        }

        long uniquePart = (timestamp << 20) | (sequence++ & 0xFFFFF);
        // 将生成的唯一号转换为36进制表示，并转换为大写
        return Long.toString(uniquePart, 36).toUpperCase();
    }

    /**
     * 生成不重复的32位字符串
     *
     * @return 32位字符串
     */
    public static synchronized String generateCode() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 32);
    }


}
