package com.lxbStu.service;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:29:41
//@Authors : 罗雄波
//@File : UserService.java
//@Software : IntelliJ IDEA

import com.lxbStu.entity.User;

public interface UserService {
    User getUserByUserName(String userName);
}
