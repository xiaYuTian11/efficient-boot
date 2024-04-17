package com.efficient.openapi.controller;

import com.efficient.common.result.Result;
import com.efficient.common.validate.QueryGroup;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import com.efficient.openapi.api.OpenApiService;
import com.efficient.openapi.entity.OpenApiAccessTokenRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author TMW
 * @since 2024/4/16 16:12
 */
@RestController
@RequestMapping("/openApi")
@Validated
@Api(tags = "openApi")
public class OpenApiController {
    @Autowired
    private OpenApiService openApiService;

    /**
     * 获取token
     */
    @Log(logOpt = LogEnum.QUERY, module = "系统第三方应用")
    @GetMapping("/createToken")
    @ApiOperation(value = "createToken")
    public Result<String> createToken(@NotBlank(message = "userId 不能为空")
                                      @RequestParam("userId") String userId,
                                      @NotBlank(message = "appCode 不能为空")
                                      @RequestParam("appCode") String appCode) {
        return openApiService.createToken(userId, appCode);
    }

    /**
     * 获取accessToken
     */
    @Log(logOpt = LogEnum.QUERY, module = "系统第三方应用")
    @PostMapping("/getAccessToken")
    @ApiOperation(value = "获取accessToken")
    public Result<String> getAccessToken(@Validated(QueryGroup.class)
                                         @RequestBody OpenApiAccessTokenRequest tokenRequest) {
        return openApiService.getAccessToken(tokenRequest);
    }

    /**
     * 获取用户信息
     */
    @Log(logOpt = LogEnum.QUERY, module = "系统第三方应用")
    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取accessToken")
    public Result<String> getUserInfo() {
        return openApiService.getUserInfo();
    }
}
