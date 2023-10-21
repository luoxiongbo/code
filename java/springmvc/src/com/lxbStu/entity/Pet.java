package com.lxbStu.entity;
//-*- code = utf-8 -*-
//@Time : 2023-10-21 10:37:40
//@Authors : 罗雄波
//@File : Pet.java
//@Software : IntelliJ IDEA

public class Pet {
    private Integer id;
    private String name;

    public Pet() {

    }

    public Pet(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
