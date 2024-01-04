package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 * 愉快政用户信息
 */
@Data
public class YkzUser {

    /**
     * 用户中心id
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 政钉id
     */
    private String accountId;
    /**
     * 政钉employeeCode
     */
    private String employeeCode;
    /**
     * 电话
     */
    private String phone;
}
