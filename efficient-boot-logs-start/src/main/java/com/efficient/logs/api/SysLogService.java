package com.efficient.logs.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.model.dto.SysLogListDTO;
import com.efficient.logs.model.entity.SysLog;
import com.efficient.logs.model.vo.SysLogVO;

/**
 * <p>
 * 服务Api
 * </p>
 *
 * @author code generator
 * @date 2022-09-05 16:24:37
 */
public interface SysLogService extends IService<SysLog> {

    boolean saveLog(Log log, String ip, String url, String params, String resultCode, String result, String exception);

    void saveLogAsync(Log log, String ip, String url, String params, String resultCode, String result, String exception);

    Page<SysLogVO> list(SysLogListDTO dto);

    SysLog find(String id);
}
