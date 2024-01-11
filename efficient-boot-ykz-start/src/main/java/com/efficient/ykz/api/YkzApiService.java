package com.efficient.ykz.api;

import cn.hutool.json.JSONObject;
import com.efficient.common.result.Result;
import com.efficient.ykz.model.vo.*;

import java.util.List;

/**
 * @author TMW
 * @since 2024/1/4 15:37
 */
public interface YkzApiService {

    Result<YkzAccessToken> accessToken();

    Result<YkzLoginUser> getUserInfo(String authCode);
}
