package com.efficient.ykz.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dcqc.uc.oauth.sdk.util.JwtHelper;
import com.dcqc.uc.oauth.sdk.util.SM2ToolUtil;
import com.efficient.common.result.Result;
import com.efficient.common.result.ResultEnum;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author TMW
 * @since 2024/1/4 15:38
 */
@Service
@Slf4j
public class YkzUserCenterServiceImpl implements YkzUserCenterService {
    public static Integer maxPageSize = 100;
    @Resource
    JwtHelper jwtHelper;
    @Autowired
    private YkzProperties ykzProperties;

    @Override
    public Result<YkzOrg> orgByCode(String orgCode) {
        JSONObject jsonObject = JSONUtil.createObj().set("organizationCode", orgCode);
        YkzOrg ykzOrg = this.sendRequestOne(ykzProperties.getUserCenter().getOrgByCode(), true, jsonObject, YkzOrg.class);
        return Objects.isNull(ykzOrg) ? Result.fail() : Result.ok(ykzOrg);
    }

    @Override
    public Result<List<YkzOrg>> orgByCodeList(List<String> orgCodeList) {
        if (CollUtil.isEmpty(orgCodeList)) {
            return Result.ok();
        }
        List<List<String>> splitList = com.efficient.common.util.CollUtil.splitList(orgCodeList, maxPageSize);
        List<YkzOrg> resultList = new ArrayList<>();
        for (List<String> list : splitList) {
            JSONObject jsonObject = JSONUtil.createObj().set("organizationCodes", list);
            List<YkzOrg> ykzOrgList = this.sendRequestList(ykzProperties.getUserCenter().getOrgByCodeList(), true, jsonObject, YkzOrg.class);
            resultList.addAll(ykzOrgList);
        }
        log.info("批量获取机构信息条数：{}", resultList.size());
        return CollUtil.isEmpty(resultList) ? Result.fail() : Result.ok(resultList);
    }

