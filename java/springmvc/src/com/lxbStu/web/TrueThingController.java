//package com.lxbStu.web;
////-*- code = utf-8 -*-
////@Time : 2023-09-18 08:44:34
////@Authors : 罗雄波
////@File : TrueThingController.java
////@Software : IntelliJ IDEA
//
//import com.lxbStu.entity.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class TrueThingController {
//    /**
//     * 通过实体类来获取参数, 并将参数封装到实体里面
//     * @param user
//     * @return
//     */
//    @RequestMapping(value = "/byEntity")
//    public String getParamByEntity(User user) {
//        System.out.println("Id : " + user.getUserId());
//        System.out.println("姓名 : " + user.getUserName());
//        System.out.println("密码 : " + user.getPassword());
//        return "submit_ok";
//    }
//}
