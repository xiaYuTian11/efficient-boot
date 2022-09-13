package com.efficient.logs.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.entity.UserTicket;
import com.efficient.common.util.RequestHolder;
import com.efficient.common.util.ThreadUtil;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.api.SysLogService;
import com.efficient.logs.dao.SysLogMapper;
import com.efficient.logs.model.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author code generator
 * @date 2022-09-05 16:24:37
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public boolean saveLog(Log log, String ip, String url, String params, String resultCode, String result, String exception) {
        UserTicket userTicket = RequestHolder.getCurrUser();
        if (Objects.isNull(userTicket)) {
            userTicket = new UserTicket();
            userTicket.setAccount(CommonConstant.UNKNOWN);
        }
        SysLog sysLog = new SysLog();
        sysLog.setModule(log.module());
        sysLog.setUserId(userTicket.getUserId());
        sysLog.setUserName(userTicket.getUsername());
        sysLog.setLogIp(ip);
        sysLog.setLogTime(new Date());
        sysLog.setRequestUrl(url);
        String optText = log.logOpt().getOpt();
        sysLog.setLogOpt(optText);
        String account = userTicket.getAccount();
        StringBuilder sb = new StringBuilder(account);
        if (log.join()) {
            sb.append("了");
            sb.append(optText);
        }
        sb.append(log.desc());
        sysLog.setLogContent(sb.toString());
        sysLog.setParams(params);
        sysLog.setResultCode(resultCode);
        sysLog.setResult(result);
        sysLog.setException(exception);
        return this.save(sysLog);
    }

    @Override
    public void saveLogAsync(Log log, String ip, String url, String params, String resultCode, String result, String exception) {
        ThreadUtil.EXECUTOR_SERVICE.execute(() -> this.saveLog(log, ip, url, params, resultCode, result, exception));
    }
}
