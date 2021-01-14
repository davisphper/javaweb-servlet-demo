package com.davis.dao.user;

import com.davis.pojo.User;

import java.sql.Connection;

public interface UserDao {
    //等到要登录的用户
    public User getLoginUser(Connection connection, String userCode, String password) throws Exception;
}
