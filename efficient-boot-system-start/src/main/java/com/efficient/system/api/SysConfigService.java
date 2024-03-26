package com.efficient.system.api;

import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.efficient.system.model.dto.SysConfigDTO;
import com.efficient.system.model.dto.SysConfigListDTO;
import com.efficient.system.model.entity.SysConfig;
import com.efficient.system.model.vo.SysConfigVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统配置 服务Api
 * </p>
 *
 * @author TMW
 * @date 2024-03-26 10:57:51
 */
public interface SysConfigService extends IService<SysConfig> {
    /***
     * 新增
     */
    Result<SysConfig> save(SysConfigDTO dto);

    /**
     * 详情
     */
    <T> T findByCode(String id, Class<T> tClass);

}
