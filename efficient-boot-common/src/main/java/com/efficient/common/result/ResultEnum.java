package com.efficient.common.result;

/**
 * @author TMW
 * @since 2022/2/24 17:13
 */
public enum ResultEnum implements ResultConstant {

    FAILED(-1, "操作失败！"),
    SUCCESS(200, "操作成功！"),
    /**
     * 系统相关
     */
    NOT_LOGIN(9992, "用户未登录！"),
    NOT_IDEMPOTENCE(9993, "请勿重复提交！"),
    ERROR_METHOD(9994, "请求方式错误！"),
    ERROR_PATH(9995, "请求路径错误！"),
    NOT_PERMISSION(9996, "权限不足！"),
    PARA_ERROR(9997, "参数错误！"),
    DATA_NOT_EXIST(9998, "数据不存在！"),
    ERROR(9999, "系统繁忙！");

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
