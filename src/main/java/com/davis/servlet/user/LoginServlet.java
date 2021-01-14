package com.davis.servlet.user;

import com.davis.pojo.User;
import com.davis.service.UserServicelmpl;
import com.davis.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String password = req.getParameter("userPassword");

        //和数据库中的账户和密码对比, 调用业务层
        UserServicelmpl userServicelmpl = new UserServicelmpl();
        User login = userServicelmpl.login(userCode, password);
        if (login != null) { //正确
            System.out.println("login success!");
            //将用户信息放入session
            req.getSession().setAttribute(Constants.USER_SESSION, login);
            //跳转到首页
            resp.sendRedirect("jsp/frame.jsp");
        } else {//失败
            //转发回登录页面并指定错误信息
            req.setAttribute("error", "登陆失败, 账号或密码错误!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
