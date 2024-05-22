package com.efficient.elasticsearch.entity;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/5/22 15:36
 */
@Data
public class ElasticSearchRequestEntity {
    /**
     * 字段
     */
    private String key;
    /**
     * 值
     */
    private String value;

    /**
     * 查找类型，1-全匹配，2-值%
     */
    private Integer searchType = 2;


}
