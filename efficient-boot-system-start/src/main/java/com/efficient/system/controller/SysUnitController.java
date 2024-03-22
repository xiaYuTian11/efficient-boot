package com.efficient.system.controller;

import com.efficient.common.permission.Permission;
import com.efficient.system.api.SysUnitService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
