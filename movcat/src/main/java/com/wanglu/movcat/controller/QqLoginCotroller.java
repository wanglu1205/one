package com.wanglu.movcat.controller;


import com.wanglu.movcat.service.QqLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/qq")
public class QqLoginCotroller {

    @Autowired
    private QqLoginService qqLoginService;

    @RequestMapping(value = "/auth")
    public void auth(String url, HttpServletResponse response) {
        qqLoginService.auth(url, response);
    }


    @RequestMapping(value = "/callback")
    public void callback(String code, String redirecturi, HttpServletResponse response, HttpServletRequest request){
        qqLoginService.callback(code, redirecturi, response, request);
    }


}