    @Override
    public Result<List<YkzOrg>> orgByParentCode(String orgCode, Integer pageNum, Integer pageSize, boolean includeTop) {
        List<YkzOrg> resultList = this.childOrg(orgCode, pageNum, pageSize);
        if (includeTop) {
            log.info("查询顶级节点数据：{}", orgCode);
            Result<YkzOrg> result = this.orgByCode(orgCode);
            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                resultList.add(result.getData());
            }
        }
        log.info("总共拉取机构数量：{}", resultList.size());
        return CollUtil.isEmpty(resultList) ? Result.fail() : Result.ok(resultList);
    }

    @Override
    public YkzUserCenterAccessToken getAccessToken(String appId, String appSecret) {
        if (StrUtil.isBlank(appId)) {
            appId = ykzProperties.getAppId();
        }
        if (StrUtil.isBlank(appSecret)) {
            appSecret = ykzProperties.getAppSecret();
        }
        JSONObject jsonObject = JSONUtil.createObj().set("appId", appId).set("appSecret", SM2ToolUtil.sm2Encode(jwtHelper.getPublicKey(), appSecret));
        return this.sendRequestOne(ykzProperties.getUserCenter().getAccessTokenUrl(), false, jsonObject, YkzUserCenterAccessToken.class);
    }

    @Override
    public YkzUserCenterAccessToken getAccessToken() {
        return this.getAccessToken(null, null);
    }

    @Override
    public <M> M sendRequestOne(String url, boolean hasToken, JSONObject params, Class<M> tClass) {
        YkzResult ykzResult = this.sendRequest(url, hasToken, params);
        if (Objects.isNull(ykzResult)) {
            return null;
        }
        return JackSonUtil.toObject(JackSonUtil.toJson(ykzResult.getData()), tClass);
    }

    public YkzResult sendRequest(String url, boolean hasToken, JSONObject params) {
        HttpRequest httpRequest = HttpRequest.post(ykzProperties.getIp() + url);
        httpRequest.contentType(YkzConstant.CONTENT_TYPE);
        YkzParam ykzParam = YkzParam.builder().requestId(IdUtil.uuid()).data(params).build();
        httpRequest.body(JackSonUtil.toJson(ykzParam));
        log.info("sendRequest 请求参数：{}", JackSonUtil.toJson(ykzParam));
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
        // if (Objects.isNull(ykzResult) || Objects.equals(Boolean.FALSE, ykzResult.getSuccess()) || Objects.isNull(ykzResult.getData())) {
        //     return null;
        // }
        return ykzResult;
    }

    @Override
    public <M> List<M> sendRequestList(String url, boolean hasToken, JSONObject params, Class<M> tClass) {
        HttpRequest httpRequest = HttpRequest.post(ykzProperties.getIp() + url);
        httpRequest.contentType(YkzConstant.CONTENT_TYPE);
        YkzParam ykzParam = YkzParam.builder().requestId(IdUtil.uuid()).data(params).build();
        httpRequest.body(JackSonUtil.toJson(ykzParam));
        log.info("sendRequestList 请求参数：{}", JackSonUtil.toJson(ykzParam));
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
        // if (Objects.isNull(ykzResult) || Objects.equals(Boolean.FALSE, ykzResult.getSuccess()) || Objects.isNull(ykzResult.getData())) {
        //     return null;
        // }
        return JackSonUtil.toObjectList(JackSonUtil.toJson(ykzResult.getData()), tClass);
    }

    @Override
    public Result<YkzUser> userByMobile(String phone) {
        JSONObject jsonObject = JSONUtil.createObj().set("mobile", phone);
        YkzUser ykzUser = this.sendRequestOne(ykzProperties.getUserCenter().getUserByMobile(), true, jsonObject, YkzUser.class);
        if (Objects.isNull(ykzUser)) {
            return Result.fail();
        }
        ykzUser.setPhone(phone);
        return Result.ok(ykzUser);
    }

    @Override
    public Result<List<YkzUserPost>> userPostByZwddId(String zwddId) {
        JSONObject jsonObject = JSONUtil.createObj().set("accountId", zwddId);
        List<YkzUserPost> ykzUserPostList = this.sendRequestList(ykzProperties.getUserCenter().getUserPostByZwddId(), true, jsonObject, YkzUserPost.class);
        log.info("获取人员职务信息条数：{}", ykzUserPostList.size());
        return CollUtil.isEmpty(ykzUserPostList) ? Result.fail() : Result.ok(ykzUserPostList);
    }

    public List<YkzOrg> childOrg(String orgCode, Integer pageNumber, Integer pageSize) {
        JSONObject jsonObject = JSONUtil.createObj().set("organizationCode", orgCode).set("pageNumber", pageNumber).set("pageSize", pageSize);
        List<YkzOrg> resultList = new ArrayList<>();
        YkzOrgPage ykzOrgPage = this.sendRequestOne(ykzProperties.getUserCenter().getOrgByParentCode(), true, jsonObject, YkzOrgPage.class);
        log.info("orgByParentCode-childOrg 当前页：{},每页数量：{},总数量：{},总页数：{}", ykzOrgPage.getPageNumber(), ykzOrgPage.getPageSize(), ykzOrgPage.getTotal(), ykzOrgPage.getTotalPage());
        List<YkzOrg> list = ykzOrgPage.getList();
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        resultList.addAll(list);

        Integer resultPageNumber = ykzOrgPage.getPageNumber();
        Integer resultTotalPage = ykzOrgPage.getTotalPage();
        if (resultPageNumber < resultTotalPage) {
            for (Integer i = resultPageNumber + 1; i <= resultTotalPage; i++) {
                List<YkzOrg> ykzOrgs = this.childOrg(orgCode, i, pageSize);
                resultList.addAll(ykzOrgs);
            }
        }

        if (CollUtil.isNotEmpty(list)) {
            for (YkzOrg ykzOrg : list) {
                List<YkzOrg> ykzOrgList = this.childOrg(ykzOrg.getOrganizationCode(), 1, pageSize);
                if (CollUtil.isNotEmpty(ykzOrgList)) {
                    resultList.addAll(ykzOrgList);
                }
            }
        }
        return resultList;
    }

}
