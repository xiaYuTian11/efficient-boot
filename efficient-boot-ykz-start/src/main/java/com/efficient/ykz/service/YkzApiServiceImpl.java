package com.efficient.ykz.service;

import cn.hutool.json.JSONObject;
import com.alibaba.xxpt.gateway.shared.api.request.OapiGettokenJsonRequest;
import com.alibaba.xxpt.gateway.shared.api.request.OapiRpcOauth2DingtalkAppUserJsonRequest;
import com.alibaba.xxpt.gateway.shared.api.response.OapiGettokenJsonResponse;
import com.alibaba.xxpt.gateway.shared.api.response.OapiRpcOauth2DingtalkAppUserJsonResponse;
import com.alibaba.xxpt.gateway.shared.client.http.IntelligentGetClient;
import com.alibaba.xxpt.gateway.shared.client.http.IntelligentPostClient;
import com.alibaba.xxpt.gateway.shared.client.http.api.OapiSpResultContent;
import com.efficient.common.result.Result;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.JackSonUtil;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.config.YkzConfig;
import com.efficient.ykz.model.vo.YkzAccessToken;
import com.efficient.ykz.model.vo.YkzLoginUser;
import com.efficient.ykz.properties.YkzApi;
import com.efficient.ykz.properties.YkzProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 *
 * @author TMW
 * @since 2024/1/11 15:35
 */
@Service
@Slf4j
public class YkzApiServiceImpl implements YkzApiService {
    @Autowired
    private YkzConfig ykzConfig;
    @Autowired
    private YkzProperties ykzProperties;

    @Override
    public Result<YkzAccessToken> accessToken() {
        YkzApi ykzApi = ykzProperties.getYkzApi();
        IntelligentGetClient intelligentGetClient = ykzConfig.getExecutableClient().newIntelligentGetClient(ykzApi.getAccessToken());
        OapiGettokenJsonRequest oapiGettokenJsonRequest = new OapiGettokenJsonRequest();
        //应用的唯一标识key
        oapiGettokenJsonRequest.setAppkey(ykzApi.getAppkey());
        //应用的密钥
        oapiGettokenJsonRequest.setAppsecret(ykzApi.getAppsecret());
        //获取结果
        OapiGettokenJsonResponse apiResult = intelligentGetClient.get(oapiGettokenJsonRequest);
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        OapiSpResultContent content = apiResult.getContent();
        Boolean success = content.getSuccess();
        if (!success) {
            return Result.build(ResultEnum.FAILED.getCode(), content.getResponseMessage());
        }
        String data = content.getData();
        YkzAccessToken accessToken = JackSonUtil.toObject(data, YkzAccessToken.class);
        return Result.ok(accessToken);
    }

    @Override
    public Result<YkzLoginUser> getUserInfo(String authCode) {
        Result<YkzAccessToken> ykzAccessTokenResult = this.accessToken();
        if (!Objects.equals(ykzAccessTokenResult.getCode(), ResultEnum.SUCCESS.getCode())) {
            return Result.build(ykzAccessTokenResult.getCode(), ykzAccessTokenResult.getMsg());
        }

        // executableClient保证单例
        YkzApi ykzApi = ykzProperties.getYkzApi();
        IntelligentPostClient intelligentPostClient = ykzConfig.getExecutableClient().newIntelligentPostClient(ykzApi.getUserInfo());
        OapiRpcOauth2DingtalkAppUserJsonRequest oapiRpcOauth2DingtalkAppUserJsonRequest = new OapiRpcOauth2DingtalkAppUserJsonRequest();
        //登录access_token
        oapiRpcOauth2DingtalkAppUserJsonRequest.setAccess_token(ykzAccessTokenResult.getData().getAccessToken());
        //临时授权码
        oapiRpcOauth2DingtalkAppUserJsonRequest.setAuth_code(authCode);
        //获取结果
        OapiRpcOauth2DingtalkAppUserJsonResponse apiResult = intelligentPostClient.post(oapiRpcOauth2DingtalkAppUserJsonRequest);
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        String content = apiResult.getContent();
        JSONObject json = new JSONObject(content);
        JSONObject contentJson = json.getJSONObject("content");
        boolean success = contentJson.getBool("success");
        if (!success) {
            return Result.build(5000,
                    contentJson.getStr("responseCode") + ": " + contentJson.getStr("responseMessage"));
        }
        String userData = contentJson.getStr("data");
        YkzLoginUser ykzLoginUser = JackSonUtil.toObject(userData, YkzLoginUser.class);
        return Result.ok(ykzLoginUser);
    }

}
