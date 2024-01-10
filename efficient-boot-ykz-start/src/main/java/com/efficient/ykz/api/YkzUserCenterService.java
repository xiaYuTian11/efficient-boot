package com.efficient.ykz.api;

import cn.hutool.json.JSONObject;
import com.efficient.common.result.Result;
import com.efficient.ykz.model.vo.*;

import java.util.List;

/**
 * @author TMW
 * @since 2024/1/4 15:37
 */
public interface YkzUserCenterService {
    Result<YkzOrg> orgByCode(String orgCode);

    Result<List<YkzOrg>> orgByCodeList(List<String> orgCodeList);

    Result<List<YkzOrg>> orgByParentCode(String orgCode, Integer pageNum, Integer pageSize, boolean includeTop);

    YkzUserCenterAccessToken getAccessToken(String appId, String appSecret);

    YkzUserCenterAccessToken getAccessToken();

    <M> M sendRequestOne(String url, boolean hasToken, JSONObject params, Class<M> tClass);

    YkzResult sendRequest(String url, boolean hasToken, JSONObject params);

    <M> List<M> sendRequestList(String url, boolean hasToken, JSONObject params, Class<M> tClass);

    Result<YkzUser> userByMobile(String phone);

    Result<List<YkzUserPost>> userPostByZwddId(String zwddId);

    Result<List<YkzUser>> userByMobileList(List<String> phoneList);

    Result<YkzUser> userByZwddId(String zwddId);

    Result<List<YkzUser>> userByZwddIdList(List<String> zwddIdList);

    Result<YkzLabel> userTagByZwddId(String zwddId);

    Result<YkzLabel> userTagByMobile(String phone);

    Result<List<YkzLabel>> userTagByZwddIdList(List<String> zwddIdList);

    Result<List<YkzLabel>> userTagByMobileList(List<String> phoneList);

}
