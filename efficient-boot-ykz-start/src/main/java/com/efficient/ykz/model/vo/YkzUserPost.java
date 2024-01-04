package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 * 愉快政用户职位信息
 */
@Data
public class YkzUserPost {

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
