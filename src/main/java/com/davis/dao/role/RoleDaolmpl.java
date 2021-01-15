package com.davis.dao.role;

import com.davis.dao.BaseDao;
import com.davis.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDaolmpl implements RoleDao{
    //角色列表
    public List<Role> getRoleList(Connection connection) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Role> roles = new ArrayList<Role>();

        if (connection != null) {
            String sql = "select * from smbms_role";
            Object[] params = {};
            resultSet = BaseDao.execute(connection, resultSet, preparedStatement, sql, params);
            while (resultSet.next()) {
                Role _role = new Role();
                _role.setId(resultSet.getInt("id"));
                _role.setRoleName(resultSet.getString("setRoleName"));
                _role.setRoleCode(resultSet.getString("setRoleCode"));
                roles.add(_role);
            }
            //关闭资源
            BaseDao.close(null, preparedStatement, resultSet);
        }
        return roles;
    }
}
