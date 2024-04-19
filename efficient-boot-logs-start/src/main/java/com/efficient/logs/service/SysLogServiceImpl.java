package com.efficient.logs.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.auth.UserTicket;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.result.ResultEnum;
import com.efficient.common.util.ThreadUtil;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.api.SysLogService;
import com.efficient.logs.constant.LogEnum;
import com.efficient.logs.dao.SysLogMapper;
import com.efficient.logs.model.converter.SysLogConverter;
import com.efficient.logs.model.dto.SysLogListDTO;
import com.efficient.logs.model.entity.SysLog;
import com.efficient.logs.model.vo.SysLogVO;
import com.efficient.logs.properties.LogsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private SysLogConverter sysLogConverter;

    @Override
    public boolean saveLog(Log log, String ip, String url, String params, String resultCode, String result, String exception) {
        UserTicket userTicket = RequestHolder.getCurrUser();
        if (Objects.isNull(userTicket)) {
            userTicket = new UserTicket();
            userTicket.setAccount(CommonConstant.UNKNOWN);
        }
        SysLog sysLog = new SysLog();
        sysLog.setSystemId(RequestHolder.getCurrSystemId());
        sysLog.setModule(log.module());
        sysLog.setUserId(userTicket.getUserId());
        sysLog.setUserUnitId(userTicket.getUserUnitId());
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

    @Override
    public Page<SysLogVO> list(SysLogListDTO dto) {
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysLog::getId, SysLog::getSystemId, SysLog::getModule, SysLog::getUserId,
                SysLog::getUserName, SysLog::getLogIp,
                SysLog::getLogTime, SysLog::getLogOpt, SysLog::getLogContent, SysLog::getResultCode);
        String systemId = dto.getSystemId();
        if (StrUtil.isNotBlank(systemId)) {
            queryWrapper.eq(SysLog::getSystemId, systemId);
        }
        String userName = dto.getUserName();
        if (StrUtil.isNotBlank(userName)) {
            queryWrapper.like(SysLog::getUserName, userName);
        }
        String logOpt = dto.getLogOpt();
        if (StrUtil.isNotBlank(logOpt)) {
            queryWrapper.eq(SysLog::getLogOpt, logOpt);
        }
        String logContent = dto.getLogContent();
        if (StrUtil.isNotBlank(logOpt)) {
            queryWrapper.like(SysLog::getLogContent, logContent);
        }
        String module = dto.getModule();
        if (StrUtil.isNotBlank(module)) {
            queryWrapper.eq(SysLog::getModule, module);
        }
        String logIp = dto.getLogIp();
        if (StrUtil.isNotBlank(module)) {
            queryWrapper.like(SysLog::getLogIp, logIp);
        }
        Integer isSuccess = dto.getIsSuccess();
        if (Objects.nonNull(isSuccess)) {
            if (Objects.equals(isSuccess, CommonConstant.TRUE_INT)) {
                queryWrapper.eq(SysLog::getResultCode, ResultEnum.SUCCESS.getCode());
            } else {
                queryWrapper.ne(SysLog::getResultCode, ResultEnum.SUCCESS.getCode());
            }
        }
        Date startDate = dto.getStartDate();
        if (Objects.nonNull(startDate)) {
            queryWrapper.ge(SysLog::getLogTime, DateUtil.beginOfDay(startDate));
        }
        Date endDate = dto.getEndDate();
        if (Objects.nonNull(endDate)) {
            queryWrapper.le(SysLog::getLogTime, DateUtil.endOfDay(endDate));
        }

        Integer logType = dto.getLogType();
        if (Objects.nonNull(logType)) {
            if (Objects.equals(logType, CommonConstant.TRUE_INT)) {
                queryWrapper.eq(SysLog::getLogOpt, LogEnum.LOGIN.getOpt());
            } else {
                queryWrapper.ne(SysLog::getLogOpt, LogEnum.LOGIN.getOpt());
            }

        }

        queryWrapper.orderByDesc(SysLog::getLogTime);
        Page<SysLog> sysLogPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<SysLog> page = this.page(sysLogPage, queryWrapper);
        List<SysLogVO> voList = new ArrayList<>();
        List<SysLog> records = page.getRecords();
        Page<SysLogVO> resultPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        resultPage.setRecords(voList);
        if (CollUtil.isEmpty(records)) {
            return resultPage;
        }
        records.forEach(et -> {
            SysLogVO sysLogVO = sysLogConverter.entity2Vo(et);
            voList.add(sysLogVO);
        });
        return resultPage;
    }

    @Override
    public SysLog find(String id) {
        SysLog byId = this.getById(id);
        return byId;
    }
}
