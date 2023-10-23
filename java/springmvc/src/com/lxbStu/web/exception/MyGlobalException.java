package com.lxbStu.web.exception;
//-*- code = utf-8 -*-
//@Time : 2023-09-24 18:14:30
//@Authors : 罗雄波
//@File : MyGlobalException.java
//@Software : IntelliJ IDEA

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

// 加这个注解就表示是全局注解
@ControllerAdvice
public class MyGlobalException {

    @ExceptionHandler({ClassCastException.class, NumberFormatException.class})
    public String test(Exception ex, HttpServletRequest request) {
        System.out.println("错误是 : " + ex.getMessage());
        request.setAttribute("error", ex.getMessage());
        System.out.println("===MyGlobalException test()===");
        return "exception_mes";
    }
}
