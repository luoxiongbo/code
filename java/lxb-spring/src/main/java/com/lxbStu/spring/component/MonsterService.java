package com.lxbStu.spring.component;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 11:25:22
//@Authors : 罗雄波
//@File : MonsterService.java
//@Software : IntelliJ IDEA

import com.lxbStu.spring.annotation.Autowired;
import com.lxbStu.spring.annotation.Component;
import com.lxbStu.spring.annotation.ComponentScan;
import com.lxbStu.spring.annotation.Scope;
import com.lxbStu.spring.ioc.BeanDefinition;
import com.lxbStu.spring.ioc.LxbSpringApplicationContext;
import com.lxbStu.spring.processe.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Scope("prototype")
@Component("monsterService")
public class MonsterService {
    @Autowired
    private MonsterDao monsterDao;
    public void m1() {
        monsterDao.hi();
    }
}
