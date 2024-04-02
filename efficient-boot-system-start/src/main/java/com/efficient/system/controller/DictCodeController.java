package com.efficient.system.controller;

import com.efficient.common.result.Result;
import com.efficient.common.permission.Permission;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import com.efficient.system.api.DictCodeService;
import com.efficient.system.model.dto.DictCodeDTO;
import com.efficient.system.model.dto.DictCodeListDTO;
import com.efficient.system.model.entity.DictCode;
import com.efficient.system.model.vo.DictCodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * efficient_dict_code controller 层
 * </p>
 *
 * @author TMW
 * @date 2024-03-29 11:05:08
 */
@RestController
@RequestMapping("/dict")
@Validated
@Api(tags = "efficient_dict_code")
@Permission
public class DictCodeController {

    @Autowired
    private DictCodeService dictCodeService;

    /**
     * 详情
     */
    @GetMapping("/find")
    @ApiOperation(value = "详情", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "数据类型", required = true)
    })
    public Result find(@NotBlank(message = "type 不能为空") @RequestParam(name = "type") String type) {
        List<DictCode> list = dictCodeService.findByType(type);
        return Result.ok(list);
    }

}
