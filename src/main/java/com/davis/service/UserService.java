package com.davis.service;

import com.davis.pojo.User;

public interface UserService {
    //用户登录
    public User login(String userCode, String password);
}
