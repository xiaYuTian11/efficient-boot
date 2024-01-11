package com.efficient.ykz.controller;

import com.efficient.common.result.Result;
import com.efficient.common.result.ResultEnum;
import com.efficient.ykz.api.YkzUserCenterService;
import com.efficient.ykz.model.vo.YkzLabel;
import com.efficient.ykz.model.vo.YkzOrg;
import com.efficient.ykz.model.vo.YkzUser;
import com.efficient.ykz.model.vo.YkzUserPost;
import com.efficient.ykz.util.YkzUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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
    public Result<YkzOrg> orgByCode(@NotBlank(message = "orgCode 不能为空") @RequestParam(name = "orgCode") String orgCode) throws Exception {
        return ykzUserCenterService.orgByCode(orgCode);
    }

    /**
     * 根据机构Code批量拉取机构
     */
    @PostMapping("/org/orgByCodeList")
    public Result<List<YkzOrg>> orgByCodeList(@RequestBody List<String> orgCodeList) throws Exception {
        return ykzUserCenterService.orgByCodeList(orgCodeList);
    }

    @GetMapping("/org/orgByParentCode")
    public Result<List<YkzOrg>> orgByParentCode(
            @NotBlank(message = "orgCode 不能为空") @RequestParam(name = "orgCode") String orgCode,
            @NotNull(message = "pageNum 不能为空") @RequestParam(name = "pageNum") Integer pageNum,
            @NotNull(message = "pageSize 不能为空") @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "includeTop", required = false, defaultValue = "true") boolean includeTop,
            @RequestParam(name = "flattenTree", required = false, defaultValue = "false") boolean flattenTree) throws Exception {
        if (pageSize > 100) {
            pageSize = 100;
        }
        if (pageSize < 20) {
            pageSize = 20;
        }
        return ykzUserCenterService.orgByParentCode(orgCode, pageNum, pageSize, includeTop,flattenTree);
    }

    /**
     * 根据用户手机号查询用户信息
     */
    @GetMapping("/user/userByMobile")
    public Result<YkzUser> userByMobile(@NotBlank(message = "phone 不能为空") @RequestParam(name = "phone") String phone) throws Exception {
        return ykzUserCenterService.userByMobile(phone);
    }

    /**
     * 批量根据用户手机号查询用户信息
     */
    @PostMapping("/user/userByMobileList")
    public Result<List<YkzUser>> userByMobileList(@RequestBody List<String> phoneList) throws Exception {
        return ykzUserCenterService.userByMobileList(phoneList);
    }

    /**
     * 根据用户政钉ID查询用户信息
     */
    @GetMapping("/user/userByZwddId")
    public Result<YkzUser> userByZwddId(@NotBlank(message = "zwddId 不能为空") @RequestParam(name = "zwddId") String zwddId) throws Exception {
        return ykzUserCenterService.userByZwddId(zwddId);
    }

    /**
     * 批量根据用户政钉ID查询用户信息
     */
    @PostMapping("/user/userByZwddIdList")
    public Result<List<YkzUser>> userByZwddIdList(@RequestBody List<String> zwddIdList) throws Exception {
        return ykzUserCenterService.userByZwddIdList(zwddIdList);
    }

    /**
     * 根据用户ID查询用户职位信息
     */
    @GetMapping("/userPost/userPostByZwddId")
    public Result<List<YkzUserPost>> userPostByZwddId(@NotBlank(message = "zwddId 不能为空") @RequestParam(name = "zwddId") String zwddId) throws Exception {
        return ykzUserCenterService.userPostByZwddId(zwddId);
    }

    /**
     * 根据用户政钉Id查询用户标签信息
     */
    @GetMapping("/userTag/userTagByZwddId")
    public Result<YkzLabel> userTagByZwddId(@NotBlank(message = "zwddId 不能为空") @RequestParam(name = "zwddId") String zwddId) throws Exception {
        return ykzUserCenterService.userTagByZwddId(zwddId);
    }

    /**
     * 根据用户手机号查询用户标签信息
     */
    @GetMapping("/userTag/userTagByMobile")
    public Result<YkzLabel> userTagByMobile(@NotBlank(message = "phone 不能为空") @RequestParam(name = "phone") String phone) throws Exception {
        return ykzUserCenterService.userTagByMobile(phone);
    }

    /**
     * 批量根据用户政钉Id查询用户标签信息
     */
    @PostMapping("/userTag/userTagByZwddIdList")
    public Result<List<YkzLabel>> userTagByZwddIdList(@RequestBody List<String> zwddIdList) throws Exception {
        return ykzUserCenterService.userTagByZwddIdList(zwddIdList);
    }

    /**
     * 批量根据用户手机号查询用户标签信息
     */
    @PostMapping("/userTag/userTagByMobileList")
    public Result<List<YkzLabel>> userTagByMobileList(@RequestBody List<String> phoneList) throws Exception {
        return ykzUserCenterService.userTagByMobileList(phoneList);
    }

}
