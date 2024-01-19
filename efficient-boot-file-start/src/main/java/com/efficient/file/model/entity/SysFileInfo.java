package com.efficient.file.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文件信息 实体类
 * </p>
 *
 * @author code generator
 * @date 2022-08-26 11:21:02
 */
@Data
@TableName("efficient_sys_file_info")
public class SysFileInfo implements Serializable {

    private static final long serialVersionUID = 5092988968055492155L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;
    /**
     * 业务主键
     */
    @TableField("biz_id")
    private String bizId;
    /**
     * 存储类型
     */
    @TableField("store_type")
    private String storeType;
    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 文件类型
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 文件类型
     */
    @TableField("file_content")
    private byte[] fileContent;
    /**
     * 文件大写，k单位
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
