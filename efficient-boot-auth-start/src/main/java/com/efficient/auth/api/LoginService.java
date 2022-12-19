package com.efficient.auth.api;

import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.common.result.Result;

/**
 * @author TMW
 * @since 2022/10/28 14:51
 */
public interface LoginService {
    Result login(LoginInfo info);

    void logout(String token, String userId);
}
