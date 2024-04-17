package com.efficient.openapi.api;

import com.efficient.common.result.Result;
import com.efficient.openapi.entity.OpenApiAccessTokenRequest;

/**
 * @author TMW
 * @since 2024/4/16 16:16
 */
public interface OpenApiService {
    Result<String> getAccessToken(OpenApiAccessTokenRequest tokenRequest);

    Result<String> getUserInfo();

    Result<String> createToken(String userId, String appCode);
}
