package com.efficient.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.system.model.entity.SysUser;

/**
 * <p>
 * 用户信息 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
public interface SysUserService extends IService<SysUser> {
    SysUser getByZwddId(String zwddId);
}
