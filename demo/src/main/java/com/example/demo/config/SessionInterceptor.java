package com.example.demo.config;

import com.example.demo.controller.UserAPI;
import com.example.demo.dao.UserDAO;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDAO userDAO;
    // Controller方法执行以前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
//获取session中用户信息，并比对
        User sessionUser = (User) ((HttpSession) session).getAttribute(UserAPI.SESSION_NAME);
//若是用户未登陆，则重定向至主页
        if (sessionUser == null) {
            response.sendRedirect("/");
            return false;
        }
        User getUser = null;
        try {
            getUser = userDAO.getById(sessionUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
//若是session中用户信息无效，则重定向至主页
        if (getUser == null || !getUser.getPassword().equals(sessionUser.getPassword())) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
    // Controller方法执行以后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    // 整个请求完成后（包括Thymeleaf渲染完毕）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
