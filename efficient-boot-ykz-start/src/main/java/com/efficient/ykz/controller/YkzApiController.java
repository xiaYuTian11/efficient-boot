package com.efficient.ykz.controller;

import com.efficient.common.result.Result;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.model.vo.YkzAccessToken;
import com.efficient.ykz.model.vo.YkzLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户中心
 *
 * @author TMW
 * @since 2024/1/4 15:31
 */
@RestController
@RequestMapping("/ykz/api/")
@Validated
public class YkzApiController {
    @Autowired
    private YkzApiService ykzApiService;

    /**
     * 获取应用access_token
     */
    @GetMapping("/login/accessToken")
    public Result<YkzAccessToken> accessToken() {
        return ykzApiService.accessToken();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/login/getUserInfo")
    public Result<YkzLoginUser> getUserInfo(@RequestParam("authCode") String authCode) {
        return ykzApiService.getUserInfo(authCode);
    }

}
