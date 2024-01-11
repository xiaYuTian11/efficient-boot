package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/11 16:14
 */
@Data
public class YkzLoginUser {
    /**
     * 账号名是自定义的, 大部分是字母
     */
    private String account;
    /**
     * 账号id
     */
    private Long accountId;
    /**
     * 应用名
     */
    private String clientId;
    /**
     * 租户下人员编码
     */
    private String employeeCode;
    /**
     * 姓名
     */
    private String lastName;
    /**
     * 账号类型
     */
    private String namespace;
    /**
     * 昵称
     */
    private String nickNameCn;
    /**
     * 租户id
     */
    private Long realmId;
    /**
     * 租户名称
     */
    private String realmName;
    /**
     * 租户+用户唯一标识
     */
    private String tenantUserId;
    /**
     * 应用+用户唯一标识
     */
    private String openid;

}
