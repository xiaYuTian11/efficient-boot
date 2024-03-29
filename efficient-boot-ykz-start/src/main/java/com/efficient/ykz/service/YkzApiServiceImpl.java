package com.efficient.ykz.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.xxpt.gateway.shared.api.request.*;
import com.alibaba.xxpt.gateway.shared.api.response.*;
import com.alibaba.xxpt.gateway.shared.client.http.IntelligentGetClient;
import com.alibaba.xxpt.gateway.shared.client.http.IntelligentPostClient;
import com.alibaba.xxpt.gateway.shared.client.http.api.OapiSpResultContent;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.AESUtils;
import com.efficient.common.util.JackSonUtil;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.config.YkzConfig;
import com.efficient.ykz.constant.YkzConstant;
import com.efficient.ykz.constant.YkzSendMsgTypeEnum;
import com.efficient.ykz.exception.YkzException;
import com.efficient.ykz.model.dto.msg.*;
import com.efficient.ykz.model.dto.todo.YkzTodoInfo;
import com.efficient.ykz.model.dto.worknotice.*;
import com.efficient.ykz.model.vo.YkzAccessToken;
import com.efficient.ykz.model.vo.YkzLoginToken;
import com.efficient.ykz.model.vo.YkzLoginUser;
import com.efficient.ykz.model.vo.YkzTodoInfoVO;
import com.efficient.ykz.properties.YkzApi;
import com.efficient.ykz.properties.YkzProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
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
        boolean success = json.getBool("success");
        if (!success) {
            return Result.build(ResultEnum.FAILED.getCode(),
                    json.getStr("errorCode") + ": " + json.getStr("errorMsg"));
        }
        JSONObject contentJson = json.getJSONObject("content");
        Boolean success2 = contentJson.getBool("success");
        if (Objects.nonNull(success2) && !success2) {
            return Result.build(ResultEnum.FAILED.getCode(),
                    contentJson.getStr("responseCode") + ": " + contentJson.getStr("responseMessage"));
        }
        String data = contentJson.getStr("data");
        if (StrUtil.isBlank(data)) {
            data = contentJson.getStr("msgId");
        }

        return Result.ok(JackSonUtil.toObject(data, tClass));
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
        log.info("accessToken 结果数据： {}", JackSonUtil.toJson(apiResult));
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
        log.info("getUserInfo 结果数据： {}", JackSonUtil.toJson(apiResult));
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
        log.info("getTokenInfo 结果数据： {}", JackSonUtil.toJson(apiResult));
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
        Boolean winOpen = ykzProperties.getYkzApi().getWinOpen();
        YkzSendMsgTypeEnum msgType = ykzSendMsg.getMsgType();
        if (Objects.isNull(msgType)) {
            throw new YkzException("msgType 参数错误！");
        }
        switch (msgType) {
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
                String messageUrl = sendMsgLink.getMessageUrl();
                if (Objects.equals(winOpen, Boolean.TRUE)) {
                    messageUrl = YkzConstant.WIN_OPEN + messageUrl;
                }
                jsonObject.set("link", new JSONObject().set("title", sendMsgLink.getTitle())
                        .set("messageUrl", messageUrl)
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
        log.info("sendMsg 结果数据： {}", JackSonUtil.toJson(apiResult));
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        String data = apiResult.getContent().getData();
        JSONObject json = new JSONObject(data);
        String contentJson = json.getStr("messageId");
        return Result.ok(contentJson);
    }

    @Override
    public Result<String> sendWorkNotice(YkzWorkNotice ykzWorkNotice) {
        // executableClient保证单例
        IntelligentGetClient intelligentGetClient = ykzConfig.getExecutableClient().newIntelligentGetClient(ykzProperties.getYkzApi().getSendWorkNotice());
        OapiMessageWorkNotificationRequest oapiMessageWorkNotificationRequest = new OapiMessageWorkNotificationRequest();
        //接收者的部门id列表
        oapiMessageWorkNotificationRequest.setOrganizationCodes(ykzWorkNotice.getOrganizationCodes());
        //接收人用户ID
        oapiMessageWorkNotificationRequest.setReceiverIds(ykzWorkNotice.getReceiverIds());
        //租户ID
        oapiMessageWorkNotificationRequest.setTenantId(String.valueOf(ykzProperties.getYkzApi().getTenantId()));
        //业务消息id
        oapiMessageWorkNotificationRequest.setBizMsgId(ykzWorkNotice.getBizMsgId());
        //消息对象
        String msg = ykzWorkNotice.getMsg();
        YkzSendMsgTypeEnum msgType = ykzWorkNotice.getMsgType();
        JSONObject jsonObject;
        Boolean winOpen = ykzProperties.getYkzApi().getWinOpen();
        if (Objects.isNull(msgType)) {
            throw new YkzException("msgType 参数错误！");
        }
        switch (msgType) {
            case TEXT:
                YkzWorkNoticeMsgText workNoticeMsgText = ykzWorkNotice.getMsgText();
                jsonObject = new JSONObject();
                jsonObject.set("msgtype", YkzSendMsgTypeEnum.TEXT.getType());
                jsonObject.set(YkzSendMsgTypeEnum.TEXT.getType(),
                        new JSONObject().set("content", workNoticeMsgText.getContent())
                );
                msg = jsonObject.toString();
                break;
            case LINK:
                YkzWorkNoticeMsgLink workNoticeMsgLink = ykzWorkNotice.getMsgLink();
                jsonObject = new JSONObject();
                jsonObject.set("msgtype", YkzSendMsgTypeEnum.LINK.getType());
                String messageUrl = workNoticeMsgLink.getMessageUrl();
                if (Objects.equals(winOpen, Boolean.TRUE)) {
                    messageUrl = YkzConstant.WIN_OPEN + messageUrl;
                }
                jsonObject.set(YkzSendMsgTypeEnum.LINK.getType(),
                        new JSONObject().set("messageUrl", messageUrl)
                                .set("picUrl", workNoticeMsgLink.getPicUrl())
                                .set("title", workNoticeMsgLink.getTitle())
                                .set("text", workNoticeMsgLink.getText())
                );
                msg = jsonObject.toString();
                break;
            case MARKDOWN:
                YkzWorkNoticeMsgMarkdown workNoticeMsgMarkdown = ykzWorkNotice.getMsgMarkdown();
                jsonObject = new JSONObject();
                jsonObject.set("msgtype", YkzSendMsgTypeEnum.LINK.getType());
                jsonObject.set(YkzSendMsgTypeEnum.LINK.getType(),
                        new JSONObject().set("title", workNoticeMsgMarkdown.getTitle())
                                .set("text", workNoticeMsgMarkdown.getText())
                );
                msg = jsonObject.toString();
                break;
            default:
                throw new YkzException("msgType 参数错误！");
        }
        oapiMessageWorkNotificationRequest.setMsg(msg);
        //获取结果
        OapiMessageWorkNotificationResponse apiResult = intelligentGetClient.get(oapiMessageWorkNotificationRequest);
        log.info("sendWorkNotice 结果数据： {}", JackSonUtil.toJson(apiResult));
        String content = apiResult.getContent();
        return this.getApiResult(content, String.class);
    }

    @Override
    public Result<String> revokeWorkNotice(YkzWorkNoticeBackOut ykzWorkNotice) {
        IntelligentGetClient intelligentGetClient = ykzConfig.getExecutableClient().newIntelligentGetClient(ykzProperties.getYkzApi().getRevokeWorkNotice());
        OapiMessageRevokeRequest oapiMessageRevokeRequest = new OapiMessageRevokeRequest();
        //消息类型，固定填写：workNotification
        oapiMessageRevokeRequest.setMsgType(ykzWorkNotice.getMsgType());
        //撤回的应用，填写：应用标识
        oapiMessageRevokeRequest.setMsgApp(ykzWorkNotice.getMsgApp());
        //租户
        oapiMessageRevokeRequest.setTenantId(String.valueOf(ykzProperties.getYkzApi().getTenantId()));
        //消息id
        oapiMessageRevokeRequest.setBizMsgId(ykzWorkNotice.getBizMsgId());
        //获取结果
        OapiMessageRevokeResponse apiResult = intelligentGetClient.get(oapiMessageRevokeRequest);
        log.info("revokeWorkNotice 结果数据： {}", JackSonUtil.toJson(apiResult));
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        String data = apiResult.getContent().getData();

        return StrUtil.equalsIgnoreCase("true", data) ? Result.ok() : Result.fail();
    }

    @Override
    public Result<YkzTodoInfoVO> createTodo(YkzTodoInfo todoInfo) {
        //executableClient保证单例
        IntelligentGetClient intelligentGetClient = ykzConfig.getExecutableClient().newIntelligentGetClient(ykzProperties.getYkzApi().getCreateTodo());
        OapiTcV2OpenapiTaskCreateJsonRequest oapiTcV2OpenapiTaskCreateJsonRequest = new OapiTcV2OpenapiTaskCreateJsonRequest();
        //标题
        oapiTcV2OpenapiTaskCreateJsonRequest.setSubject(todoInfo.getSubject());
        //创建人ID
        oapiTcV2OpenapiTaskCreateJsonRequest.setCreatorId(todoInfo.getCreatorId());
        //租户ID
        oapiTcV2OpenapiTaskCreateJsonRequest.setTenantId(String.valueOf(ykzProperties.getYkzApi().getTenantId()));
        //业务系统自定义ID
        oapiTcV2OpenapiTaskCreateJsonRequest.setBizTaskId(todoInfo.getBizTaskId());
        //URL
        Boolean winOpen = ykzProperties.getYkzApi().getWinOpen();
        String url = todoInfo.getUrl();
        UserTicket userTicket = new UserTicket();
        userTicket.setZwddId(todoInfo.getAssigneeId());
        userTicket.setCreateTime(new Date().getTime());
        String token = URLEncodeUtil.encodeAll(AESUtils.encrypt(JackSonUtil.toJson(userTicket)));
        String bizTaskId = todoInfo.getBizTaskId();
        if (StrUtil.isNotBlank(url)) {
            url = url + "&authCode=" + token + "&bizTaskId=" + bizTaskId;
        }
        String mobileUrl = todoInfo.getMobileUrl();
        if (StrUtil.isNotBlank(mobileUrl)) {
            mobileUrl = mobileUrl + "&authCode=" + token + "&bizTaskId=" + bizTaskId;
        }

        if (Objects.equals(winOpen, Boolean.TRUE)) {
            url = YkzConstant.WIN_OPEN + URLEncodeUtil.encodeAll(url);
        }

        oapiTcV2OpenapiTaskCreateJsonRequest.setUrl(url);
        //移动端URL
        oapiTcV2OpenapiTaskCreateJsonRequest.setMobileUrl(mobileUrl);
        //模板code
        oapiTcV2OpenapiTaskCreateJsonRequest.setTemplateCode(todoInfo.getTemplateCode());
        //待办人ID
        oapiTcV2OpenapiTaskCreateJsonRequest.setAssigneeId(todoInfo.getAssigneeId());
        oapiTcV2OpenapiTaskCreateJsonRequest.setIsSendDynamicCard(todoInfo.getIsSendDynamicCard());
        oapiTcV2OpenapiTaskCreateJsonRequest.setIsSendWindowNotice(todoInfo.getIsSendWindowNotice());
        oapiTcV2OpenapiTaskCreateJsonRequest.setDueNotifyLevel(todoInfo.getDueNotifyLevel());
        oapiTcV2OpenapiTaskCreateJsonRequest.setDueNotifyTypeArr(todoInfo.getDueNotifyTypeArr());
        oapiTcV2OpenapiTaskCreateJsonRequest.setCategory(todoInfo.getCategory());
        oapiTcV2OpenapiTaskCreateJsonRequest.setPackageUuid(todoInfo.getPackageUuid());
        if (Objects.nonNull(todoInfo.getDueTime())) {
            oapiTcV2OpenapiTaskCreateJsonRequest.setDueTime(DateUtil.formatDateTime(todoInfo.getDueTime()));
        }
        oapiTcV2OpenapiTaskCreateJsonRequest.setSubTypeCode(todoInfo.getSubTypeCode());
        //传了模板code的情况下，待办列表展示formValues数据，格式  {     "componentId1":"value1",     "componentId2":"value2"  }  componentId来自与创建模板时的组件ID
        oapiTcV2OpenapiTaskCreateJsonRequest.setFormValues(todoInfo.getFormValues());
        //创建人信息
        oapiTcV2OpenapiTaskCreateJsonRequest.setCreatorInfo(todoInfo.getCreatorInfo());
        oapiTcV2OpenapiTaskCreateJsonRequest.setActionBindingJson(todoInfo.getActionBindingJson());
        //获取结果
        OapiTcV2OpenapiTaskCreateJsonResponse apiResult = intelligentGetClient.get(oapiTcV2OpenapiTaskCreateJsonRequest);
        String json = JackSonUtil.toJson(apiResult);
        log.info("createTodo 结果数据： {}", json);
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        // Boolean success = apiResult.getContent().getSuccess();
        String data = apiResult.getContent().getData();
        return StrUtil.isNotBlank(data) ? Result.ok(JackSonUtil.toObject(data, YkzTodoInfoVO.class)) : Result.build(ResultEnum.FAILED.getCode(), "请检查bizTaskId是否重复或其他参数是否正确！");
    }

    @Override
    public Result<String> finishTodo(String assigneeId, String taskUuid, boolean closePackage) {
        //executableClient保证单例
        IntelligentGetClient intelligentGetClient = ykzConfig.getExecutableClient().newIntelligentGetClient(ykzProperties.getYkzApi().getFinishTodo());
        OapiTcOpenapiTaskFinishJsonRequest oapiTcOpenapiTaskFinishJsonRequest = new OapiTcOpenapiTaskFinishJsonRequest();
        //同步处理实例
        oapiTcOpenapiTaskFinishJsonRequest.setClosePackage(closePackage);
        //用户ID
        oapiTcOpenapiTaskFinishJsonRequest.setUserId(assigneeId);
        //任务唯一ID
        oapiTcOpenapiTaskFinishJsonRequest.setTaskUuid(taskUuid);
        //获取结果
        OapiTcOpenapiTaskFinishJsonResponse apiResult = intelligentGetClient.get(oapiTcOpenapiTaskFinishJsonRequest);
        log.info("finishTodo 结果数据： {}", JackSonUtil.toJson(apiResult));
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        String data = apiResult.getContent().getData();
        return StrUtil.equals(data, "true") ? Result.ok() : Result.fail();
    }

    @Override
    public Result<String> cancelTodo(String assigneeId, String taskUuid, boolean closePackage) {
        //executableClient保证单例
        IntelligentGetClient intelligentGetClient = ykzConfig.getExecutableClient().newIntelligentGetClient(ykzProperties.getYkzApi().getCancelTodo());
        OapiTcOpenapiTaskCancelJsonRequest oapiTcOpenapiTaskCancelJsonRequest = new OapiTcOpenapiTaskCancelJsonRequest();
        //同步处理实例
        oapiTcOpenapiTaskCancelJsonRequest.setCancelPakcage(closePackage);
        //用户ID
        oapiTcOpenapiTaskCancelJsonRequest.setUserId(assigneeId);
        //任务唯一ID
        oapiTcOpenapiTaskCancelJsonRequest.setTaskUuid(taskUuid);
        //获取结果
        OapiTcOpenapiTaskCancelJsonResponse apiResult = intelligentGetClient.get(oapiTcOpenapiTaskCancelJsonRequest);
        log.info("cancelTodo 结果数据： {}", JackSonUtil.toJson(apiResult));
        if (!apiResult.getSuccess()) {
            return Result.build(ResultEnum.FAILED.getCode(), apiResult.getMessage());
        }
        String data = apiResult.getContent().getData();
        return StrUtil.equals(data, "true") ? Result.ok() : Result.fail();
    }
}
