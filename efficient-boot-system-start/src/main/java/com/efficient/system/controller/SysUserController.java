package com.efficient.system.controller;

import com.efficient.common.result.Result;
import com.efficient.common.permission.Permission;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import com.efficient.system.api.SysUserService;
import com.efficient.system.model.dto.SysUserDTO;
import com.efficient.system.model.dto.SysUserListDTO;
import com.efficient.system.model.entity.SysUser;
import com.efficient.system.model.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

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
