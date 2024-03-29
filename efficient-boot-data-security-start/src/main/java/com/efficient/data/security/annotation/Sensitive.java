package com.efficient.data.security.annotation;

import com.efficient.data.security.constant.SensitiveType;
import com.efficient.data.security.sensitive.SensitiveJsonSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 数据脱敏
 *
 * @author TMW
 * @since 2023/7/6 15:50
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
@Inherited
@Documented
public @interface Sensitive {

    SensitiveType rule();
}
