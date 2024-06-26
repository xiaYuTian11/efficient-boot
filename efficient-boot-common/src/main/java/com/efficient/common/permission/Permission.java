package com.efficient.common.permission;

import com.efficient.common.constant.MenuRelation;

import java.lang.annotation.*;

/**
 * 权限注解
 *
 * @author TMW
 * @since 2021/4/9 11:11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Permission {
    /**
     * 菜单集合
     */
    String[] value() default {};

    /**
     * 系统标识
     *
     * @return
     */
    String systemId() default "";

    /**
     * 菜单之间的权限
     */
    MenuRelation relation() default MenuRelation.OR;
}
