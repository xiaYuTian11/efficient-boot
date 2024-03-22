package com.efficient.system.controller;

import com.efficient.common.result.Result;
import com.efficient.common.permission.Permission;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import com.efficient.system.api.SysUnitService;
import com.efficient.system.model.dto.SysUnitDTO;
import com.efficient.system.model.dto.SysUnitListDTO;
import com.efficient.system.model.entity.SysUnit;
import com.efficient.system.model.vo.SysUnitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
* <p>
* 机构数据 controller 层
* </p>
*
* @author TMW
* @date 2024-03-21 14:25:29
*/
@RestController
@RequestMapping("/sysUnit")
@Validated
@Api(tags = "机构数据")
@Permission
public class SysUnitController {

    @Autowired
    private SysUnitService sysUnitService;

}
