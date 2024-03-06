package com.efficient.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.efficient.auth.api.LoginService;
import com.efficient.auth.constant.AuthConstant;
import com.efficient.auth.constant.AuthResultEnum;
import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.properties.AuthProperties;
import com.efficient.cache.api.CacheUtil;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.common.util.WebUtil;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.UUID;

/**
 * @author TMW
 * @since 2022/10/28 11:38
 */
@RestController
@Validated
@Api(tags = "用户登录")
@Slf4j
public class LoginController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private LoginService loginService;

    @Log(logOpt = LogEnum.LOGIN, desc = "登录系统")
    @PostMapping("/login")
    public Result<UserTicket> login(@Validated @RequestBody LoginInfo info) {
        if (authProperties.getLogin().isCaptcha()) {
            boolean flag = loginService.checkCaptcha(info.getCaptchaId(), info.getCaptcha());
            if (!flag) {
                return Result.build(AuthResultEnum.CAPTCHA_NOT_MATCH);
            }
        }
        String loginIp = WebUtil.getIP(request);
        info.setLoginIp(loginIp);
        return loginService.login(info);
    }

    /**
     * 登录
     */
    @Permission
    @Log(logOpt = LogEnum.LOGOUT, desc = "登出系统")
    @GetMapping("/logout")
    public Result logout(@NotBlank(message = "token 不能为空") @RequestParam("token") String token,
                         @NotBlank(message = "userId 不能为空") @RequestParam("token") String userId) {
        loginService.logout(token, userId);
        return Result.ok();
    }

    /***
     * 渲染验证码
     * */
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString() + +System.nanoTime();
        // 自定义验证码，排除0，1，L,I（随机4位，可重复）
        RandomGenerator randomGenerator = new RandomGenerator(authProperties.getLogin().getCaptchaRule(), 4);
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(120, 34, 4, 0);
        circleCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        circleCaptcha.createCode();
        cacheUtil.put(AuthConstant.CACHE_CAPTCHA_CODE, uuid, circleCaptcha.getCode());
        // 自定义响应头，存放uuid
        response.setHeader("captchaId", uuid);
        try (ServletOutputStream out = response.getOutputStream()) {
            circleCaptcha.write(out);
        } catch (IOException e) {
            log.error("生成验证码错误", e);
        }
    }

    /***
     * 校验渲染验证码
     * */
    @GetMapping("/checkCaptcha")
    public Result<Boolean> checkCaptcha(@NotBlank(message = "captchaId 不能为空") @RequestParam("captchaId") String captchaId,
                                        @NotBlank(message = "captcha 不能为空") @RequestParam("captcha") String captcha) {
        boolean flag = loginService.checkCaptcha(captchaId, captcha);
        return Result.ok(flag);
    }
}
