package com.efficient.system.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.system.model.entity.SysUserPost;

/**
 * <p>
 * 用户职位信息 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-03-21 14:25:28
 */
public interface SysUserPostService extends IService<SysUserPost> {
    SysUserPost getMainByUserId(String userId);
}
