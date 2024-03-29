package com.efficient.auth.properties;

import cn.hutool.core.util.StrUtil;
import com.efficient.common.auth.UserTicket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TMW
 * @since 2022/10/28 11:45
 */
@ConfigurationProperties("com.efficient.auth")
@Data
@Slf4j
public class AuthProperties {
    private static volatile Class<? extends UserTicket> userTicketClassInstance = null;
    private LoginProperties login = new LoginProperties();
    /**
     * 系统ID字段
     */
    private String systemIdField = "systemId";
    private String permissionCheckType = "default";
    private String userTicketClassName = "com.efficient.common.auth.UserTicket";
    /**
     * 白名单
     */
    private List<String> whiteList = new ArrayList<String>() {
        private static final long serialVersionUID = -6831198909191678412L;

        {
            add("**swagger-resources**");
            add("/login");
            add("/captcha");
        }
    };

    /**
     * get方式传递token
     */
    private List<String> tokenGet = new ArrayList<String>() {
        private static final long serialVersionUID = -6831198909191678412L;

        {
            add("/video/play");
        }
    };
    /**
     * post方式传递token
     */
    @Deprecated
    private List<String> tokenPost = new ArrayList<>();

    public Class<? extends UserTicket> getUserTicketClass() {
        if (userTicketClassInstance == null) {
            synchronized (AuthProperties.class) {
                if (userTicketClassInstance == null) {
                    userTicketClassInstance = initializeUserTicketClass();
                }
            }
        }
        return userTicketClassInstance;
    }

    private Class<? extends UserTicket> initializeUserTicketClass() {

        if (StrUtil.equals(userTicketClassName, "com.efficient.common.auth.UserTicket")) {
            return UserTicket.class;
        }

        try {
            return (Class<? extends UserTicket>) Class.forName(userTicketClassName);
        } catch (ClassNotFoundException e) {
            log.error("获取用户类异常", e);
        }

        return UserTicket.class;
    }
}
