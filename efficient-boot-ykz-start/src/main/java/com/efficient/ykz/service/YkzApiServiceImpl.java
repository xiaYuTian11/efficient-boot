package com.efficient.ykz.service;

import cn.hutool.json.JSONObject;
import com.alibaba.xxpt.gateway.shared.api.request.OapiChatSendMsgRequest;
import com.alibaba.xxpt.gateway.shared.api.request.OapiGettokenJsonRequest;
import com.alibaba.xxpt.gateway.shared.api.request.OapiRpcOauth2DingtalkAppTokenJsonRequest;
import com.alibaba.xxpt.gateway.shared.api.request.OapiRpcOauth2DingtalkAppUserJsonRequest;
import com.alibaba.xxpt.gateway.shared.api.response.OapiChatSendMsgResponse;
import com.alibaba.xxpt.gateway.shared.api.response.OapiGettokenJsonResponse;
import com.alibaba.xxpt.gateway.shared.api.response.OapiRpcOauth2DingtalkAppTokenJsonResponse;
import com.alibaba.xxpt.gateway.shared.api.response.OapiRpcOauth2DingtalkAppUserJsonResponse;
import com.alibaba.xxpt.gateway.shared.client.http.IntelligentGetClient;
import com.alibaba.xxpt.gateway.shared.client.http.IntelligentPostClient;
import com.alibaba.xxpt.gateway.shared.client.http.api.OapiSpResultContent;
import com.efficient.common.result.Result;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.JackSonUtil;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.config.YkzConfig;
import com.efficient.ykz.constant.YkzSendMsgTypeEnum;
import com.efficient.ykz.model.dto.*;
import com.efficient.ykz.model.vo.YkzAccessToken;
import com.efficient.ykz.model.vo.YkzLoginToken;
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

    private <T> Result<T> getApiResult(String content, Class<T> tClass) {
        JSONObject json = new JSONObject(content);
        JSONObject contentJson = json.getJSONObject("content");
        boolean success = contentJson.getBool("success");
        if (!success) {
            return Result.build(ResultEnum.FAILED.getCode(),
                    contentJson.getStr("responseCode") + ": " + contentJson.getStr("responseMessage"));
        }
        String userData = contentJson.getStr("data");
        return Result.ok(JackSonUtil.toObject(userData, tClass));
    }

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
        return getApiResult(content, YkzLoginUser.class);
    }

    @Override
    public Result<YkzLoginToken> getTokenInfo(String authCode) {
        Result<YkzAccessToken> ykzAccessTokenResult = this.accessToken();
        if (!Objects.equals(ykzAccessTokenResult.getCode(), ResultEnum.SUCCESS.getCode())) {
            return Result.build(ykzAccessTokenResult.getCode(), ykzAccessTokenResult.getMsg());
        }
        //executableClient保证单例
        IntelligentPostClient intelligentPostClient = ykzConfig.getExecutableClient().newIntelligentPostClient(ykzProperties.getYkzApi().getTokenInfo());
        OapiRpcOauth2DingtalkAppTokenJsonRequest oapiRpcOauth2DingtalkAppTokenJsonRequest = new OapiRpcOauth2DingtalkAppTokenJsonRequest();
        //登录access_token
        oapiRpcOauth2DingtalkAppTokenJsonRequest.setAccess_token(ykzAccessTokenResult.getData().getAccessToken());
        //临时授权码
        oapiRpcOauth2DingtalkAppTokenJsonRequest.setAuth_code(authCode);
        //获取结果
        OapiRpcOauth2DingtalkAppTokenJsonResponse apiResult = intelligentPostClient.post(oapiRpcOauth2DingtalkAppTokenJsonRequest);
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        String content = apiResult.getContent();
        return getApiResult(content, YkzLoginToken.class);
    }

    @Override
    public Result<String> sendMsg(YkzSendMsg ykzSendMsg) {
        //executableClient保证单例
        IntelligentGetClient intelligentGetClient = ykzConfig.getExecutableClient().newIntelligentGetClient(ykzProperties.getYkzApi().getSendMsg());
        OapiChatSendMsgRequest oapiChatSendMsgRequest = new OapiChatSendMsgRequest();
        //消息体
        String msg = "";
        JSONObject jsonObject;
        switch (ykzSendMsg.getMsgType()) {
            case TEXT:
                YkzSendMsgText sendMsgText = ykzSendMsg.getTextDetail();
                jsonObject = new JSONObject();
                jsonObject.set("msgtype", YkzSendMsgTypeEnum.TEXT.getType());
                jsonObject.set("text", new JSONObject().set("content", sendMsgText.getContent()));
                msg = jsonObject.toString();
                break;
            case LINK:
                YkzSendMsgLink sendMsgLink = ykzSendMsg.getLinkDetail();
                jsonObject = new JSONObject();
                jsonObject.set("msgtype", YkzSendMsgTypeEnum.LINK.getType());
                jsonObject.set("link", new JSONObject().set("title", sendMsgLink.getTitle())
                        .set("messageUrl", sendMsgLink.getMessageUrl())
                        .set("text", sendMsgLink.getText())
                        .set("picMediaId", sendMsgLink.getPicMediaId())
                        .set("sourceUrl", sendMsgLink.getSourceUrl())
                );
                msg = jsonObject.toString();
                break;
            case IMAGE:
                YkzSendMsgImage sendMsgImage = ykzSendMsg.getImageDetail();
                jsonObject = new JSONObject();
                jsonObject.set("msgtype", YkzSendMsgTypeEnum.IMAGE.getType());
                jsonObject.set("image", new JSONObject().set("mediaId", sendMsgImage.getMediaId())
                        .set("fileType", sendMsgImage.getFileType())
                );
                msg = jsonObject.toString();
                break;
            case FILE:
                YkzSendMsgFile sendMsgFile = ykzSendMsg.getFileDetail();
                jsonObject = new JSONObject();
                jsonObject.set("msgtype", YkzSendMsgTypeEnum.FILE.getType());
                jsonObject.set("previewFile", new JSONObject().set("mediaId", sendMsgFile.getMediaId())
                        .set("size", sendMsgFile.getSize())
                        .set("size", sendMsgFile.getSize())
                        .set("name", sendMsgFile.getName())
                        .set("fileType", sendMsgFile.getFileType())
                );
                msg = jsonObject.toString();
                break;
        }
        oapiChatSendMsgRequest.setMsg(msg);
        //发送者用户id
        oapiChatSendMsgRequest.setSenderId(ykzSendMsg.getSenderId());
        //单聊接受者用户id（chatType为1时必填）
        oapiChatSendMsgRequest.setReceiverId(ykzSendMsg.getReceiverId());
        //群聊会话id（chatType为2时必填）
        oapiChatSendMsgRequest.setChatId(ykzSendMsg.getChatId());
        //租户id
        oapiChatSendMsgRequest.setTenantId(ykzProperties.getYkzApi().getTenantId());
        //发起的会话类型（1单聊、2群聊）
        oapiChatSendMsgRequest.setChatType(ykzSendMsg.getChatType());
        //获取结果
        OapiChatSendMsgResponse apiResult = intelligentGetClient.get(oapiChatSendMsgRequest);
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        String data = apiResult.getContent().getData();
        JSONObject json = new JSONObject(data);
        String contentJson = json.getJSONObject("data").getStr("messageId");
        return Result.ok(contentJson);
    }
}
