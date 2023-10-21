package com.lxbStu.web.interceptor;
//-*- code = utf-8 -*-
//@Time : 2023-09-24 17:26:58
//@Authors : 罗雄波
//@File : MyInterceptor02.java
//@Software : IntelliJ IDEA

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor02 implements HandlerInterceptor {
    /**
     * 1.pre 方法在目标方法执行之前被调用. * 2.返回 false, 则不会再执行目标方法. 可以在此响应请求返回给页面
     * 3.不管返回 true 还是 false
     * 会执行当前拦截器之前的拦截器的 afterCompletion 方法. 注意:不会执行当前拦
     * 截器的 afterCompletion 方法.
     */
    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        // System.out.println("preHandle.....");
        // String mess = "炸弹";
        // if(mess.equals("炸弹")){
        // //返回到一个警告页面
        // arg0.getRequestDispatcher("/WEB-INF/pages/warning.jsp").forward(arg0, arg1);
        // return false;
        // }else{
        // return true;
        // }
        System.out.println("===MyInterceptor02 preHandle()===");
        return false;
    }

    /**
     * 说 明 在 目 标 方 法 被 执 行 之 后 执 行 . 可 以 在 方 法 中 访 问 到 目 标 方 法 返 回 的
     * ModelAndView 对象.
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
        System.out.println("===MyInterceptor02 postHandle()===");
    }

    /**
     * 若 preHandle 返回 true, 则方法在渲染视图之后被执行. * 若 preHandle 返回 false, 则该方法不会被调用. * 若当前拦截器的下一个拦截器的 preHandle 方法返回 false, 则在执行下一个拦截器
     * 韩顺平 Java 工程师
     * preHandle 方法后马上被执行. * 可以访问到目标方法中出现的异常.
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        System.out.println("===MyInterceptor02 afterCompletion()===");
    }
}
