package com.efficient.elasticsearch.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TMW
 * @since 2022/8/26 9:59
 */
@Data
public class FlinkProperties implements Serializable {
    private static final long serialVersionUID = 2589822200868710049L;
    /**
     * 批处理操作数
     */
    private Integer bulkActions = 1000;
    /**
     * 批处理大小，单位默认MB
     */
    private Integer bulkSize = 5;
    /**
     * 刷新间隔
     */
    private Integer flushInterval = 5;
    /**
     * 并发请求数
     */
    private Integer concurrentRequests = 5;
    /**
     * 重试次数
     */
    private Integer tryCount = 3;
    /**
     * 重试等待时间
     */
    private Integer tryWaitTime = 100;

}
