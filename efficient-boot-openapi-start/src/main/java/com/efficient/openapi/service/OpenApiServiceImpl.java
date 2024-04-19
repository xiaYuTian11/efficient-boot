package com.efficient.openapi.service;

import cn.hutool.core.date.DateUtil;
import com.efficient.auth.api.LoginService;
import com.efficient.auth.constant.AuthConstant;
import com.efficient.auth.constant.LoginTypeEnum;
import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.auth.util.JwtUtil;
import com.efficient.cache.api.CacheUtil;
import com.efficient.cache.constant.CacheConstant;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;
import com.efficient.common.util.AESUtils;
import com.efficient.common.util.IdUtil;
import com.efficient.common.util.JackSonUtil;
import com.efficient.common.util.RsaUtil;
import com.efficient.openapi.api.OpenApiService;
import com.efficient.openapi.constant.OpenApiConstant;
import com.efficient.openapi.entity.OpenApiAccessTokenRequest;
import com.efficient.openapi.entity.OpenApiToken;
import com.efficient.openapi.properties.OpenApiProperties;
import com.efficient.system.api.SysApplicationService;
import com.efficient.system.model.vo.SysApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author TMW
 * @since 2024/4/16 16:17
 */
@Service
public class OpenApiServiceImpl implements OpenApiService {
    @Autowired
    private SysApplicationService applicationService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OpenApiProperties openApiProperties;
    @Autowired
    private LoginService loginService;
    @Autowired
    private AuthProperties authProperties;

    @Override
    public Result<String> getAccessToken(OpenApiAccessTokenRequest tokenRequest) {
        Result<SysApplicationVO> result = applicationService.findByAppCode(tokenRequest.getAppCode());
        if (!Objects.equals(result.getCode(), Result.ok().getCode())) {
            return Result.fail(result.getMsg());
        }
        String accessToken = IdUtil.generateBatchNumber();
        cacheUtil.put(CacheConstant.SYSTEM_CACHE + OpenApiConstant.accessTokenCache, accessToken, tokenRequest, openApiProperties.getTimeToIdle() * 60);
        return Result.ok(accessToken);
    }

    @Override
    public Result<String> getUserInfo() {
        String accessToken = RequestHolder.getCurrRequest().getHeader("accessToken");
        if (Objects.isNull(accessToken)) {
            return Result.fail("Header 缺失参数: accessToken");
        }
        String token = RequestHolder.getCurrRequest().getHeader("token");
        if (Objects.isNull(token)) {
            return Result.fail("Header 缺失参数: token");
        }

        Object object = cacheUtil.get(CacheConstant.SYSTEM_CACHE + OpenApiConstant.accessTokenCache, accessToken);
        if (Objects.isNull(object)) {
            return Result.fail("accessToken已过期！");
        }

        String encrypt = AESUtils.decrypt(token);
        OpenApiToken openApiToken = JackSonUtil.toObject(encrypt, OpenApiToken.class);
        long createTime = openApiToken.getCreateTime();
        if (createTime <= 0 || DateUtil.offsetMinute(new Date(), -openApiProperties.getTimeToIdle()).getTime() - createTime > 0) {
            return Result.fail("token已过期！");
        }
        Result<SysApplicationVO> result = applicationService.findByAppCode(openApiToken.getAppCode());
        if (!Objects.equals(result.getCode(), Result.ok().getCode())) {
            return Result.fail(result.getMsg());
        }
        String tokenToken = openApiToken.getToken();
        String jwtToken = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.TOKEN_CACHE + tokenToken);
        if (Objects.isNull(jwtToken)) {
            return Result.fail("token已过期！");
        }
        UserTicket userTicket = jwtUtil.validateToken(jwtToken, authProperties.getUserTicketClass());
        if (Objects.isNull(userTicket)) {
            return Result.fail("token已过期！");
        }

        UserTicket userTicketResult = new UserTicket();
        userTicketResult.setToken(userTicket.getToken());
        userTicketResult.setUserId(userTicket.getUserId());
        userTicketResult.setZwddId(userTicket.getZwddId());
        userTicketResult.setAccount(userTicket.getAccount());
        userTicketResult.setUsername(userTicket.getUsername());
        userTicketResult.setCreateTime(userTicket.getCreateTime());

        SysApplicationVO sysApplicationVO = result.getData();
        String encryptedReversal = RsaUtil.encryptReversal(JackSonUtil.toJson(userTicketResult), sysApplicationVO.getAppSecret());
        return Result.ok(encryptedReversal);
    }

    @Override
    public Result<String> createToken(String userId, String appCode) {
        Result<SysApplicationVO> result = applicationService.findByAppCode(appCode);
        if (!Objects.equals(result.getCode(), Result.ok().getCode())) {
            return Result.fail(result.getMsg());
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(userId);
        loginInfo.setLoginType(LoginTypeEnum.SSO_LOGIN.getCode());

        Result<UserTicket> login = loginService.login(loginInfo);
        if (!Objects.equals(login.getCode(), Result.ok().getCode())) {
            return Result.build(login.getCode(), login.getMsg());
        }
        UserTicket userTicket = login.getData();
        OpenApiToken openApiToken = new OpenApiToken();
        openApiToken.setUserId(userId);
        openApiToken.setCreateTime(new Date().getTime());
        openApiToken.setAppCode(appCode);
        openApiToken.setToken(userTicket.getToken());
        String encrypt = AESUtils.encrypt(JackSonUtil.toJson(openApiToken));
        return Result.ok(encrypt);
    }
}
