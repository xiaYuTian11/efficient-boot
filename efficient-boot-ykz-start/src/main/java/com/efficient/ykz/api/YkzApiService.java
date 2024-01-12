package com.efficient.ykz.api;

import com.efficient.common.result.Result;
import com.efficient.ykz.model.dto.YkzSendMsg;
import com.efficient.ykz.model.vo.*;

/**
 * @author TMW
 * @since 2024/1/4 15:37
 */
public interface YkzApiService {

    Result<YkzAccessToken> accessToken();

    Result<YkzLoginUser> getUserInfo(String authCode);

    Result<YkzLoginToken> getTokenInfo(String authCode);

    Result<String> sendMsg(YkzSendMsg ykzSendMsg);
}
