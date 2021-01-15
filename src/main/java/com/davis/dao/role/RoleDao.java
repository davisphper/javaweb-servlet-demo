package com.davis.dao.role;

import com.davis.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleDao {

    //角色列表
    public List<Role> getRoleList(Connection connection) throws Exception;
}
