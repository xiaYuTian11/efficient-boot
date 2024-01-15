package com.efficient.ykz.model.dto.todo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * @author TMW
 * @since 2024/1/15 10:34
 */
@Data
public class YkzTodoInfo {
    /**
     * 待办人信息，示例：{“imgId”:"",“name”:“张三”}不传的话待办人缺少名称与头像信息
     * 不必填
     */
    private String assigneeInfo;
    /**
     * 标题（上限200字符）
     * 必填
     */
    private String subject;
    /**
     * 1-渝快政内部打开，2-外部浏览器打开
     * 必填
     */
    private Integer openType = 1;
    /**
     * 创建人信息，示例：{“imgId”:"",“name”:“张三”}不传的话创建人缺少名称与头像信息
     * 不必填
     */
    private String creatorInfo;
    /**
     * 创建人ID，人员信息的accountId
     * 必填
     */
    private String creatorId;
    /**
     * 截止时间
     * 不必填
     */
    private Date dueTime;
    /**
     * 标题（上限200字符）
     * 必填
     */
    private String tenantId;
    /**
     * 模板code
     * 不必填
     */
    private String templateCode;
    /**
     * 传了模板code的情况下，待办列表展示formValues数据，格式 { “componentId1”:“value1”, “componentId2”:“value2” } componentId来自与创建模板时的组件ID
     * 不必填
     */
    private String formValues;
    /**
     * 业务系统自定义ID，业务系统保证唯一，否则不保证幂等
     * 必填
     */
    private String bizTaskId;
    /**
     * 移动端详情URL（上限1000字符，必须为带着参数的get请求,会在链接后面拼接&ddtab=true参数）
     * 必填
     */
    private String mobileUrl;
    /**
     * 待办人ID，人员信息中的accountId
     * 必填
     */
    private String assigneeId;
    /**
     * 详情URL（上限1000字符，必须为带着参数的get请求,会在链接后面拼接&ddtab=true参数）
     * 必填
     */
    private String url;
    /**
     * 实例唯一ID，任务会关联在这个实例下，不传的时候会同步创建一个新的实例
     * 不必填
     */
    private String packageUuid;
    /**
     * 是否发送动态卡片
     * 不必填
     */
    private Boolean isSendDynamicCard;
    /**
     * 事项类别GENERAL_TASK:普通任务OFFICIAL_DOCUMENT：公文任务
     * 不必填
     */
    private String category;
    /**
     * 提醒方式app-应用内(弹窗);notification-工作通知
     * 不必填
     */
    private List<String> dueNotifyTypeArr;
    /**
     * 提醒时间 （仅在配置截止时间后生效）0：截止时;1：15分钟前;2：1小时前;3：3小时前4：1天前
     * 不必填
     */
    private Integer dueNotifyLevel;
    /**
     * 是否弹窗提醒
     * 不必填
     */
    private Boolean isSendWindowNotice;
    /**
     * 种类code
     * 不必填
     */
    private String subTypeCode;
    /**
     * 动作ID请求与请求绑定的数组列表，序列化json字符串(传入种类后code生效)如：[{“id”:“AGREE”,“action”:{“url”:“xxx”,“headers”:{“header_1”:“xxx”},“paramsMap”:{“param_1”:“xxx”,“param_2”:“xxx”}}}]
     * 不必填
     */
    private String actionBindingJson;
    /**
     * 标签
     * 不必填
     */
    private String tag;

}
