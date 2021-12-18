package com.example.demo.service.impl;

import com.example.demo.dao.UserDAO;
import com.example.demo.entity.TokenEntity;
import com.example.demo.entity.User;
import com.example.demo.entity.Result;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import com.example.demo.util.TokenConstant;

import java.util.Map;


@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Result<User> register(User user) {
        Result<User> result = new Result<>();
        //先去数据库找用户名是否存在
        User getUser = null;
        try {
            getUser = userDAO.getByUsername(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser != null) {
            result.setResultFailed("该用户名已存在！", 403);
            return result;
        }
        //加密储存用户的密码
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        //存入数据库
        userDAO.add(user);
        //返回用户数据，成功消息
        result.setResultSuccess("注册用户成功！", user, 201);
        return result;
    }

    @Override
    public Result<TokenEntity> login(User user) {
        Result<TokenEntity> result = new Result<>();
        //去数据库查找用户
        User getUser = null;
        try {
            getUser = userDAO.getByUsername(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser == null) {
            result.setResultFailed("用户不存在！", 400);
            return result;
        }
        //比对密码（数据库取出用户的密码是加密的，因此要把前端传来的用户密码加密再比对）
        if (!getUser.getPassword().equals(DigestUtils.md5Hex(user.getPassword()))) {
//        if (!getUser.getPassword().equals(user.getPassword())) {
            result.setResultFailed("用户名或者密码错误！", 400);
            return result;
        }

        String tokenString = TokenUtil.sign(getUser.getUsername(),getUser.getId().toString());
        TokenEntity token = new TokenEntity();
        token.updateToken(tokenString);
        //设定登录成功消息以及用户信息
        result.setResultSuccess("登录成功！", token, 200);
        return result;
    }

    @Override
    public Result<User> update(User user) {
        Result<User> result = new Result<>();
        if (user.getId() == null) {
            result.setResultFailed("用户id不可为空！", 401);
            return result;
        }
        //去数据库查找用户
        User getUser = null;
        try {
            getUser = userDAO.getById(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getUser == null) {
            result.setResultFailed("用户不存在！", 400);
            return result;
        }
        //检测传来的对象里面字段值是否为空，若是就用数据库里面的对象相应字段值补上
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(getUser.getPassword());
        } else {
            //加密储存
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
//            user.setPassword(user.getPassword());
        }
        //存入数据库
        userDAO.update(user);
        result.setResultSuccess("修改用户成功！", user, 200);
        return result;
    }

}
