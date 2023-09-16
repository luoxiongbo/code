package com.lxbStu.spring.component;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 16:14:38
//@Authors : 罗雄波
//@File : SmartDog.java
//@Software : IntelliJ IDEA

import com.lxbStu.spring.annotation.Component;

@Component("smartDog")
public class SmartDog implements SmartAnimalable{
    @Override
    public float getSum(float i, float j) {
        float result = i + j;
        System.out.println("getSum() 方法内部打印 result= " + result);
        return result;
    }

    @Override
    public float getSub(float i, float j) {
        float result = i - j;
        System.out.println("getSub() 方法内部打印 result= " + result);
        return result;
    }
}
