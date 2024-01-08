package com.efficient.ykz.api;

import cn.hutool.json.JSONObject;
import com.efficient.common.result.Result;
import com.efficient.ykz.model.vo.YkzResult;
import com.efficient.ykz.model.vo.YkzUserCenterAccessToken;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author TMW
 * @since 2024/1/4 15:37
 */
public interface YkzUserCenterService {
    Result orgByCode(String orgCode);

    YkzUserCenterAccessToken getAccessToken(String appId, String appSecret);

    YkzUserCenterAccessToken getAccessToken();

    <M> M sendRequestOne(String url, boolean hasToken, JSONObject params, Class<M> tClass);

    YkzResult sendRequest(String url, boolean hasToken, JSONObject params);
    <M> List<M> sendRequestList(String url, boolean hasToken, JSONObject params, Class<M> tClass);

    Result userByMobile(String phone);

    Result userPostByZwddId(String zwddId);
}
