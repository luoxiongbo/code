package com.lxbStu.web.interceptor;
//-*- code = utf-8 -*-
//@Time : 2023-09-24 16:52:19
//@Authors : 罗雄波
//@File : MyInterceptor01.java
//@Software : IntelliJ IDEA

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor01 implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) {
        System.out.println("===MyInterceptor01 preHandle()===");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest arg0,
                           HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) {
        System.out.println("===MyInterceptor01 postHandle()===");
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1,
                                Object arg2, Exception arg3) throws Exception {
        System.out.println("===MyInterceptor01 afterCompletion()===");
    }
}
