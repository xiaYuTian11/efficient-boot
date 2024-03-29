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
 * 愉快政用户职位信息 实体类
 * </p>
 *
 * @author TMW
 * @date 2024-01-18 16:24:04
 */
@Data
@TableName("efficient_ykz_user_post")
public class YkzUserPostDb implements Serializable {

    private static final long serialVersionUID = 6694368259609836238L;
    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;
    /**
     * 政务钉钉 ID
     */
    @TableField("account_id")
    private String accountId;
    /**
     * 职务所在机构code
     */
    @TableField(value = "organization_code")
    private String organizationCode;
    /**
     * 任职类型 1主职、2兼职、3挂职、4借调
     */
    @TableField("post_type")
    private Integer postType;
    /**
     * 职务
     */
    @TableField("pos_job")
    private String posJob;
    /**
     * 错误信息
     */
    @TableField("error_info")
    private String errorInfo;
    /**
     * 拉取时间
     */
    @TableField("pull_time")
    private Date pullTime;
}
