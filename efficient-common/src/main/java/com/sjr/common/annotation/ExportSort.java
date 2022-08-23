package com.sjr.common.annotation;

import java.lang.annotation.*;

/**
 * @author TMW
 * @since 2022/5/24 16:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExportSort {
    /**
     * 排序
     *
     * @return
     */
    int value() default 0;
}
