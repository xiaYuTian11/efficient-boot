package com.efficient.system.api;

import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.system.model.dto.SysUserPostDTO;
import com.efficient.system.model.dto.SysUserPostListDTO;
import com.efficient.system.model.entity.SysUserPost;
import com.efficient.system.model.vo.SysUserPostVO;
import com.baomidou.mybatisplus.extension.service.IService;

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
