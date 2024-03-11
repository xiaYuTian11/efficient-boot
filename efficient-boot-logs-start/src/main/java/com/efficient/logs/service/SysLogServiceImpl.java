package com.efficient.logs.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.util.ThreadUtil;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.api.SysLogService;
import com.efficient.logs.constant.LogEnum;
import com.efficient.logs.dao.SysLogMapper;
import com.efficient.logs.model.entity.SysLog;
import com.efficient.logs.properties.LogsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
@ConditionalOnProperty(name = "com.efficient.logs.db", havingValue = "true")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private LogsProperties logsProperties;
    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean saveLog(Log log, String ip, String url, String params, String resultCode, String result, String exception) {
        UserTicket userTicket = RequestHolder.getCurrUser();
        if (Objects.isNull(userTicket)) {
            userTicket = new UserTicket();
            userTicket.setAccount(CommonConstant.UNKNOWN);
        }
        SysLog sysLog = new SysLog();
        sysLog.setSystemId(request.getHeader(logsProperties.getSystemIdField()));
        sysLog.setModule(log.module());
        sysLog.setUserId(userTicket.getUserId());
        sysLog.setUserName(userTicket.getUsername());
        sysLog.setLogIp(ip);
        sysLog.setLogTime(new Date());
        sysLog.setRequestUrl(url);
        LogEnum logEnum = log.logOpt();
        String optText = logEnum.getOpt();
        if (Objects.equals(logEnum, LogEnum.CUSTOM)) {
            optText = log.customOpt();
        }
        sysLog.setLogOpt(optText);
        String account = userTicket.getAccount();
        String desc = log.desc();
        StringBuilder sb = new StringBuilder(account);
        if (StrUtil.isBlank(desc)) {
            if (log.join()) {
                sb.append(optText);
            }
            if (StrUtil.isNotBlank(log.module())) {
                sb.append(log.module());
            }
            sysLog.setLogContent(sb.toString());
        } else {
            sb.append(desc);
            sysLog.setLogContent(sb.toString());
        }

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
