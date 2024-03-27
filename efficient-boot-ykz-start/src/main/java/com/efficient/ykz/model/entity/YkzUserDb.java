package com.efficient.ykz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * YKZ 用户信息 实体类
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Data
@TableName("efficient_ykz_user")
public class YkzUserDb implements Serializable {

    private static final long serialVersionUID = 2241850641676716527L;
    /**
     *主键
     */
    @TableId(value = "id")
    private String id;
    /**
     *用户中心 ID
     */
    @TableField(value = "ykz_id")
    private Long ykzId;
    /**
     *姓名
     */
    @TableField("name")
    private String name;
    /**
     *用户名
     */
    @TableField("username")
    private String username;
    /**
     *政务钉钉 ID
     */
    @TableField("account_id")
    private String accountId;
    /**
     *政务钉钉员工编号
     */
    @TableField("employee_code")
    private String employeeCode;
    /**
     *电话号码
     */
    @TableField("mobile")
    private String mobile;
    /**
     *错误信息
     */
    @TableField("error_info")
    private String errorInfo;
    /**
     *拉取时间
     */
    @TableField("pull_time")
    private Date pullTime;
}
