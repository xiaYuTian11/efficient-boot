package com.efficient.auth.api;

import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.model.entity.UserAuthInfo;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;

import java.util.Date;

/**
 * 需要自定义实现处逻辑
 *
 * @author TMW
 * @since 2022/10/28 14:52
 */
public interface AuthService {
    /**
     * 获取用户基本信息
     *
     * @param info
     * @return
     */
    UserAuthInfo getUserByAccount(LoginInfo info);

    UserAuthInfo getUserByZwddId(String zwddId);

    UserAuthInfo getUserByUserId(LoginInfo info);

    UserAuthInfo getUserByOtherAuthCode(String authCode);

    boolean unLockUser(String userId);

    boolean lockUser(String userId, Date unLockTime);

    Result<UserTicket> getUserTicket(UserAuthInfo userAuthInfo);

}
