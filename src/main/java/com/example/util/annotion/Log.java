package com.example.util.annotion;

import com.example.util.LogEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /** 模块 */
    String title() default "";

    /** 操作类型 */
    LogEnum type() default LogEnum.DEFAULT;
}
