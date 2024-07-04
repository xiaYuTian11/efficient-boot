package com.efficient.logs.aop;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.result.Result;
import com.efficient.common.util.JackSonUtil;
import com.efficient.common.util.ThreadUtil;
import com.efficient.common.util.WebUtil;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.api.SysLogService;
import com.efficient.logs.constant.LogEnum;
import com.efficient.logs.handle.LogSpelProcess;
import com.efficient.logs.properties.LogsProperties;
import com.google.common.collect.Lists;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author TMW
 * @since 2022/9/5 15:33
 */
@Aspect
@Component
public class LogsAop {
    public static final TransmittableThreadLocal<Long> START_TIME = new TransmittableThreadLocal<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(LogsAop.class);
    private static final String REQUEST_NORMAL_FORMAT = "\n=============\nip:%s\nurl:%s\ncontroller:%s\nargs:%s\ntoken:%s\nreturn:%s\ntime:%d\n请求成功\n=============";
    private static final String REQUEST_ERROR_FORMAT = "\n=============\nip:%s\nurl:%s\ncontroller:%s\nargs:%s\ntoken:%s\nreturn:%s\nerror:%s\ntime:%d\n请求失败\n=============";
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogService logService;
    @Autowired
    private LogsProperties logsProperties;
    @Autowired
    private LogSpelProcess logSpelProcess;

    @Pointcut("@annotation(com.efficient.logs.annotation.Log)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void doBefore() {
        START_TIME.set(System.currentTimeMillis());
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行，如果连接点抛出异常，则不会执行
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     *
     * @param joinPoint 切入点
     * @param result    返回结果
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void doAfterLog(JoinPoint joinPoint, Object result) {
        recordLog(joinPoint, result, null);
    }

    private void recordLog(JoinPoint joinPoint, Object resultValue, Exception exp) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 设置子线程共享
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
        if (Objects.isNull(RequestHolder.getCurrRequest())) {
            RequestHolder.set(request);
        }
        ThreadUtil.EXECUTOR_SERVICE.execute(() -> {
            final HttpServletRequest request = RequestHolder.getCurrRequest();
            final String ip = WebUtil.getIP(request);
            final String requestUrl = request.getRequestURI();
            final String token = request.getHeader(CommonConstant.TOKEN);
            try {
                // 从切面织入点处通过反射机制获取织入点处的方法
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                // 获取请求的类名
                String className = joinPoint.getTarget().getClass().getName();
                // 获取切入点所在的方法
                Method method = signature.getMethod();
                Log log = method.getAnnotation(Log.class);
                String argsStr = null;
                Object[] args = joinPoint.getArgs();
                if (Objects.equals(log.logOpt(), LogEnum.IMPORT) && args.length > 0 && args[0] instanceof MultipartFile) {
                    MultipartFile multipartFile = (MultipartFile) args[0];
                    argsStr = multipartFile.getOriginalFilename();
                } else {
                    argsStr = JackSonUtil.toJson(args);
                }

                long endTime = System.currentTimeMillis();
                String resultCode = "-1";
                String returnValue = JackSonUtil.toJson(resultValue);
                String expStr = null;
                if (resultValue instanceof Result) {
                    Result result = (Result) resultValue;
                    resultCode = String.valueOf(result.getCode());
                }

                if (Objects.nonNull(exp)) {
                    String message = exp.getMessage();
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    exp.printStackTrace(pw);
                    pw.close();
                    expStr = sw.toString();
                    // 有全局异常，这里输出异常日志过于重复，简化
                    LOGGER.error(String.format(REQUEST_ERROR_FORMAT, ip, requestUrl, className, argsStr, token, "无", message, 0L));
                } else {
                    LOGGER.info(String.format(REQUEST_NORMAL_FORMAT, ip, requestUrl, className, argsStr, token, returnValue, (endTime - START_TIME.get())));
                }

                // 获取操作
                if (logsProperties.isDb()) {
                    String parseContent = this.parseContent(log, joinPoint);
                    logService.saveLog(log, ip, parseContent, requestUrl, argsStr, resultCode, returnValue, expStr);
                }
            } catch (Exception e) {
                LOGGER.error("日志记录异常：", e);
            }
        });
    }

    /**
     * 解析数据
     *
     * @param log
     * @param joinPoint
     * @return
     */
    public String parseContent(Log log, JoinPoint joinPoint) {
        // 获取存在Spel表达式的属性
        String content = log.desc();
        if (StrUtil.isBlank(content)) {
            return null;
        }
        List<String> templates = Lists.newArrayList(content);
        templates = templates.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
        // 解析SPEL属性和方法
        HashMap<String, String> processMap = logSpelProcess.processBeforeExec(templates, joinPoint);
        // 解析三目运算
        HashMap<String, String> process = logSpelProcess.ternaryProcess(processMap, joinPoint);
        return process.get(log.desc());
    }

    @AfterThrowing(value = "pointCut()", throwing = "exp")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exp) {
        recordLog(joinPoint, null, exp);
    }
}
