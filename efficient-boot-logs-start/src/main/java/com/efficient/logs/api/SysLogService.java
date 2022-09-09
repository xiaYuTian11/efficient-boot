package com.efficient.logs.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.model.entity.SysLog;

/**
 * <p>
 * 服务Api
 * </p>
 *
 * @author code generator
 * @date 2022-09-05 16:24:37
 */
public interface SysLogService extends IService<SysLog> {
    public boolean saveLog(Log log, String ip, String url, String params, String resultCode, String result, String exception);
}
