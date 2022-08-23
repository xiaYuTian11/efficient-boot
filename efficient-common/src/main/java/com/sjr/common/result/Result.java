package com.sjr.common.result;

import java.io.Serializable;

/**
 * @author TMW
 * @since 2022/2/24 16:14
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1598014388149858217L;

    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultConstant resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultEnum.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultEnum.SUCCESS, data);
    }

    public static <T> Result<T> ok(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultEnum.FAILED, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(ResultEnum.FAILED, data);
    }

    public static <T> Result<T> fail(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> build(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> build(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> build(ResultConstant resultEnum, T data) {
        return new Result<>(resultEnum, data);
    }

    public static <T> Result<T> build(ResultConstant resultEnum) {
        return new Result<>(resultEnum, null);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
