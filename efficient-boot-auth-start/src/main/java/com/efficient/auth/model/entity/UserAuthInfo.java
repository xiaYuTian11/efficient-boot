package com.efficient.auth.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author TMW
 * @since 2022/10/28 15:21
 */
@Data
public class UserAuthInfo {
    private String account;
    private String password;
    private String username;
    private String userId;
    private String zwddId;
    /**
     * 用户是否锁定
     */
    private boolean lock;
    /**
     * 解锁时间
     */
    private Date unLockTime;
    private Object userInfo;

}
