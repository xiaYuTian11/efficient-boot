package com.efficient.ykz.model.vo;

import lombok.Data;

/**
 *
 * @author TMW
 * @since 2024/1/15 10:49
 */
@Data
public class YkzTodoInfoVO {
    /**
     * 应用key
     */
    private String appKey;
    private String appName;
    private String businessType;
    private String taskUuid;
    private String packageUuid;
    private String bizTaskId;
    private Object creator;
    private Object assignee;
    private String subject;
    private String url;
    private String mobileUrl;
    private String createTime;
    private String startTime;
    private String dueTime;
    private String priority;
    private String finishTime;
    private String formValues;
    private String packageCreateTime;
    private String overdue;
    private String accelerators;
    private String needVpn;
    private String dueNotifyTypeArr;
    private String dueNotifyLevel;
    private String tag;

}
