package com.efficient.auth.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.efficient.auth.properties.AuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author TMW
 * @since 2023/3/21 10:14
 */
@Component
@Slf4j
public class JwtUtil {

    @Autowired
    AuthProperties authProperties;

    /**
     * 创建TOKEN
     *
     * @param sub
     * @return
     */
    public String createToken(String sub) {
        return JWT.create()
                .withSubject(sub)
                .withExpiresAt(DateUtil.offset(new Date(), DateField.SECOND, authProperties.getTokenLive()))
                .sign(Algorithm.HMAC512(authProperties.getSecret()));
    }

    /**
     * 验证token
     *
     * @param token
     */
    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(authProperties.getSecret()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            log.error("jwt validateToken error", e);
            return null;
        }
    }

    /**
     * 检查token是否需要更新
     *
     * @param token
     * @return
     */
    public boolean isNeedUpdate(String token) {
        // 获取token过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(authProperties.getSecret()))
                    .build()
                    .verify(token)
                    .getExpiresAt();
        } catch (Exception e) {
            log.error("jwt validateToken error", e);
            return true;
        }
        // 如果剩余过期时间少于过期时常的一般时 需要更新
        return (expiresAt.getTime() - System.currentTimeMillis()) < (authProperties.getTokenLive() >> 1);
    }

}
