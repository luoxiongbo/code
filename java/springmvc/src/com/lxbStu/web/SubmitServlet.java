package com.lxbStu.web;
//-*- code = utf-8 -*-
//@Time : 2023-09-17 15:57:12
//@Authors : 罗雄波
//@File : SubmitServlet.java
//@Software : IntelliJ IDEA

import com.lxbStu.entity.Pet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.locks.ReentrantLock;

@RequestMapping("/computer")
@Component
public class SubmitServlet {
    @PostMapping(value = "/submit")
    public String submit(@RequestParam(value = "brand", required = false) String brand,@RequestParam(value = "price", required = false) String price,@RequestParam(value = "mount", required = false) String mount) {
        System.out.println("品牌 : " + brand);
        System.out.println("价格 : " + price);
        System.out.println("数量 : " + mount);
        return "submit_ok";
    }

    @RequestMapping(value = "/head")
    public String headerTest(@RequestHeader("Accept-Encoding") String ae,
                             @RequestHeader("Host") String host) {
        System.out.println("Accept-Encoding = " + ae);
        System.out.println("Host = " + host);
        return "login_ok";
    }

    @RequestMapping(value = "/pet")
    public String JsonPetTest(Pet pet) {
        System.out.println("id 是 : " + pet.getId());
        System.out.println("姓名是 : " + pet.getName());
        return "login_ok";
    }

    @RequestMapping("/servletApi")
    public String ServletApiTest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("id 是 : " + request.getParameter("id"));
        System.out.println("name 是 : " + request.getParameter("name"));
        System.out.println("响应是 : " + response);
        return "login_ok";
    }

}
