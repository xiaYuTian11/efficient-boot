package com.efficient.ykz.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 愉快政用户职位信息
 */
@Data
public class YkzUserPost implements Serializable {

    private static final long serialVersionUID = 3355733204378658155L;
    /**
     * 职务所在机构code
     */
    private String organizationCode;
    /**
     * 任职类型 1主职、2兼职、3挂职、4借调
     */
    private Integer postType;
    private String posJob;
}