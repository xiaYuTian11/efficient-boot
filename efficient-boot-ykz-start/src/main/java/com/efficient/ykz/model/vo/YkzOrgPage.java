package com.efficient.ykz.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author TMW
 * @since 2024/1/9 14:20
 */
@Data
public class YkzOrgPage {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer total;
    private Integer totalPage;
    private List<YkzOrg> list;

}
