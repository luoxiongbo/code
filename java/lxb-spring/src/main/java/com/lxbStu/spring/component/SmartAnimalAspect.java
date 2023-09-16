package com.lxbStu.spring.component;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 16:18:11
//@Authors : 罗雄波
//@File : SmartAnimalAspect.java
//@Software : IntelliJ IDEA

import com.lxbStu.spring.annotation.After;
import com.lxbStu.spring.annotation.Aspect;
import com.lxbStu.spring.annotation.Before;
import com.lxbStu.spring.annotation.Component;


@Aspect
@Component
public class SmartAnimalAspect {
    @Before("execution com.lxbStu.spring.component.SmartDog getSum")
    public void showBeginLog() {
        System.out.println("前置通知");
    }

    @After("execution com.lxbStu.spring.component.SmartDog getSum")
    public void showSuccessEndLog() {
        System.out.println("返回通知");
    }
}
