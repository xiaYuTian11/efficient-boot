package com.efficient.ykz.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
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
import com.efficient.ykz.util.YkzUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author TMW
 * @since 2024/1/4 15:38
 */
@Service
@Slf4j
public class YkzUserCenterServiceImpl implements YkzUserCenterService {
    private static final TransmittableThreadLocal<Result> YKZ_ERROR_MSG = new TransmittableThreadLocal<>();
    public static Integer maxPageSize = 100;
    private static Date expiresInDate = null;
    private static String accessToken = null;
    @Resource
    JwtHelper jwtHelper;
    @Autowired
    private YkzProperties ykzProperties;

    @Override
    public Result<YkzOrg> orgByCode(String orgCode) {
        JSONObject jsonObject = JSONUtil.createObj().set("organizationCode", orgCode);
        YkzOrg ykzOrg = this.sendRequestOne(ykzProperties.getUserCenter().getOrgByCode(), true, jsonObject, YkzOrg.class);
        return Objects.isNull(ykzOrg) ? YKZ_ERROR_MSG.get() : Result.ok(ykzOrg);
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
            if (CollUtil.isNotEmpty(ykzOrgList)) {
                resultList.addAll(ykzOrgList);
            }

        }
        log.info("批量获取机构信息条数：{}", resultList.size());
        return CollUtil.isEmpty(resultList) ? YKZ_ERROR_MSG.get() : Result.ok(resultList);
    }

    @Override
    public Result<List<YkzOrg>> orgByParentCode(String orgCode, Integer pageNum, Integer pageSize, boolean includeTop, boolean flattenTree) {
        TimeInterval timeInterval = DateUtil.timer();
        // timeInterval.start();
        List<YkzOrg> resultList = this.childOrg(orgCode, pageNum, pageSize);
        log.info("查询顶级节点数据：{}", orgCode);
        if (!StrUtil.equalsAny(orgCode, YkzConstant.YKZ_ORG_TOP_CODE_DEV, YkzConstant.YKZ_ORG_TOP_CODE) && includeTop) {
            Result<YkzOrg> result = this.orgByCode(orgCode);
            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                resultList.add(result.getData());
            }
        }

        log.info("总共拉取机构数量：{},耗时：{} s", resultList.size(), timeInterval.interval() / 1000);
        List<YkzOrg> ykzOrgList = YkzUtil.createTree(resultList, flattenTree);
        return CollUtil.isEmpty(ykzOrgList) ? Result.fail() : Result.ok(ykzOrgList);
    }

    @Override
    public YkzAccessToken getAccessToken(String appId, String appSecret) {
        if (StrUtil.isBlank(appId)) {
            appId = ykzProperties.getUserCenter().getAppId();
        }
        if (StrUtil.isBlank(appSecret)) {
            appSecret = ykzProperties.getUserCenter().getAppSecret();
        }
        Date now = new Date();
        JSONObject jsonObject = JSONUtil.createObj().set("appId", appId).set("appSecret", SM2ToolUtil.sm2Encode(jwtHelper.getPublicKey(), appSecret));
        YkzAccessToken userCenterAccessToken = this.sendRequestOne(ykzProperties.getUserCenter().getAccessTokenUrl(), false, jsonObject, YkzAccessToken.class);
        accessToken = userCenterAccessToken.getAccessToken();
        expiresInDate = DateUtil.offsetSecond(now, userCenterAccessToken.getExpiresIn() - 30);
        return userCenterAccessToken;
    }

    @Override
    public YkzAccessToken getAccessToken() {
        return this.getAccessToken(null, null);
    }

    @Override
    public <M> M sendRequestOne(String url, boolean hasToken, JSONObject params, Class<M> tClass) {
        YkzResult ykzResult = this.sendRequest(url, hasToken, params);
        if (Objects.isNull(ykzResult)) {
            return null;
        }
        if (Objects.equals(Boolean.FALSE, ykzResult.getSuccess()) || Objects.isNull(ykzResult.getData())) {
            YKZ_ERROR_MSG.set(Result.build(ykzResult.getStatus(), ykzResult.getMessage()));
            return null;
        }
        return JackSonUtil.toObject(JackSonUtil.toJson(ykzResult.getData()), tClass);
    }

    public YkzResult sendRequest(String url, boolean hasToken, JSONObject params) {
        HttpRequest httpRequest = HttpRequest.post(ykzProperties.getUserCenter().getIp() + url);
        httpRequest.contentType(YkzConstant.CONTENT_TYPE);
        YkzParam ykzParam = YkzParam.builder().requestId(IdUtil.uuid()).data(params).build();
        httpRequest.body(JackSonUtil.toJson(ykzParam));
        log.info("sendRequest 请求参数：{}", JackSonUtil.toJson(ykzParam));
        if (hasToken) {
            String token = this.setRequestHeader();
            httpRequest.header(YkzConstant.HEADER_AUTHORIZATION, YkzConstant.HEADER_TOKEN_BEARER + token);
        }
        HttpResponse response = httpRequest.execute();
        log.info("{} 结果数据： {}", url, response.body());
        YkzResult ykzResult = JackSonUtil.toObject(response.body(), YkzResult.class);
        return ykzResult;
    }

    @Override
    public <M> List<M> sendRequestList(String url, boolean hasToken, JSONObject params, Class<M> tClass) {
        HttpRequest httpRequest = HttpRequest.post(ykzProperties.getUserCenter().getIp() + url);
        httpRequest.contentType(YkzConstant.CONTENT_TYPE);
        YkzParam ykzParam = YkzParam.builder().requestId(IdUtil.uuid()).data(params).build();
        httpRequest.body(JackSonUtil.toJson(ykzParam));
        log.info("sendRequestList 请求参数：{}", JackSonUtil.toJson(ykzParam));
        if (hasToken) {
            String token = this.setRequestHeader();
            httpRequest.header(YkzConstant.HEADER_AUTHORIZATION, YkzConstant.HEADER_TOKEN_BEARER + token);
        }
        HttpResponse response = httpRequest.execute();
        log.info("{} 结果数据： {}", url, response.body());
        YkzResult ykzResult = JackSonUtil.toObject(response.body(), YkzResult.class);
        if (Objects.isNull(ykzResult) || Objects.equals(Boolean.FALSE, ykzResult.getSuccess()) || Objects.isNull(ykzResult.getData())) {
            if (Objects.nonNull(ykzResult)) {
                YKZ_ERROR_MSG.set(Result.build(ykzResult.getStatus(), ykzResult.getMessage()));
            }
            return null;
        }
        return JackSonUtil.toObjectList(JackSonUtil.toJson(ykzResult.getData()), tClass);
    }

    @Override
    public Result<YkzUser> userByMobile(String phone) {
        JSONObject jsonObject = JSONUtil.createObj().set("mobile", phone);
        YkzUser ykzUser = this.sendRequestOne(ykzProperties.getUserCenter().getUserByMobile(), true, jsonObject, YkzUser.class);
        if (Objects.isNull(ykzUser)) {
            return YKZ_ERROR_MSG.get();
        }
        ykzUser.setMobile(phone);
        return Result.ok(ykzUser);
    }

    @Override
    public Result<List<YkzUserPost>> userPostByZwddId(String zwddId) {
        JSONObject jsonObject = JSONUtil.createObj().set("accountId", zwddId);
        List<YkzUserPost> ykzUserPostList = this.sendRequestList(ykzProperties.getUserCenter().getUserPostByZwddId(), true, jsonObject, YkzUserPost.class);
        log.info("获取人员职务信息条数：{}", ykzUserPostList.size());
        return CollUtil.isEmpty(ykzUserPostList) ? YKZ_ERROR_MSG.get() : Result.ok(ykzUserPostList);
    }

    @Override
    public Result<List<YkzUser>> userByMobileList(List<String> phoneList) {
        JSONObject jsonObject = JSONUtil.createObj().set("mobiles", phoneList);
        List<YkzUser> ykzUserList = this.sendRequestList(ykzProperties.getUserCenter().getUserByMobileList(), true, jsonObject, YkzUser.class);
        if (CollUtil.isEmpty(ykzUserList)) {
            return YKZ_ERROR_MSG.get();
        }

        return Result.ok(ykzUserList);
    }

    @Override
    public Result<YkzUser> userByZwddId(String zwddId) {
        JSONObject jsonObject = JSONUtil.createObj().set("accountId", zwddId);
        YkzUser ykzUser = this.sendRequestOne(ykzProperties.getUserCenter().getUserByZwddId(), true, jsonObject, YkzUser.class);
        if (Objects.isNull(ykzUser)) {
            return YKZ_ERROR_MSG.get();
        }
        return Result.ok(ykzUser);
    }

    @Override
    public Result<List<YkzUser>> userByZwddIdList(List<String> zwddIdList) {
        JSONObject jsonObject = JSONUtil.createObj().set("accountIds", zwddIdList);
        List<YkzUser> ykzUserList = this.sendRequestList(ykzProperties.getUserCenter().getUserByZwddIdList(), true, jsonObject, YkzUser.class);
        if (CollUtil.isEmpty(ykzUserList)) {
            return YKZ_ERROR_MSG.get();
        }
        return Result.ok(ykzUserList);
    }

    @Override
    public Result<YkzLabel> userTagByZwddId(String zwddId) {
        JSONObject jsonObject = JSONUtil.createObj().set("accountId", zwddId);
        YkzLabel ykzLabel = this.sendRequestOne(ykzProperties.getUserCenter().getUserTagByZwddId(), true, jsonObject, YkzLabel.class);
        if (Objects.isNull(ykzLabel)) {
            return YKZ_ERROR_MSG.get();
        }
        return Result.ok(ykzLabel);
    }

    @Override
    public Result<YkzLabel> userTagByMobile(String phone) {
        JSONObject jsonObject = JSONUtil.createObj().set("mobile", phone);
        YkzLabel ykzLabel = this.sendRequestOne(ykzProperties.getUserCenter().getUserTagByMobile(), true, jsonObject, YkzLabel.class);
        if (Objects.isNull(ykzLabel)) {
            return YKZ_ERROR_MSG.get();
        }
        return Result.ok(ykzLabel);
    }

    @Override
    public Result<List<YkzLabel>> userTagByZwddIdList(List<String> zwddIdList) {
        JSONObject jsonObject = JSONUtil.createObj().set("accountIds", zwddIdList);
        List<YkzLabel> ykzLabelList = this.sendRequestList(ykzProperties.getUserCenter().getUserTagByZwddIdList(), true, jsonObject, YkzLabel.class);
        if (CollUtil.isEmpty(ykzLabelList)) {
            return YKZ_ERROR_MSG.get();
        }
        return Result.ok(ykzLabelList);
    }

    @Override
    public Result<List<YkzLabel>> userTagByMobileList(List<String> phoneList) {
        JSONObject jsonObject = JSONUtil.createObj().set("mobiles", phoneList);
        List<YkzLabel> ykzLabelList = this.sendRequestList(ykzProperties.getUserCenter().getUserTagByMobileList(), true, jsonObject, YkzLabel.class);
        if (CollUtil.isEmpty(ykzLabelList)) {
            return YKZ_ERROR_MSG.get();
        }
        return Result.ok(ykzLabelList);
    }

    private String setRequestHeader() {
        log.info("accessToken: {},expiresInDate: {}", accessToken, expiresInDate);
        if (Objects.nonNull(expiresInDate) && Objects.nonNull(accessToken) && DateUtil.between(new Date(), expiresInDate, DateUnit.SECOND, false) >= 0) {
            return accessToken;
        } else {
            YkzAccessToken centerAccessToken = this.getAccessToken();
            if (Objects.isNull(centerAccessToken)) {
                return null;
            }
            return centerAccessToken.getAccessToken();
        }
    }

    public List<YkzOrg> childOrg(String orgCode, Integer pageNumber, Integer pageSize) {
        JSONObject jsonObject = JSONUtil.createObj().set("organizationCode", orgCode).set("pageNumber", pageNumber).set("pageSize", pageSize);
        YkzOrgPage ykzOrgPage = this.sendRequestOne(ykzProperties.getUserCenter().getOrgByParentCode(), true, jsonObject, YkzOrgPage.class);
        log.info("orgByParentCode-childOrg 当前页：{},每页数量：{},总数量：{},总页数：{}", ykzOrgPage.getPageNumber(), ykzOrgPage.getPageSize(), ykzOrgPage.getTotal(), ykzOrgPage.getTotalPage());
        List<YkzOrg> list = ykzOrgPage.getList();
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        CopyOnWriteArrayList<YkzOrg> resultList = new CopyOnWriteArrayList<>(list);

        Integer resultPageNumber = ykzOrgPage.getPageNumber();
        Integer resultTotalPage = ykzOrgPage.getTotalPage();
        if (resultPageNumber < resultTotalPage) {
            for (Integer i = resultPageNumber + 1; i <= resultTotalPage; i++) {
                List<YkzOrg> ykzOrgs = this.childOrg(orgCode, i, pageSize);
                resultList.addAll(ykzOrgs);
            }
        }

        if (CollUtil.isNotEmpty(list)) {
            // list.parallelStream().map(YkzOrg::getOrganizationCode)
            //         .map(et -> this.childOrg(et, 1, pageSize))
            //         .forEach(resultList::addAll);

            list.parallelStream().forEach(et -> {
                List<YkzOrg> ykzOrgList = this.childOrg(et.getOrganizationCode(), 1, pageSize);
                if (CollUtil.isNotEmpty(ykzOrgList)) {
                    resultList.addAll(ykzOrgList);
                }
            });

            // for (YkzOrg ykzOrg : list) {
            //     List<YkzOrg> ykzOrgList = this.childOrg(ykzOrg.getOrganizationCode(), 1, pageSize);
            //     if (CollUtil.isNotEmpty(ykzOrgList)) {
            //         resultList.addAll(ykzOrgList);
            //     }
            // }
        }
        return resultList;
    }

}
