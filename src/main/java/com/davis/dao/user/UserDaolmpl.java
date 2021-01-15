package com.davis.dao.user;

import com.davis.dao.BaseDao;
import com.davis.pojo.Role;
import com.davis.pojo.User;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 根据用户名和角色获取总数
     *
     * @param connection
     * @param userName
     * @param userRole
     * @return
     * @throws Exception
     */
    public int getUserCount(Connection connection, String userName, int userRole) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int num = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as num from smbms_user u, smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //list to array
            Object[] objects = list.toArray();

            String sqlQuery = sql.toString();

            //print sql
            System.out.println(sqlQuery);
            resultSet = BaseDao.execute(connection, resultSet, preparedStatement, sqlQuery, objects);
            if (resultSet.next()) {
                num = resultSet.getInt("num");
            }
            //关闭资源
            BaseDao.close(null, preparedStatement, resultSet);
        }
        return num;
    }

    //用户列表
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            resultSet = BaseDao.execute(connection, resultSet, preparedStatement, sql.toString(), params);
            while (resultSet.next()) {
                User _user = new User();
                _user.setId(resultSet.getInt("id"));
                _user.setUserCode(resultSet.getString("userCode"));
                _user.setUserName(resultSet.getString("userName"));
                _user.setGender(resultSet.getInt("gender"));
                _user.setBirthday(resultSet.getDate("birthday"));
                _user.setPhone(resultSet.getString("phone"));
                _user.setUserRole(resultSet.getInt("userRole"));
                _user.setUserRoleName(resultSet.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.close(null, preparedStatement, resultSet);
        }
        return userList;
    }
}















