package com.lxb.service.impl;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 18:47:21
//@Authors : 罗雄波
//@File : MonsterServiceImpl.java
//@Software : IntelliJ IDEA

import com.lxb.entity.Monster;
import com.lxb.service.MonsterService;
import com.lxb.springmvc.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonsterServiceImpl implements MonsterService {

    @Override
    public List<Monster> monsterList() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(100, "牛魔王", "芭蕉扇", 500));
        monsters.add(new Monster(200, "蜘蛛精", "吐口水", 200));
        return monsters;
    }

    @Override
    public List<Monster> monsterFind(String name) {
        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(100, "牛魔王", "芭蕉扇", 500));
        monsters.add(new Monster(200, "蜘蛛精", "吐口水", 200));
        monsters.add(new Monster(300, "白骨精", "吸人气", 200));
        monsters.add(new Monster(400, "青牛怪", "顶人技", 200));

        ArrayList<Monster> findMonsters = new ArrayList<>();
        for (Monster monster : monsters) {
            if(monster.getName().contains(name)) {
                findMonsters.add(monster);
            }
        }
        return findMonsters;
    }

    @Override
    public boolean login(String name) {
        return "白骨精".equals(name);
    }
}
