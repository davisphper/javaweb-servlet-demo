package com.davis.dao.user;

import com.davis.pojo.Role;
import com.davis.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    //获取要登录的用户
    public User getLoginUser(Connection connection, String userCode, String password) throws Exception;

    //用户修改密码
    public int updatePwd(Connection connection, int id, String password) throws Exception;

    //根据用户名和角色获取总数
    public int getUserCount(Connection connection, String userName, int userRole) throws Exception;

    //通过条件查询-userList
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception;
}
