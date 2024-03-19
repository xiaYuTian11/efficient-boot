package com.efficient.auth.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.common.util.JackSonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

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
    public String createToken(Object sub) {
        return JWT.create()
                .withSubject(JackSonUtil.toJson(sub))
                .withExpiresAt(DateUtil.offset(new Date(), DateField.SECOND, authProperties.getLogin().getTokenLive()))
                .sign(Algorithm.HMAC512(authProperties.getLogin().getSecret()));
    }

    /**
     * 创建TOKEN,永不过期，试用于渝快政消息通知类型
     *
     * @param sub
     * @return
     */
    public String createAuthCode(Object sub) {
        return JWT.create()
                .withSubject(JackSonUtil.toJson(sub))
                .sign(Algorithm.HMAC512(authProperties.getLogin().getSecret()));
    }

    /**
     * 验证token
     *
     * @param token
     */
    public <T> T validateToken(String token, Class<T> tClass) {
        if (StrUtil.isBlank(token)) {
            log.error("token is null");
            return null;
        }
        try {
            String subject = JWT.require(Algorithm.HMAC512(authProperties.getLogin().getSecret()))
                    .build()
                    .verify(token)
                    .getSubject();
            return JackSonUtil.toObject(subject, tClass);
        } catch (Exception e) {
            log.error(e.getMessage());
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
            expiresAt = JWT.require(Algorithm.HMAC512(authProperties.getLogin().getSecret()))
                    .build()
                    .verify(token)
                    .getExpiresAt();
        } catch (Exception e) {
            log.error("jwt validateToken error", e);
            return true;
        }
        if (Objects.isNull(expiresAt)) {
            log.info("jwt未设置过期时间");
            return false;
        }
        // 如果剩余过期时间少于过期时常的一般时 需要更新
        return ((expiresAt.getTime() - System.currentTimeMillis()) / 1000) < (authProperties.getLogin().getTokenLive() >> 1);
    }

}
