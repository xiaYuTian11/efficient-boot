package com.efficient.logs.annotation;

import com.efficient.logs.constant.LogEnum;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author TMW
 * @since 2022/3/2 17:43
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface Log {

    /**
     * 操作类型
     */
    LogEnum logOpt() default LogEnum.CUSTOM;

    /**
     * 自定义操作，当logOpt为CUSTOM时生效
     *
     * @return
     */
    String customOpt() default "";

    /**
     * 模块
     */
    String module() default "";

    /**
     * 操作说明
     * get请求参数{#id}，eg: 通过 {#id} 查询用户
     * post请求参数{#dto.id},eg: 将用户id为{#dto.userId} 的用户名更新为{#dto.userName}
     * 请求方法[getUserNameByUserId{#userId}],eg:用户id为 {#userId} 用户名为 [getUserNameByUserId{#userId}] 已被删除
     * 三目运算，eg: #dto.userId == null ? '新增' + #dto.userName + '用户':'将用户id为' + #dto.userId + '的用户名更新为' + #dto.userName
     */
    String desc() default "";

    /**
     * 是否拼接日志
     */
    boolean join() default true;

}
