package com.efficient.cache.controller;

import com.efficient.cache.util.ProgressUtil;
import com.efficient.cache.vo.DataProgressVO;
import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TMW
 * @since 2022/8/26 9:29
 */
@RestController
@RequestMapping("/progress")
@Validated
@Slf4j
@Permission
public class ProgressController {
    @Autowired
    private ProgressUtil progressUtil;

    /**
     * 获取进度条
     *
     * @param progressKey 进度条key
     * @return
     */
    @GetMapping(value = "/get")
    public Result<DataProgressVO> getProgress(@RequestParam("progressKey") String progressKey) {
        DataProgressVO progressVO = progressUtil.getByKey(progressKey);
        return Result.ok(progressVO);
    }
}
