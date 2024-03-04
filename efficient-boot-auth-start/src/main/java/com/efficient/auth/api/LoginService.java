package com.efficient.auth.api;

import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.model.entity.UserAuthInfo;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;

/**
 * @author TMW
 * @since 2022/10/28 14:51
 */
public interface LoginService {
    Result<UserTicket> login(LoginInfo info);

    /**
     * 清理过期用户
     * @param userId
     * @return
     */
    boolean checkUserTokens(String userId);

    UserAuthInfo getUserAuthByLogin(LoginInfo info);

    void logout(String token, String userId);
}
