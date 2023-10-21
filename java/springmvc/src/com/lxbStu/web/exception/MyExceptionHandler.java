package com.lxbStu.web.exception;
//-*- code = utf-8 -*-
//@Time : 2023-09-24 17:39:09
//@Authors : 罗雄波
//@File : MyExeceptionHandler.java
//@Software : IntelliJ IDEA

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyExceptionHandler {
    @ExceptionHandler({ArithmeticException.class, NullPointerException.class})
    public String localException(Exception ex, HttpServletRequest request) {
        System.out.println("异常信息是 : " + ex.getMessage());

        //ExceptionHandlerMethodResolver
        request.setAttribute("error", ex.getMessage());
        return "exception_mes";
    }

    @RequestMapping(value = "/testException")
    public String test01() {
        System.out.println("===MyExceptionHandler test01()===");
        int i = 1/0;
        return "success";
    }

    @RequestMapping(value = "/testGlobalE")
    public String test02() {
        System.out.println("===testGlobal test02()===");
        Integer i = Integer.parseInt("hello");
        return "success";
    }

    @RequestMapping(value = "/testDivException")
    public String test03() {
        System.out.println("===testDivException test03()===");
        throw new AgeException();
    }
}
