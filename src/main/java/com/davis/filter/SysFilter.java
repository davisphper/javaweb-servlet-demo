package com.davis.filter;

import com.davis.pojo.User;
import com.davis.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //从session获取用户
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        //判断用户登录是否有效
        if (user == null) { //登录失效, 重定向至登录页
            response.sendRedirect("/login.jsp");
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    public void destroy() {

    }
}
