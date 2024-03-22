package com.efficient.system.controller;

import com.efficient.common.permission.Permission;
import com.efficient.system.api.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息 controller 层
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
@RestController
@RequestMapping("/sysUser")
@Validated
@Api(tags = "用户信息")
@Permission
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

}
