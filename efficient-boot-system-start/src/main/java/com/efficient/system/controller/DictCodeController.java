package com.efficient.system.controller;

import com.efficient.common.entity.TreeNode;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.system.api.DictCodeService;
import com.efficient.system.model.entity.DictCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 字典表 controller 层
 * </p>
 *
 * @author TMW
 * @date 2024-03-29 11:05:08
 */
@RestController
@RequestMapping("/dict")
@Validated
@Api(tags = "字典表")
@Permission
public class DictCodeController {

    @Autowired
    private DictCodeService dictCodeService;

    /**
     * 获取字典表列表
     */
    @GetMapping("/find")
    @ApiOperation(value = "详情", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "数据类型", required = true)
    })
    public Result<List<DictCode>> find(@NotBlank(message = "type 不能为空") @RequestParam(name = "type") String type) {
        List<DictCode> list = dictCodeService.findByType(type);
        return Result.ok(list);
    }

    /**
     * 获取字典表树
     */
    @GetMapping("/findTree")
    @ApiOperation(value = "获取字典表树", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "数据类型", required = true)
    })
    public Result<List<TreeNode>> findTree(@NotBlank(message = "type 不能为空") @RequestParam(name = "type") String type) {
        List<TreeNode> treeNode = dictCodeService.findTree(type);
        return Result.ok(treeNode);
    }

}
