package com.efficient.auth.api;

import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.model.entity.UserAuthInfo;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.result.Result;

import java.util.Date;

/**
 * 需要自定义实现处逻辑
 *
 * @author TMW
 * @since 2022/10/28 14:52
 */
public interface AuthService {
    /**
     * 获取用户信息-普通登录
     *
     * @param info
     * @return
     */
    UserAuthInfo getUserByAccount(LoginInfo info);

    /**
     * 获取用户信息-政务钉登录
     *
     * @param zwddId
     * @return
     */
    UserAuthInfo getUserByZwddId(String zwddId);

    /**
     * 获取用户信息-本系统单点登录
     *
     * @param info
     * @return
     */
    UserAuthInfo getUserByUserId(LoginInfo info);

    /**
     * 获取用户信息- 其他认证方式登录
     *
     * @param info
     * @return
     */

    UserAuthInfo getUserByOtherAuthCode(LoginInfo info);

    /**
     * 解锁用户
     *
     * @param userId
     * @return
     */
    boolean unLockUser(String userId);

    /**
     * 锁定用户
     *
     * @param userId
     * @param unLockTime
     * @return
     */
    boolean lockUser(String userId, Date unLockTime);

    /**
     * 加载登录信息
     *
     * @param userAuthInfo
     * @return
     */
    Result<UserTicket> loadUserTicket(UserAuthInfo userAuthInfo);

}
