package com.efficient.logs.aop;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.efficient.common.auth.RequestHolder;
import com.efficient.common.constant.CommonConstant;
import com.efficient.common.result.Result;
import com.efficient.common.util.JackSonUtil;
import com.efficient.common.util.ThreadUtil;
import com.efficient.common.util.WebUtil;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.api.SysLogService;
import com.efficient.logs.properties.LogsProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author TMW
 * @since 2022/9/5 15:33
 */
@Aspect
@Component
public class LogsAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogsAop.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogService logService;
    @Autowired
    private LogsProperties logsProperties;

    public static final TransmittableThreadLocal<Long> START_TIME = new TransmittableThreadLocal<>();
    private static final String REQUEST_NORMAL_FORMAT = "\n=============\nip:%s\nurl:%s\ncontroller:%s\nargs:%s\ntoken:%s\nreturn:%s\ntime:%d\n请求成功\n=============";
    private static final String REQUEST_ERROR_FORMAT = "\n=============\nip:%s\nurl:%s\ncontroller:%s\nargs:%s\ntoken:%s\nreturn:%s\nerror:%s\ntime:%d\n请求失败\n=============";

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

    @AfterThrowing(value = "pointCut()", throwing = "exp")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exp) {
        recordLog(joinPoint, null, exp);
    }

    private void recordLog(JoinPoint joinPoint, Object resultValue, Exception exp) {
        // ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 设置子线程共享
        // RequestContextHolder.setRequestAttributes(servletRequestAttributes,true);
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
                // 获取操作
                Log log = method.getAnnotation(Log.class);
                if (Objects.isNull(log)) {
                    return;
                }
                Object[] args = joinPoint.getArgs();
                String argsStr = JackSonUtil.toJson(args);

                long endTime = System.currentTimeMillis();
                String resultCode = "-1";
                String returnValue = JackSonUtil.toJson(resultValue);
                String expStr = null;
                if (resultValue instanceof Result) {
                    Result result = (Result) resultValue;
                    resultCode = String.valueOf(result.getCode());
                }

                if (Objects.nonNull(exp)) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    exp.printStackTrace(pw);
                    pw.close();
                    expStr = sw.toString();
                    LOGGER.error(String.format(REQUEST_ERROR_FORMAT, ip, requestUrl, className, argsStr, token, "无", sw, 0L), exp);
                } else {
                    LOGGER.info(String.format(REQUEST_NORMAL_FORMAT, ip, requestUrl, className, argsStr, token, returnValue, (endTime - START_TIME.get())));
                }
                if (logsProperties.isDb()) {
                    logService.saveLog(log, ip, requestUrl, argsStr, resultCode, returnValue, expStr);
                }
            } catch (Exception e) {
                LOGGER.error("日志记录异常：", e);
            }
        });
    }

}
