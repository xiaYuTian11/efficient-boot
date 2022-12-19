package com.efficient.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.efficient.auth.api.AuthService;
import com.efficient.auth.api.LoginService;
import com.efficient.auth.constant.AuthConstant;
import com.efficient.auth.constant.AuthResultEnum;
import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.model.entity.UserCheck;
import com.efficient.common.auth.UserTicket;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.auth.util.AuthUtil;
import com.efficient.auth.util.TokenUtil;
import com.efficient.cache.api.CacheUtil;
import com.efficient.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/10/28 14:51
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public Result login(LoginInfo info) {
        UserCheck userCheck = authService.getUserInfo(info);
        if (Objects.isNull(userCheck)) {
            return Result.build(AuthResultEnum.USER_NOT_EXIST);
        }
        Date unLockTime = userCheck.getUnLockTime();
        String userId = userCheck.getUserId();
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
        if (!StrUtil.equals(userCheck.getPassword(), authUtil.encrypt(info.getPassword()))) {
            // 密码错误
            int retryCount = authProperties.getRetryCount();
            int lockTime = authProperties.getLockTime();

            int count = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId);
            count += 1;
            if (count > retryCount) {
                // 用户已被锁定
                return Result.build(AuthResultEnum.ACCOUNT_LOCK);
            } else if (count < retryCount) {
                cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId, count);
                return Result.build(AuthResultEnum.ACCOUNT_LOCK_COUNT.getCode(), String.format(AuthResultEnum.ACCOUNT_LOCK_COUNT.getMsg(), retryCount - count));
            } else {
                unLockTime = DateUtil.offset(new Date(), DateField.MINUTE, lockTime);
                cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId);
                // 锁定用户
                authService.lockUser(userId, unLockTime);
                return Result.build(AuthResultEnum.ACCOUNT_LOCK);
            }
        }

        final int maxOnline = authProperties.getMaxOnline();
        // 计数
        List<String> userTokenList = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId);
        if (CollUtil.isNotEmpty(userTokenList)) {
            if (maxOnline >= userTokenList.size()) {
                return Result.build(AuthResultEnum.USER_MAX_ONLINE);
            }
        } else {
            userTokenList = new ArrayList<>();
        }
        UserTicket userTicket = new UserTicket();

        userTicket.setUserId(userId);
        userTicket.setAccount(userCheck.getAccount());
        userTicket.setUsername(userCheck.getUsername());

        userTicket.setLoginIp(info.getLoginIp());
        List<String> operationList = authService.getUserOperationList(userId);
        userTicket.setOperationList(operationList);
        List<String> permissionList = authService.getUserPermissionList(userId);
        userTicket.setPermissionList(permissionList);
        Object extendInfo = authService.getUserExtendInfo(userId);
        userTicket.setExtendInfo(extendInfo);

        long timeMillis = System.currentTimeMillis();
        String token = TokenUtil.createToken(userTicket.getUserId(), timeMillis);
        userTicket.setToken(token);
        userTicket.setCreateTime(timeMillis);
        // 存入缓存
        cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token, userTicket);
        userTokenList.add(token);
        cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId, userTokenList);
        return Result.ok(userTicket);
    }

    @Override
    public void logout(String token, String userId) {
        // 移除缓存
        cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_TOKEN_CACHE + token);
        cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_FAIL_CACHE + userId);
        List<String> userTokenList = cacheUtil.get(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId);
        if (CollUtil.isNotEmpty(userTokenList) && userTokenList.size() >= 1) {
            userTokenList.remove(token);
            cacheUtil.put(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId, userTokenList);
        } else {
            cacheUtil.removeCache(AuthConstant.AUTH_CACHE, AuthConstant.CACHE_USER_CACHE + userId);
        }
    }
}
