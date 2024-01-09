package com.efficient.ykz.controller;

import com.efficient.common.result.Result;
import com.efficient.ykz.api.YkzUserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户中心
 *
 * @author TMW
 * @since 2024/1/4 15:31
 */
@RestController
@RequestMapping("/ykz/userCenter/")
@Validated
public class YkzUserCenterController {
    @Autowired
    private YkzUserCenterService ykzUserCenterService;

    /**
     * 根据机构Code拉取机构
     * 拉取顶级机构organizationCode正式环境传575324d0-2257-4f53-8e5f-ca72d992abe9，测试环境传GO_1065d20ebe964b4d9da264cfe5e5d240
     */
    @GetMapping("/org/orgByCode")
    public Result orgByCode(@NotBlank(message = "orgCode 不能为空") @RequestParam(name = "orgCode") String orgCode) throws Exception {
        return ykzUserCenterService.orgByCode(orgCode);
    }

    /**
     * 根据机构Code批量拉取机构
     */
    @PostMapping("/org/orgByCodeList")
    public Result orgByCodeList(@RequestBody List<String> orgCodeList) throws Exception {
        return ykzUserCenterService.orgByCodeList(orgCodeList);
    }

    @GetMapping("/org/orgByParentCode")
    public Result orgByParentCode(
            @NotBlank(message = "orgCode 不能为空") @RequestParam(name = "orgCode") String orgCode,
            @NotNull(message = "pageNum 不能为空") @RequestParam(name = "pageNum") Integer pageNum,
            @NotNull(message = "pageSize 不能为空") @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "includeTop", required = false, defaultValue = "true") boolean includeTop) throws Exception {
        if (pageSize > 100) {
            pageSize = 100;
        }
        if (pageSize < 20) {
            pageSize = 20;
        }
        return ykzUserCenterService.orgByParentCode(orgCode, pageNum, pageSize, includeTop);
    }

    /**
     * 根据用户手机号查询用户信息
     */
    @GetMapping("/user/userByMobile")
    public Result userByMobile(@NotBlank(message = "phone 不能为空") @RequestParam(name = "phone") String phone) throws Exception {
        return ykzUserCenterService.userByMobile(phone);
    }

    /**
     * 根据用户ID查询用户职位信息
     */
    @GetMapping("/userPost/userPostByZwddId")
    public Result userPostByZwddId(@NotBlank(message = "zwddId 不能为空") @RequestParam(name = "zwddId") String zwddId) throws Exception {
        return ykzUserCenterService.userPostByZwddId(zwddId);
    }
}
