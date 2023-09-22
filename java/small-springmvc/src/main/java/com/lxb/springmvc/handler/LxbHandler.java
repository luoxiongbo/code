package com.lxb.springmvc.handler;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 17:04:03
//@Authors : 罗雄波
//@File : LxbHandler.java
//@Software : IntelliJ IDEA

import java.lang.reflect.Method;

public class LxbHandler {
    private String url;
    private Object controller;
    private Method method;

    public LxbHandler() {
    }

    public LxbHandler(String url, Object controller, Method method) {
        this.url = url;
        this.controller = controller;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "LxbHandler{" +
                "url='" + url + '\'' +
                ", controller=" + controller +
                ", method=" + method +
                '}';
    }
}
