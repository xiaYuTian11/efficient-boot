package com.efficient.auth.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.efficient.auth.permission.Permission;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.constant.MenuRelation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author TMW
 * @since 2023/3/21 10:47
 */
public class DefaultPermissionCheck implements PermissionCheck{
    @Override
    public boolean checkPermission(Permission permission, UserTicket userTicket) {
        List<String> currMenus = userTicket.getPermissionList();
        final String[] menuEnums = permission.value();
        if (menuEnums.length <= 0) {
            return true;
        }
        if (CollUtil.isEmpty(currMenus)) {
            return false;
        }
        final MenuRelation relation = permission.relation();
        if (Objects.equals(relation, MenuRelation.OR)) {
            return Arrays.stream(menuEnums).anyMatch(currMenus::contains);
        } else {
            return Arrays.stream(menuEnums).allMatch(currMenus::contains);
        }
    }
}
