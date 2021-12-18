package com.example.demo.service;

import com.example.demo.entity.TokenEntity;
import com.example.demo.entity.User;
import com.example.demo.entity.Result;
import org.springframework.stereotype.Service;

//接口中声明方法
@Service
public interface UserService {
    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    Result<User> register(User user);
    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return 登录结果
     */
    Result<TokenEntity> login(User user);
    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 修改结果
     */
    Result<User> update(User user);

}
