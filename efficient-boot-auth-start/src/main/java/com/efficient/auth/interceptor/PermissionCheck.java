package com.efficient.auth.interceptor;

import com.efficient.common.permission.Permission;
import com.efficient.common.auth.UserTicket;

/**
 * @author TMW
 * @since 2023/3/21 10:45
 */
public interface PermissionCheck {

    public boolean checkPermission(Permission permission, UserTicket userTicket);

}
