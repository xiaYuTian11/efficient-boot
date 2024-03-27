package com.efficient.ykz.controller;

import com.efficient.common.permission.Permission;
import com.efficient.common.result.Result;
import com.efficient.ykz.api.YkzApiService;
import com.efficient.ykz.model.dto.msg.YkzSendMsg;
import com.efficient.ykz.model.dto.todo.YkzTodoInfo;
import com.efficient.ykz.model.dto.worknotice.YkzWorkNotice;
import com.efficient.ykz.model.dto.worknotice.YkzWorkNoticeBackOut;
import com.efficient.ykz.model.vo.YkzAccessToken;
import com.efficient.ykz.model.vo.YkzLoginToken;
import com.efficient.ykz.model.vo.YkzLoginUser;
import com.efficient.ykz.model.vo.YkzTodoInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户中心
 *
 * @author TMW
 * @since 2024/1/4 15:31
 */
@RestController
@RequestMapping("/ykz/api/")
@Validated
@Api(tags = "YKZ -消息接口")
@Permission
public class YkzApiController {
    @Autowired
    private YkzApiService ykzApiService;

    /**
     * 获取应用access_token
     */
    @GetMapping("/login/accessToken")
    @ApiOperation(value = "获取accessToken", response = YkzAccessToken.class)
    public Result<YkzAccessToken> accessToken() {
        return ykzApiService.accessToken();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/login/getUserInfo")
    @ApiOperation(value = "获取用户信息", response = YkzLoginUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authCode", value = "YKZ authCode", required = true)
    })
    public Result<YkzLoginUser> getUserInfo(@RequestParam("authCode") String authCode) {
        return ykzApiService.getUserInfo(authCode);
    }

    /**
     * 根据authCode获取登录token
     */
    @GetMapping("/login/getTokenInfo")
    @ApiOperation(value = "根据authCode获取登录token", response = YkzLoginToken.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authCode", value = "YKZ authCode", required = true)
    })
    public Result<YkzLoginToken> getTokenInfo(@RequestParam("authCode") String authCode) {
        return ykzApiService.getTokenInfo(authCode);
    }

    /**
     * 发送消息
     */
    @PostMapping("/msg/send")
    @ApiOperation(value = "发送消息", response = String.class)
    public Result<String> sendMsg(@RequestBody YkzSendMsg ykzSendMsg) {
        return ykzApiService.sendMsg(ykzSendMsg);
    }

    /**
     * 工作通知-发送
     */
    @PostMapping("/workNotice/send")
    @ApiOperation(value = "工作通知-发送", response = String.class)
    public Result<String> sendWorkNotice(@RequestBody YkzWorkNotice ykzWorkNotice) {
        return ykzApiService.sendWorkNotice(ykzWorkNotice);
    }

    /**
     * 工作通知-撤销
     */
    @PostMapping("/workNotice/revoke")
    @ApiOperation(value = "工作通知-撤销", response = String.class)
    public Result<String> revokeWorkNotice(@RequestBody YkzWorkNoticeBackOut ykzWorkNotice) {
        return ykzApiService.revokeWorkNotice(ykzWorkNotice);
    }

    /**
     * 待办-创建
     */
    @PostMapping("/todo/create")
    @ApiOperation(value = "待办-创建", response = YkzTodoInfoVO.class)
    public Result<YkzTodoInfoVO> createTodo(@RequestBody YkzTodoInfo todoInfo) {
        return ykzApiService.createTodo(todoInfo);
    }

    /**
     * 待办-完成
     * @param assigneeId 接收人ID
     * @param taskUuid 任务主键
     * @param closePackage    是否同步关闭实例，默认false
     */
    @GetMapping("/todo/finish")
    @ApiOperation(value = "待办-完成", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assigneeId", value = "接收人ID", required = true),
            @ApiImplicitParam(name = "taskUuid", value = "任务ID", required = true),
            @ApiImplicitParam(name = "closePackage", value = "关闭空间", required = false)
    })
    public Result<String> finishTodo(@RequestParam("assigneeId") String assigneeId,
                                     @RequestParam("taskUuid") String taskUuid,
                                     @RequestParam(value = "closePackage", required = false, defaultValue = "false") boolean closePackage) {
        return ykzApiService.finishTodo(assigneeId, taskUuid, closePackage);
    }

    /**
     * 待办-取消
     * @param assigneeId 接收人ID
     * @param taskUuid 任务主键
     * @param closePackage    是否同步关闭实例，默认false
     */
    @GetMapping("/todo/cancel")
    @ApiOperation(value = "待办-完成", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assigneeId", value = "接收人ID", required = true),
            @ApiImplicitParam(name = "taskUuid", value = "任务ID", required = true),
            @ApiImplicitParam(name = "closePackage", value = "关闭空间", required = false)
    })
    public Result<String> cancelTodo(@RequestParam("assigneeId") String assigneeId,
                                     @RequestParam("taskUuid") String taskUuid,
                                     @RequestParam(value = "closePackage", required = false, defaultValue = "false") boolean closePackage) {
        return ykzApiService.cancelTodo(assigneeId, taskUuid, closePackage);
    }

}
