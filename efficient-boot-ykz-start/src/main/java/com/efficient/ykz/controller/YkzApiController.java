package com.efficient.ykz.controller;

import com.efficient.common.result.Result;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.model.dto.msg.YkzSendMsg;
import com.efficient.ykz.model.dto.worknotice.YkzWorkNotice;
import com.efficient.ykz.model.dto.worknotice.YkzWorkNoticeBackOut;
import com.efficient.ykz.model.vo.YkzAccessToken;
import com.efficient.ykz.model.vo.YkzLoginToken;
import com.efficient.ykz.model.vo.YkzLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据authCode获取登录token
     */
    @GetMapping("/login/getTokenInfo")
    public Result<YkzLoginToken> getTokenInfo(@RequestParam("authCode") String authCode) {
        return ykzApiService.getTokenInfo(authCode);
    }

    /**
     * 发送消息
     */
    @PostMapping("/msg/send")
    public Result<String> sendMsg(@RequestBody YkzSendMsg ykzSendMsg) {
        return ykzApiService.sendMsg(ykzSendMsg);
    }

    /**
     * 工作通知-发送
     */
    @PostMapping("/workNotice/send")
    public Result<String> sendWorkNotice(@RequestBody YkzWorkNotice ykzWorkNotice) {
        return ykzApiService.sendWorkNotice(ykzWorkNotice);
    }

    /**
     * 工作通知-撤销
     */
    @PostMapping("/workNotice/revoke")
    public Result<String> revokeWorkNotice(@RequestBody YkzWorkNoticeBackOut ykzWorkNotice) {
        return ykzApiService.revokeWorkNotice(ykzWorkNotice);
    }

}
