package com.davis.service.user;

import com.davis.pojo.Role;
import com.davis.pojo.User;

import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode, String password);

    //更加用户id修改密码
    public boolean updatePwd(int id, String password);

    //查询记录数
    public int getUserCount(String userName, int userRole);

    //用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
}
