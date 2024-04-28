package com.efficient.auth.service;

import com.efficient.auth.api.AuthService;
import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.model.entity.UserAuthInfo;
import com.efficient.auth.util.AuthUtil;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author TMW
 * @since 2024/4/25 16:55
 */
@ConditionalOnProperty(name = "com.efficient.auth.authService", havingValue = "default", matchIfMissing = true)
@Service
public class EfficientAuthService implements AuthService {
    @Autowired
    private AuthUtil authUtil;

    @Override
    public UserAuthInfo getUserByAccount(LoginInfo info) {
        return this.findUserAuthInfo();
    }

    @Override
    public UserAuthInfo getUserByZwddId(String zwddId) {
        return this.findUserAuthInfo();
    }

    @Override
    public UserAuthInfo getUserByUserId(LoginInfo info) {
        return this.findUserAuthInfo();
    }

    @Override
    public UserAuthInfo getUserByOtherAuthCode(LoginInfo info) {
        return this.findUserAuthInfo();
    }

    @Override
    public boolean unLockUser(String userId) {
        return false;
    }

    @Override
    public boolean lockUser(String userId, Date unLockTime) {
        return false;
    }

    @Override
    public Result<UserTicket> loadUserTicket(UserAuthInfo userAuthInfo) {
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userAuthInfo.getUserId());
        userTicket.setZwddId(userAuthInfo.getZwddId());
        userTicket.setAccount(userAuthInfo.getAccount());
        userTicket.setUsername(userAuthInfo.getUsername());
        return Result.ok(userTicket);
    }

    private UserAuthInfo findUserAuthInfo() {
        UserAuthInfo userAuthInfo = new UserAuthInfo();
        userAuthInfo.setAccount("admin");
        userAuthInfo.setPassword(authUtil.createPassword("123456"));
        userAuthInfo.setUsername("系统管理员");
        userAuthInfo.setUserId("1");
        userAuthInfo.setZwddId("1");
        return userAuthInfo;
    }
}
