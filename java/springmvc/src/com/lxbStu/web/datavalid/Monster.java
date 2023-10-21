package com.lxbStu.web.datavalid;
//-*- code = utf-8 -*-
//@Time : 2023-09-22 10:40:11
//@Authors : 罗雄波
//@File : Monster.java
//@Software : IntelliJ IDEA

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

public class Monster {
    private Integer id;
    @NotEmpty(message = "空了@@@@@@@@@@@@@@@@@@@@")
    private String name;
    @Range(min = 1, max = 100, message = "错了@@@@@@@@@@@@@@@@@@@")
    private Integer age;
    //@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "错了")
    //说明还是没有类型转换器, 有了类型转换器才可以进行转换, 下面这个@DateTimeFormat(pattern = "yyyy-MM-dd")似乎没啥用
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    //@NumberFormat(pattern = "###,###.##")
    //@Pattern(regexp = "^\\d+,?+(\\.\\d)?$", message = "错了")
    private Float salary;
    private String email;

    public Monster() {
    }

    public Monster(Integer id, String name, Integer age, Date birthday, Float salary, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.salary = salary;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + (birthday == null ? null : new java.sql.Date(birthday.getTime())) +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                '}';
    }
}
