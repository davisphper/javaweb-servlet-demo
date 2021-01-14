package com.davis.service;

import com.davis.dao.BaseDao;
import com.davis.dao.user.UserDao;
import com.davis.dao.user.UserDaolmpl;
import com.davis.pojo.User;

import java.sql.Connection;

public class UserServicelmpl implements UserService {
    //业务层都会调用dao层,所以我们需要引入dao层
    private UserDao userDao;

    public UserServicelmpl() {
        userDao = new UserDaolmpl();
    }

    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        connection = BaseDao.getConnection();
        try {
            user = userDao.getLoginUser(connection, userCode, password);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.close(connection, null, null);
        }
        return user;
    }

}
