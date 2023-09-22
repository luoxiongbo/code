package com.lxb.service;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 18:46:58
//@Authors : 罗雄波
//@File : MonsterService.java
//@Software : IntelliJ IDEA

import com.lxb.entity.Monster;

import java.util.List;

public interface MonsterService {
    public List<Monster> monsterList();
    public List<Monster> monsterFind(String name);

    public boolean login(String name);
}
