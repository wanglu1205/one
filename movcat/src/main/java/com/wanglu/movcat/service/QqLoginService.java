package com.wanglu.movcat.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface QqLoginService {

    //授权
    void auth(String url, HttpServletResponse response);

    //回调
    void callback(String code, String redirecturi, HttpServletResponse response, HttpServletRequest request);
}
