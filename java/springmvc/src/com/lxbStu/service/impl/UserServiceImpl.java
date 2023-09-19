package com.lxbStu.service.impl;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:30:28
//@Authors : 罗雄波
//@File : UserServiceImpl.java
//@Software : IntelliJ IDEA

import com.lxbStu.dao.UserDao;
import com.lxbStu.entity.User;
import com.lxbStu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }
}
