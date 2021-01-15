package com.davis.service.role;

import com.davis.dao.BaseDao;
import com.davis.dao.role.RoleDao;
import com.davis.dao.role.RoleDaolmpl;
import com.davis.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class RoleServicelmpl implements RoleService {
    private RoleDao roleDao;

    public RoleServicelmpl() {
        roleDao = new RoleDaolmpl();
    }

    //角色列表
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.close(connection, null, null);
        }
        return roleList;
    }

    @Test
    public void test()
    {
        RoleServicelmpl roleServicelmpl = new RoleServicelmpl();
        List<Role> roleList = roleServicelmpl.getRoleList();
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }
}
