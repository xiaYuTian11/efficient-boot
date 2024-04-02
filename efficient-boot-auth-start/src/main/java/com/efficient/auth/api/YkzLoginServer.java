package com.efficient.auth.api;

import com.efficient.common.result.Result;
import com.efficient.ykz.model.vo.YkzLoginUser;

/**
 *
 * @author TMW
 * @since 2024/4/2 9:16
 */
public interface YkzLoginServer {
    Result<YkzLoginUser> getUserInfo(String authCode);
}
