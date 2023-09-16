package com.lxbStu.spring.utils;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 19:20:29
//@Authors : 罗雄波
//@File : SpringUtils.java
//@Software : IntelliJ IDEA

import java.lang.reflect.Method;

public class SpringUtils {
    public static Method FindMethodByName(Method[] methods, String name) {
        for (Method method : methods) {
            if(method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }
}
