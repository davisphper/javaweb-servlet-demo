package com.davis.dao.user;

import com.davis.dao.BaseDao;
import com.davis.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaolmpl implements UserDao {

    /**
     * 获取登录用户
     *
     * @param connection
     * @param userCode
     * @param password
     * @return
     * @throws Exception
     */
    public User getLoginUser(Connection connection, String userCode, String password) throws Exception {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        if (connection != null) {
            String sql = "select * from smbms_user where userCode = ? and userPassword = ?";
            Object[] params = {userCode, password};

            resultSet = BaseDao.execute(connection, resultSet, preparedStatement, sql, params);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.close(null, preparedStatement, resultSet);
        }
        return user;
    }

    /**
     * 修改当前登录用户密码
     *
     * @param connection
     * @param id
     * @param password
     * @return
     * @throws Exception
     */
    public int updatePwd(Connection connection, int id, String password) throws Exception {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;
        if (connection != null) {
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {password, id};
            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.close(null, preparedStatement, null);
        }
        return updateRows;
    }
}
