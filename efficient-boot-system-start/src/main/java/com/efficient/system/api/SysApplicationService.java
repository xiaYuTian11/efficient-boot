package com.efficient.system.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.common.result.Result;
import com.efficient.system.model.dto.SysApplicationDTO;
import com.efficient.system.model.dto.SysApplicationListDTO;
import com.efficient.system.model.entity.SysApplication;
import com.efficient.system.model.vo.SysApplicationVO;

/**
 * <p>
 * 系统第三方应用 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-04-16 16:02:49
 */
public interface SysApplicationService extends IService<SysApplication> {
    /***
     * 新增
     */
    Result<SysApplication> save(SysApplicationDTO dto);

    /**
     * 修改
     */
    Result<Boolean> update(SysApplicationDTO dto);

    /**
     * 删除
     */
    Result<Boolean> delete(String id);

    /**
     * 列表查询
     */
    Page<SysApplication> list(SysApplicationListDTO dto);

    Result<SysApplicationVO> findByAppCode(String appCode);

}
