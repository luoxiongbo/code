package com.lxbStu.web;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:27:22
//@Authors : 罗雄波
//@File : LoginController.java
//@Software : IntelliJ IDEA

import com.lxbStu.entity.User;
import com.lxbStu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping("/user")
    public String sayHello() {
        return "user";
    }

    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        User user2 = userService.getUserByUserName(user.getUserName());
        if (user2 != null && user2.getPassword().equals(user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user2);
            return "redirect:/getProductList";
        }
        request.setAttribute("msg", "账号或密码错误！");
        return "user";
    }
}
