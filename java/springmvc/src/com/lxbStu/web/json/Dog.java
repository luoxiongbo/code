package com.lxbStu.web.json;
//-*- code = utf-8 -*-
//@Time : 2023-09-22 16:49:43
//@Authors : 罗雄波
//@File : Dog.java
//@Software : IntelliJ IDEA

public class Dog {
    private String name;
    private String address;

    public Dog() {
    }

    public Dog(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}