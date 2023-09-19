package com.lxbStu.dao;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:34:12
//@Authors : 罗雄波
//@File : UserDao.java
//@Software : IntelliJ IDEA

import com.lxbStu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDao {
    private static Map<String, User> users = null;

    static {
        users = new HashMap<String, User>();
        User user = new User();
        user.setUserId("1001");
        user.setUserName("C语言中文网默认用户");
        user.setPassword("123456789");

        User user1 = new User();
        user1.setUserId("1002");
        user1.setUserName("南忘");
        user1.setPassword("qwertyuiop");

        User user2 = new User();
        user2.setUserId("1003");
        user2.setUserName("Java用户");
        user2.setPassword("987654321");

        User user3 = new User();
        user3.setUserId("1004");
        user3.setUserName("李小龙");
        user3.setPassword("1qazxsw2");

        users.put(user.getUserName(), user);
        users.put(user1.getUserName(), user1);
        users.put(user2.getUserName(), user2);
        users.put(user3.getUserName(), user3);
    }

    public List getUserList() {
        List userList = new ArrayList();
        Set<String> keys = users.keySet();
        for (String key : keys) {
            User user = users.get(key);
            userList.add(user);
        }
        return userList;
    }

    public User getUserByUserName(String userName) {
        User user = users.get(userName);
        return user;
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void update(User user) {
        users.put(user.getUserId(), user);
    }

    public void delete(String userId) {
        users.remove(userId);
    }
}
