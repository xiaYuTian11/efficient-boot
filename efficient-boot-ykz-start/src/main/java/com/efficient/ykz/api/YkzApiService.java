package com.efficient.ykz.api;

import com.efficient.common.result.Result;
import com.efficient.ykz.model.dto.msg.YkzSendMsg;
import com.efficient.ykz.model.dto.todo.YkzTodoInfo;
import com.efficient.ykz.model.dto.worknotice.YkzWorkNotice;
import com.efficient.ykz.model.dto.worknotice.YkzWorkNoticeBackOut;
import com.efficient.ykz.model.vo.YkzAccessToken;
import com.efficient.ykz.model.vo.YkzLoginToken;
import com.efficient.ykz.model.vo.YkzLoginUser;
import com.efficient.ykz.model.vo.YkzTodoInfoVO;

/**
 * @author TMW
 * @since 2024/1/4 15:37
 */
public interface YkzApiService {

    Result<YkzAccessToken> accessToken();

    Result<YkzLoginUser> getUserInfo(String authCode);

    Result<YkzLoginToken> getTokenInfo(String authCode);

    Result<String> sendMsg(YkzSendMsg ykzSendMsg);

    Result<String> sendWorkNotice(YkzWorkNotice ykzWorkNotice);

    Result<String> revokeWorkNotice(YkzWorkNoticeBackOut ykzWorkNotice);

    Result<YkzTodoInfoVO> createTodo(YkzTodoInfo todoInfo);

    Result<String> finishTodo(String assigneeId, String taskUuid, boolean closePackage);

    Result<String> cancelTodo(String assigneeId, String taskUuid, boolean closePackage);


}
