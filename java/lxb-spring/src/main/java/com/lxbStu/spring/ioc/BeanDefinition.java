package com.lxbStu.spring.ioc;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 11:37:59
//@Authors : 罗雄波
//@File : BeanDefinition.java
//@Software : IntelliJ IDEA


import com.lxbStu.spring.annotation.Scope;

public class BeanDefinition {
    private Class clazz;
    private String scope;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setScope(String  scope) {
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public String  getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "clazz=" + clazz +
                ", scope=" + scope +
                '}';
    }
}
