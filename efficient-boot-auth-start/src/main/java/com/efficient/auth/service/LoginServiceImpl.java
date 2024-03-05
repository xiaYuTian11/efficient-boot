package com.efficient.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.efficient.auth.api.AuthService;
import com.efficient.auth.api.LoginService;
import com.efficient.auth.constant.AuthConstant;
import com.efficient.auth.constant.AuthResultEnum;
import com.efficient.auth.constant.LoginTypeEnum;
import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.model.entity.UserAuthInfo;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.auth.properties.LoginProperties;
import com.efficient.auth.util.AuthUtil;
import com.efficient.auth.util.JwtUtil;
import com.efficient.auth.util.TokenUtil;
import com.efficient.cache.api.CacheUtil;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.model.vo.YkzLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author TMW
 * @since 2022/10/28 14:51
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthService authService;
    @Autowired
    private YkzApiService ykzApiService;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result<UserTicket> login(LoginInfo info) {
        UserAuthInfo userAuthInfo = this.getUserAuthByLogin(info);
        if (Objects.isNull(userAuthInfo)) {
            return Result.build(AuthResultEnum.USER_NOT_EXIST);
        }

        LoginProperties loginProperties = authProperties.getLogin();

        // 判断是否锁定
        Date unLockTime = userAuthInfo.getUnLockTime();
        String userId = userAuthInfo.getUserId();
        if (Objects.nonNull(unLockTime)) {
            long between = DateUtil.between(unLockTime, new Date(), DateUnit.MINUTE);
            if (between <= 0) {
                // 解锁
                authService.unLockUser(userId);
            } else {
                // 提示
                return Result.build(AuthResultEnum.ACCOUNT_LOCK);
            }
        }

        // 判断是否需要锁定用户
        if (!authUtil.checkEncrypt(userAuthInfo.getPassword(), info.getPassword())) {
            // 密码错误
            int retryCount = loginProperties.getRetryCount();
            int retryTime = loginProperties.getRetryTime();
            int lockTime = loginProperties.getLockTime();
            if (retryCount == -1) {
                return Result.build(AuthResultEnum.ACCOUNT_FAIL);
            }

            int count = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId);
            count += 1;
            if (count > retryCount) {
                // 用户已被锁定
                return Result.build(AuthResultEnum.ACCOUNT_LOCK);
            } else if (count < retryCount) {
                cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId, count, retryTime * 60);
                return Result.build(AuthResultEnum.ACCOUNT_LOCK_COUNT.getCode(), String.format(AuthResultEnum.ACCOUNT_LOCK_COUNT.getMsg(), retryCount - count));
            } else {
                unLockTime = DateUtil.offset(new Date(), DateField.MINUTE, lockTime);
                cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId);
                // 锁定用户
                authService.lockUser(userId, unLockTime);
                return Result.build(AuthResultEnum.ACCOUNT_LOCK);
            }
        } else {
            cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId);
            authService.lockUser(userId, unLockTime);
        }

        int maxOnline = loginProperties.getMaxOnline();
        List<String> userTokenList = new ArrayList<>();
        if (maxOnline > 0) {
            // 计数
            userTokenList = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId);
            if (CollUtil.isNotEmpty(userTokenList)) {
                if (maxOnline <= userTokenList.size()) {
                    return Result.build(AuthResultEnum.USER_MAX_ONLINE);
                }
            } else {
                userTokenList = new ArrayList<>();
            }
        }

        Result<UserTicket> result = authService.getUserTicket(userAuthInfo);
        if (!Objects.equals(result.getCode(), Result.ok().getCode())) {
            return result;
        }
        UserTicket userTicket = result.getData();
        userTicket.setUserId(userId);
        userTicket.setAccount(userAuthInfo.getAccount());
        userTicket.setUsername(userAuthInfo.getUsername());
        userTicket.setLoginIp(info.getLoginIp());
        userTicket.setZwddId(userAuthInfo.getZwddId());
        long timeMillis = System.currentTimeMillis();
        String token = TokenUtil.createToken(userTicket.getUserId(), timeMillis, RequestHolder.getCurrRequest());
        userTicket.setToken(token);
        String jwtToken = jwtUtil.createToken(userTicket);
        userTicket.setCreateTime(timeMillis);
        RequestHolder.set(userTicket);
        // 存入缓存
        cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token, jwtToken);
        // 当前同账号登录了几人
        userTokenList.add(token);
        cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId, userTokenList);
        return Result.ok(userTicket);
    }

    @Override
    public void putCacheUser(String token, UserTicket userTicket) {
        String jwtToken = jwtUtil.createToken(userTicket);
        cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token, jwtToken);
    }

    @Override
    public UserTicket getCacheUser(String token) {
        String jwtToken = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token);
        return jwtUtil.validateToken(jwtToken, authProperties.getUserTicketClass());
    }

    @Override
    public boolean checkUserTokens(String userId) {
        List<String> userTokenList = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId);
        if (CollUtil.isNotEmpty(userTokenList)) {
            Iterator<String> iterator = userTokenList.iterator();
            while (iterator.hasNext()) {
                String token = iterator.next();
                String tokenCacheKey = AuthConstant.CACHE_TOKEN_CACHE + token;
                if (Objects.isNull(cacheUtil.get(AuthConstant.AUTH_CACHE, tokenCacheKey))) {
                    // 缓存中不存在该 token，表示用户不在线
                    iterator.remove();
                    // 移除用户对应的缓存
                    cacheUtil.removeCache(AuthConstant.AUTH_CACHE, tokenCacheKey);
                }
            }
            // 更新用户在线列表
            cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId, userTokenList);
        }
        return true;
    }

    @Override
    public UserAuthInfo getUserAuthByLogin(LoginInfo info) {
        Integer loginType = info.getLoginType();
        if (Objects.equals(loginType, LoginTypeEnum.LOGIN.getCode())) {
            return authService.getUserByAccount(info);
        } else if (Objects.equals(loginType, LoginTypeEnum.YKZ_LOGIN.getCode())) {
            String authCode = info.getAuthCode();
            Result<YkzLoginUser> result = ykzApiService.getUserInfo(authCode);
            if (!Objects.equals(result.getCode(), Result.ok().getCode())) {
                return null;
            }
            Long accountId = result.getData().getAccountId();
            return authService.getUserByZwddId(String.valueOf(accountId));
        } else {
            String authCode = info.getAuthCode();
            return authService.getUserByOtherAuthCode(authCode);
        }
    }

    @Override
    public void logout(String token, String userId) {
        // 移除缓存
        cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token);
        cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId);
        List<String> userTokenList = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId);
        if (CollUtil.isNotEmpty(userTokenList) && !userTokenList.isEmpty()) {
            userTokenList.remove(token);
            cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId, userTokenList);
        } else {
            cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId);
        }
    }
}
