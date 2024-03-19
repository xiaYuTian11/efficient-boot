package com.efficient.configs.config;

import com.efficient.common.exception.DataSecurityException;
import com.efficient.common.result.Result;
import com.efficient.common.result.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

/**
 * 扩展全局异常
 * <p>
 * 针对服务器异常，返回具体异常信息
 *
 * @author TMW
 * @date 2021/2/24 11:17
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class,Throwable.class})
    @ResponseBody
    public Result<?> handler(Exception e) {
        LOGGER.error("error: {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return Result.build(ResultEnum.ERROR, sw.toString());
    }

    /**
     * 单个参数异常处理
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Result<?> handler(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        LOGGER.error("error: {}", message);
        return validateResultFormat(message);
    }

    private <T extends Throwable> Result<?> validateResultFormat(String message) {
        return Result.build(ResultEnum.PARA_ERROR, message);
    }

    /**
     * 一般参数校验绑定异常处理
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result<?> handler(BindException ex) {
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        LOGGER.error("error: {}", message);
        return validateResultFormat(message);
    }

    /**
     * 一般参数校验绑定异常处理
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result<?> handler(HttpRequestMethodNotSupportedException ex) {
        String message = ex.getMessage();
        LOGGER.error("error: {}", message);
        return validateResultFormat(message);
    }

    /**
     * 缺失参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public Result<?> handler(MissingServletRequestParameterException ex) {
        final String parameterName = ex.getParameterName();
        LOGGER.error("参数缺失: {}", parameterName);
        return validateResultFormat("参数缺失：" + parameterName);
    }

    /**
     * JSON参数校验绑定异常处理
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> handler(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        LOGGER.error("error: {}", message);
        return validateResultFormat(message);
    }

    /**
     * Http消息不可读异常返回特定的信息
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result<?> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorInfo = String.format("参数格式无效:%s", ex);
        LOGGER.error("error: {}", errorInfo);
        return Result.build(ResultEnum.ERROR, errorInfo);
    }

    /**
     * Http消息不可读异常返回特定的信息
     */
    @ExceptionHandler(DataSecurityException.class)
    @ResponseBody
    public Result<?> dataSecurityException(DataSecurityException ex) {
        LOGGER.error("error: {}", ResultEnum.DATA_SECURITY.getMsg());
        return Result.build(ResultEnum.DATA_SECURITY);
    }

}
