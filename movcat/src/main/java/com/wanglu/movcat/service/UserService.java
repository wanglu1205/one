package com.wanglu.movcat.service;

import com.wanglu.movcat.model.User;

public interface UserService {

    //获取用户详情
    User findOne(Integer id);

    boolean findByTelOrEmail(String telOrEmail);

    User findByPasswordAndTelOrEmail(String telOrEmail, String password);

    //注册
    User saveUser(User user);

    //通过openId查询用户
    User findOpenId(String openId);

    //保存qq用户
    User saveQqUser(User user);
}
