package com.davis.dao.user;

import com.davis.pojo.User;

import java.sql.Connection;

public interface UserDao {
    //获取要登录的用户
    public User getLoginUser(Connection connection, String userCode, String password) throws Exception;

    public int updatePwd(Connection connection, int id, String password) throws Exception;
}
