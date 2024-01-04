package com.efficient.ykz.api;

import cn.hutool.json.JSONObject;
import com.efficient.common.result.Result;
import com.efficient.ykz.model.vo.YkzUserCenterAccessToken;

/**
 * @author TMW
 * @since 2024/1/4 15:37
 */
public interface YkzUserCenterService {
    Result orgByCode(String orgCode);

    YkzUserCenterAccessToken getAccessToken(String appId, String appSecret);

    YkzUserCenterAccessToken getAccessToken();

    <M> M sendRequest(String url, JSONObject params, Class<M> tClass);

    <M> M sendRequest(String url, boolean hasToken, JSONObject params, Class<M> tClass);

    Result userByMobile(String phone);

    Result userPostByZwddId(String zwddId);
}
