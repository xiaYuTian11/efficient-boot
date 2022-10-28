package com.efficient.auth.api;

import com.efficient.auth.model.dto.LoginInfo;
import com.efficient.auth.model.entity.UserCheck;

import java.util.Date;
import java.util.List;

/**
 * 需要自定义实现处逻辑
 *
 * @author TMW
 * @since 2022/10/28 14:52
 */
public interface AuthService {
    /**
     * 获取用户基本信息
     *
     * @param info
     * @return
     */
    UserCheck getUserInfo(LoginInfo info);

    /**
     * 获取用户菜单权限
     *
     * @param userId
     * @return
     */
    List<String> getUserMenuList(String userId);

    /**
     * 获取用户按钮权限
     *
     * @param userId
     * @return
     */
    List<String> getUserPermissionList(String userId);

    /**
     * 获取用户扩展信息
     *
     * @param userId
     * @return
     */
    Object getUserExtendInfo(String userId);

    boolean unLockUser(String userId);

    boolean lockUser(String userId, Date unLockTime);
}
