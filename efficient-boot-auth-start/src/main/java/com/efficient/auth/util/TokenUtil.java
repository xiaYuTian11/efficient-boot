package com.efficient.auth.util;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * token 生成校验工具
 *
 * @author TMW
 * @date 2021/3/4 22:04
 */
@Slf4j
public class TokenUtil {
    private static final String SEPARATOR = "@";

    /***
     * 创建token
     * */
    public static String createToken(String userId, long createTime) {
        String data = userId + SEPARATOR + createTime;
        return DigestUtil.md5Hex(data);
    }

}
