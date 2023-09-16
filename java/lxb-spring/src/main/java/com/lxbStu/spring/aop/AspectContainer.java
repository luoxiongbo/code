package com.lxbStu.spring.aop;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 16:32:27
//@Authors : 罗雄波
//@File : AspectContainer.java
//@Software : IntelliJ IDEA

import java.util.concurrent.ConcurrentHashMap;

public class AspectContainer {
    private static final ConcurrentHashMap<Class, String[]> aspectJMap = new ConcurrentHashMap<>();
    public static void add(Class clazz, String[] value) {
        aspectJMap.put(clazz, value);
    }
    public static ConcurrentHashMap<Class, String[]> getAspectJ() {
        return aspectJMap;
    }
}
