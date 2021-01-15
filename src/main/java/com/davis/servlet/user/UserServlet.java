package com.davis.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.davis.pojo.User;
import com.davis.service.user.UserServicelmpl;
import com.davis.util.Constants;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 实现servlet复用
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd") && method != null) {
            this.updatePwd(req, resp);
        } else if (method.equals("pwdmodify") && method != null) {
            this.pwdmodify(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 验证旧密码, session中已有用户, 无需查库, 直接与session中的用户对比
     *
     * @param req
     * @param resp
     * @return
     */
    public void pwdmodify(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        HashMap<String, String> resultMap = new HashMap<String, String>();

        if (user == null) { //登录过期
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) { //旧密码为空
            resultMap.put("result", "error");
        } else {
            //验证旧密码是否正确
            if (oldpassword.equals(user.getUserPassword())) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改密码
     *
     * @param req
     * @param resp
     */
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        boolean b = false;

        //从session获取用户id
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        //接收用户传入的新密码
        String newpassword = req.getParameter("newpassword");
        //判断用户id和新密码都为真时, 才可以修改密码
        if (user != null && !StringUtils.isNullOrEmpty(newpassword)) {
            UserServicelmpl userServicelmpl = new UserServicelmpl();
            b = userServicelmpl.updatePwd(user.getId(), newpassword);
            if (b == true) {
                //修改成功
                req.setAttribute("message", "密码修改成功, 请退出重新登录!");
                //清除session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                //修改失败
                req.setAttribute("message", "密码修改失败, 请重试!");
            }
        } else {
            req.setAttribute("message", "新密码错误!");
        }
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
