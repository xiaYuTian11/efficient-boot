package com.efficient.system.api;

import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.system.model.dto.SysUserDTO;
import com.efficient.system.model.dto.SysUserListDTO;
import com.efficient.system.model.entity.SysUser;
import com.efficient.system.model.vo.SysUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

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
