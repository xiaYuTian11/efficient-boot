package com.efficient.ykz.controller;

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
public class YkzApiController {
    @Autowired
    private YkzApiService ykzApiService;

    /**
     * 获取应用access_token
     */
    @GetMapping("/login/accessToken")
    public Result<YkzAccessToken> accessToken() {
        return ykzApiService.accessToken();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/login/getUserInfo")
    public Result<YkzLoginUser> getUserInfo(@RequestParam("authCode") String authCode) {
        return ykzApiService.getUserInfo(authCode);
    }

    /**
     * 根据authCode获取登录token
     */
    @GetMapping("/login/getTokenInfo")
    public Result<YkzLoginToken> getTokenInfo(@RequestParam("authCode") String authCode) {
        return ykzApiService.getTokenInfo(authCode);
    }

    /**
     * 发送消息
     */
    @PostMapping("/msg/send")
    public Result<String> sendMsg(@RequestBody YkzSendMsg ykzSendMsg) {
        return ykzApiService.sendMsg(ykzSendMsg);
    }

    /**
     * 工作通知-发送
     */
    @PostMapping("/workNotice/send")
    public Result<String> sendWorkNotice(@RequestBody YkzWorkNotice ykzWorkNotice) {
        return ykzApiService.sendWorkNotice(ykzWorkNotice);
    }

    /**
     * 工作通知-撤销
     */
    @PostMapping("/workNotice/revoke")
    public Result<String> revokeWorkNotice(@RequestBody YkzWorkNoticeBackOut ykzWorkNotice) {
        return ykzApiService.revokeWorkNotice(ykzWorkNotice);
    }

    /**
     * 待办-创建
     */
    @PostMapping("/todo/create")
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
    public Result<String> cancelTodo(@RequestParam("assigneeId") String assigneeId,
                                     @RequestParam("taskUuid") String taskUuid,
                                     @RequestParam(value = "closePackage", required = false, defaultValue = "false") boolean closePackage) {
        return ykzApiService.cancelTodo(assigneeId, taskUuid, closePackage);
    }

}
