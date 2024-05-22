package com.efficient.elasticsearch.entity;

import lombok.Data;

/**
 * @author TMW
 * @since 2024/5/22 15:42
 */
@Data
public class ElasticSearchRangeEntity {
    private String key;
    private RangeEntity rangeEntity;
}
