package com.wanglu.movcat.service;

import com.wanglu.movcat.model.GiftArticle;
import com.wanglu.movcat.model.Result;
import com.wanglu.movcat.model.User;

import java.util.List;

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

    //上传
    Result upload(String image) throws Exception;

    //加入点赞列表
    Result addPraiseList(Integer userId, Integer giftArticleId)throws Exception;

    //加入点赞列表
    List<GiftArticle> praiseList(Integer userId);
}
