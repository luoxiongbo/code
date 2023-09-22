package com.lxb.entity;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 18:48:16
//@Authors : 罗雄波
//@File : Monster.java
//@Software : IntelliJ IDEA

public class Monster {
    private Integer id;
    private String name;
    private String skill;
    private Integer age;

    public Monster() {
    }

    public Monster(Integer id, String name, String skill, Integer age) {
        this.id = id;
        this.name = name;
        this.skill = skill;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
