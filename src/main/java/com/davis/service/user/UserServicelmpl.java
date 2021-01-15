package com.davis.service.user;

import com.davis.dao.BaseDao;
import com.davis.dao.user.UserDao;
import com.davis.dao.user.UserDaolmpl;
import com.davis.pojo.User;

import java.sql.Connection;
import java.util.List;

public class UserServicelmpl implements UserService {
    //业务层都会调用dao层,所以我们需要引入dao层
    private UserDao userDao;

    public UserServicelmpl() {
        userDao = new UserDaolmpl();
    }

    //用户登录
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

    //用户修改密码
    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            int i = userDao.updatePwd(connection, id, password);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.close(connection, null, null);
        }
        return flag;
    }

    //获取查询记录数
    public int getUserCount(String userName, int userRole) {
        Connection connection = null;
        int userCount = 0;
        try {
            connection = BaseDao.getConnection();
            userCount = userDao.getUserCount(connection, userName, userRole);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.close(connection, null, null);
        }
        return userCount;
    }

    //用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.close(connection, null, null);
        }
        return userList;
    }
}
