package com.davis.service;

import com.davis.pojo.User;

public interface UserService {
    //用户登录
    public User login(String userCode, String password);

    //更加用户id修改密码
    public boolean updatePwd(int id, String password);
}
