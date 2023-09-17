package com.lxbStu.web;
//-*- code = utf-8 -*-
//@Time : 2023-09-17 14:23:45
//@Authors : 罗雄波
//@File : UserServlet.java
//@Software : IntelliJ IDEA

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 */
//@Controller : 被当做一个控制器，作用类似前面讲过的 Servlet
//这里配置了一个 @Controller 就已经被 springMvc 框架接管
@Controller
public class UserServlet {
    /**
     * 解读
     * 1. @RequestMapping("/login"): /login 等价于 web.xml servlet 的 url-pattern
     * 2. return "login_ok" * 2.1 将结果 "login_ok" 返回给 InternalResourceViewResolver
     * 2.2 然后跳转到哪个页面这里就是 /WEB-INF/pages/login_ok.jsp
     * @return
     */
    @RequestMapping(value = "/login")
    public String login() {
        System.out.println("login ok....");
        return "login_ok";
    }

    @PostMapping(value = "/submit")
    public String submit(String brand, String price, String mount) {
        System.out.println("品牌 : " + brand);
        System.out.println("价格 : " + price);
        System.out.println("数量 : " + mount);
        return "submit_ok";
    }

    @RequestMapping(value = "/test", params = "param")
    public String test(String param) {
        if(param != null && !param.equals("")) {
            System.out.println("测试成功!!!" + param);
        } else {
            System.out.println("测试失败!!!");
        }
        return "submit_ok";
    }

    @RequestMapping(value = "/zhanwei/{username}/{pwd}")
    public String zhanwei(@PathVariable("username")String name, @PathVariable("pwd")String pwd) {
        System.out.println("username = " + name);
        System.out.println("pwd = " + pwd);
        return "submit_ok";
    }

    @GetMapping(value = "/header", headers = {"Content-Type=application/json"})
    public String header() {
        System.out.println("测试成功!!!");
        return "submit_ok";
    }
}
