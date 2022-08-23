package com.sjr.common.entity;

import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author TMW
 * @since 2022/4/26 14:09
 */
@Data
public class UserTicket {
    /**
     * token
     */
    private String token;
    /**
     * 账号
     */
    private String account;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 票据生成时间
     */
    private long createTime;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 权限集合
     */
    private List<String> permissions;
    /**
     * 用户信息
     */
    private Object userInfo;
}
