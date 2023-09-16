package com.lxbStu.spring.component;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 11:26:18
//@Authors : 罗雄波
//@File : MonsterDao.java
//@Software : IntelliJ IDEA

import com.lxbStu.spring.annotation.Component;
import com.lxbStu.spring.processe.InitializingBean;

@Component("monsterDao")
public class MonsterDao implements InitializingBean {
    public void hi() {
        System.out.println("喵喵喵~~~");
    }

    /**
     * 就像之前那样, 我们是通过写一个方法在xml中配置属性的时候给初始化的属性进行赋值
     * 初始化方法可有可无, 但是后置处理器是
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MonsterService 进行初始化, 具体业务由程序员来搞定....");
    }
}
