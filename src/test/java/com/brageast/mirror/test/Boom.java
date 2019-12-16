package com.brageast.mirror.test;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Boom {
    String value() default "";
    int num() default 0;
}
