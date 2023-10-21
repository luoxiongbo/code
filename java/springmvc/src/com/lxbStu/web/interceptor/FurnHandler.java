package com.lxbStu.web.interceptor;
//-*- code = utf-8 -*-
//@Time : 2023-09-24 16:59:52
//@Authors : 罗雄波
//@File : FurnHandler.java
//@Software : IntelliJ IDEA

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FurnHandler {
    @RequestMapping(value = "/hi")
    public String hi() {
        System.out.println("===FurnHandle  hi()===");
        return "success";
    }

    @RequestMapping(value = "/hello")
    public String hello() {
        System.out.println("===FurnHandle  hello()===");
        return "success";
    }
}
