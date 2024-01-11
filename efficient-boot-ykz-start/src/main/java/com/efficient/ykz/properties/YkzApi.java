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
     * 用户中心 appkey
     */
    private String appkey;
    /**
     * 用户中心 appsecret
     */
    private String appsecret;
    private String accessToken = "/gettoken.json";
    private String userInfo = "/rpc/oauth2/dingtalk_app_user.json";

}
