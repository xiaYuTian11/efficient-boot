package com.efficient.common.auth;

import lombok.Data;

import java.util.List;

/**
 * 用户实体
 *
 * @author TMW
 * @since 2022/10/28 11:34
 */
@Data
public class UserTicket {
    private String token;
    private String userId;
    private String zwddId;
    private String account;
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
     * 菜单权限
     */
    private List<String> permissionList;
    /**
     * 二级权限
     */
    private List<String> operationList;
    /**
     * 扩展信息
     */
    private Object extendInfo;


}
