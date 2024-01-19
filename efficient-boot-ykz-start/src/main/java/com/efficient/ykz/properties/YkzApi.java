package com.efficient.ykz.properties;

import lombok.Data;

/**
 * 渝快政用户中心
 *
 * @author TMW
 * @since 2024/1/4 11:52
 */
@Data
public class YkzApi {
    /**
     * 用户中心 IP
     */
    private String domainName = "zd-openplatform.bigdatacq.com";
    /**
     * 协议
     */
    private String protocal = "https";
    /**
     * 协议
     */
    private Long tenantId = 1L;
    /**
     * 用户中心 appkey
     */
    private String appkey;
    /**
     * 用户中心 appsecret
     */
    private String appsecret;
    /**
     * 获取accessToken
     */
    private String accessToken = "/gettoken.json";
    /**
     * 获取用户信息
     */
    private String userInfo = "/rpc/oauth2/dingtalk_app_user.json";
    /**
     * 获取token信息
     */
    private String tokenInfo = "/rpc/oauth2/dingtalk_app_token.json";
    /**
     * 发送消息
     */
    private String sendMsg = "/chat/sendMsg";
    /**
     * 发送工作通知
     */
    private String sendWorkNotice = "/message/workNotification";
    /**
     * 撤销工作通知
     */
    private String revokeWorkNotice = "/message/revoke";
    /**
     * 创建待办
     */
    private String createTodo = "/tc/v2/openapi/task/create.json";
    /**
     * 完成待办
     */
    private String finishTodo = "/tc/openapi/task/finish.json";
    /**
     * 撤销待办
     */
    private String cancelTodo = "/tc/openapi/task/cancel.json";

}
