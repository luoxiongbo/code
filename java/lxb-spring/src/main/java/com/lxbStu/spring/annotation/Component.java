package com.lxbStu.spring.annotation;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 10:54:02
//@Authors : 罗雄波
//@File : Component.java
//@Software : IntelliJ IDEA

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String value() default "";
}
