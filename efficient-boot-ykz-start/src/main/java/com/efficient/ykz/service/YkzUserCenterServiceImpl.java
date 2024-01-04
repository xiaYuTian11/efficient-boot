package com.efficient.ykz.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dcqc.uc.oauth.sdk.util.JwtHelper;
import com.dcqc.uc.oauth.sdk.util.SM2ToolUtil;
import com.efficient.common.result.Result;
import com.efficient.common.util.IdUtil;
import com.efficient.common.util.JackSonUtil;
import com.efficient.ykz.api.YkzUserCenterService;
import com.efficient.ykz.constant.YkzConstant;
import com.efficient.ykz.model.dto.YkzParam;
import com.efficient.ykz.model.vo.*;
import com.efficient.ykz.properties.YkzProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author TMW
 * @since 2024/1/4 15:38
 */
@Service
@Slf4j
public class YkzUserCenterServiceImpl implements YkzUserCenterService {
    @Resource
    JwtHelper jwtHelper;
    @Autowired
    private YkzProperties ykzProperties;

    @Override
    public Result orgByCode(String orgCode) {
        JSONObject jsonObject = JSONUtil.createObj().set("organizationCode", orgCode);
        YkzOrg ykzOrg = this.sendRequest(ykzProperties.getUserCenter().getOrgByCode(), true, jsonObject, YkzOrg.class);
        return Objects.isNull(ykzOrg) ? Result.fail() : Result.ok(ykzOrg);
    }

    @Override
    public YkzUserCenterAccessToken getAccessToken(String appId, String appSecret) {
        if (StrUtil.isBlank(appId)) {
            appId = ykzProperties.getIp();
        }
        if (StrUtil.isBlank(appSecret)) {
            appSecret = ykzProperties.getAppSecret();
        }
        JSONObject jsonObject = JSONUtil.createObj().set("appId", appId).set("appSecret", SM2ToolUtil.sm2Encode(jwtHelper.getPublicKey(), appSecret));
        return this.sendRequest(ykzProperties.getUserCenter().getAccessTokenUrl(), false, jsonObject, YkzUserCenterAccessToken.class);
    }

    @Override
    public YkzUserCenterAccessToken getAccessToken() {
        return this.getAccessToken(null, null);
    }

    @Override
    public <M> M sendRequest(String url, JSONObject params, Class<M> tClass) {
        HttpRequest httpRequest = HttpRequest.post(ykzProperties.getIp() + url);
        httpRequest.contentType(YkzConstant.CONTENT_TYPE);
        YkzParam ykzParam = YkzParam.builder().requestId(IdUtil.uuid()).data(params.toString()).build();
        httpRequest.body(JackSonUtil.toJson(ykzParam));

        HttpResponse response = httpRequest.execute();
        log.info("{} 结果数据： {}", url, response.body());
        YkzResult ykzResult = JackSonUtil.toObject(response.body(), YkzResult.class);
        if (Objects.isNull(ykzResult) || Objects.equals(Boolean.FALSE, ykzResult.getSuccess()) || Objects.isNull(ykzResult.getData())) {
            return null;
        }
        return JackSonUtil.toObject(String.valueOf(ykzResult.getData()), tClass);
    }

    @Override
    public <M> M sendRequest(String url, boolean hasToken, JSONObject params, Class<M> tClass) {
        HttpRequest httpRequest = HttpRequest.post(ykzProperties.getIp() + url);
        httpRequest.contentType(YkzConstant.CONTENT_TYPE);
        YkzParam ykzParam = YkzParam.builder().requestId(IdUtil.uuid()).data(params.toString()).build();
        httpRequest.body(JackSonUtil.toJson(ykzParam));
        if (hasToken) {
            YkzUserCenterAccessToken centerAccessToken = this.getAccessToken();
            if (Objects.isNull(centerAccessToken)) {
                return null;
            }
            httpRequest.header(YkzConstant.HEADER_AUTHORIZATION, YkzConstant.HEADER_TOKEN_BEARER + centerAccessToken.getAccessToken());
        }
        HttpResponse response = httpRequest.execute();
        log.info("{} 结果数据： {}", url, response.body());
        YkzResult ykzResult = JackSonUtil.toObject(response.body(), YkzResult.class);
        if (Objects.isNull(ykzResult) || Objects.equals(Boolean.FALSE, ykzResult.getSuccess()) || Objects.isNull(ykzResult.getData())) {
            return null;
        }
        return JackSonUtil.toObject(String.valueOf(ykzResult.getData()), tClass);
    }

    @Override
    public Result userByMobile(String phone) {
        JSONObject jsonObject = JSONUtil.createObj().set("mobile", phone);
        YkzUser ykzUser = this.sendRequest(ykzProperties.getUserCenter().getUserByMobile(), true, jsonObject, YkzUser.class);
        if (Objects.isNull(ykzUser)) {
            return Result.fail();
        }
        ykzUser.setPhone(phone);
        return Result.ok(ykzUser);
    }

    @Override
    public Result userPostByZwddId(String zwddId) {
        JSONObject jsonObject = JSONUtil.createObj().set("accountId", zwddId);
        YkzUserPost ykzUserPost = this.sendRequest(ykzProperties.getUserCenter().getUserByMobile(), true, jsonObject, YkzUserPost.class);
        return Objects.isNull(ykzUserPost) ? Result.fail() : Result.ok(ykzUserPost);
    }
}
