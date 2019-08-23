package com.tmall.service;

import com.tmall.pojo.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void delete(int id);
    void update(User user);
    User get(int id);
    List<User> list();
    // 用户注册时，用来判断用户名是否已存在的
    boolean isExist(String name);
}