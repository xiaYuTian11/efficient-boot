package com.efficient.ykz.properties;

import lombok.Data;

/**
 * YKZ 用户中心
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
     * 用浏览器打开，不加默认用,只针对pc端
     */
    private Boolean winOpen = true;
    /**
     * pc访问地址前缀
     */
    private String pcUrl="http://127.0.0.1:8080";
    /**
     * app访问地址前缀
     */
    private String appUrl="http://127.0.0.1:8080";
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
