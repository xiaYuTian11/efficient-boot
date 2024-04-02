package com.efficient.auth.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.efficient.common.permission.Permission;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.constant.MenuRelation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 菜单权限校验，如果自定义实现，请先实现类com.efficient.auth.interceptor.PermissionCheck
 *
 * @author TMW
 * @since 2023/3/21 10:47
 */
@ConditionalOnProperty(name = "com.efficient.auth.permissionCheckType", havingValue = "default", matchIfMissing = true)
@Service
public class DefaultPermissionCheck implements PermissionCheck {
    @Override
    public boolean checkPermission(Permission permission, UserTicket userTicket) {
        List<String> currMenus = userTicket.getPermissionList();
        String systemId = permission.systemId();
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
