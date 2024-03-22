package com.efficient.system.controller;

import com.efficient.common.result.Result;
import com.efficient.common.permission.Permission;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import com.efficient.system.api.SysUserPostService;
import com.efficient.system.model.dto.SysUserPostDTO;
import com.efficient.system.model.dto.SysUserPostListDTO;
import com.efficient.system.model.entity.SysUserPost;
import com.efficient.system.model.vo.SysUserPostVO;
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
* 用户职位信息 controller 层
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:28
*/
@RestController
@RequestMapping("/sysUserPost")
@Validated
@Api(tags = "用户职位信息")
@Permission
public class SysUserPostController {

    @Autowired
    private SysUserPostService sysUserPostService;

}
