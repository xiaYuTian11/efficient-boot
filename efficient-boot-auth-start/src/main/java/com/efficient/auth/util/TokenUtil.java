package com.efficient.auth.util;

import cn.hutool.crypto.digest.DigestUtil;
import com.efficient.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

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
    public static String createToken(String userId, long createTime, Integer loginType, HttpServletRequest request) {
        String ip = WebUtil.getIP(request);
        String data = userId + SEPARATOR + ip + SEPARATOR + loginType + SEPARATOR + createTime;
        return DigestUtil.md5Hex(data);
    }

}
