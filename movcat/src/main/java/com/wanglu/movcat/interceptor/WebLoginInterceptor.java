package com.wanglu.movcat.interceptor;

import com.wanglu.movcat.exception.MacroApiException;
import com.wanglu.movcat.model.User;
import com.wanglu.movcat.util.IpUtil;
import com.wanglu.movcat.util.RedisTemplateUtil;
import com.wanglu.movcat.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
public class WebLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(WebLoginInterceptor.class);

    /** 
     * 进入controller层之前拦截请求 
     * @param request
     * @param response
     * @param o 
     * @return 
     * @throws Exception 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
  
        log.info("------------------------开始进入请求地址拦截-------------------------");

        //通过IP实现网站浏览量统计分析 by wangl
        //1.获取用户真实IP
        String ip = IpUtil.getIpAddr(request);
        //2.获取String类型的日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = sdf.format(date);
        SetOperations set = RedisTemplateUtil.setRedisTemplate(redisTemplate).opsForSet();
        HashOperations hash = RedisTemplateUtil.setRedisTemplate(redisTemplate).opsForHash();
        //3.ip地址插入redis
        Long add = set.add("user_ip:"+newTime, ip);
        // =1 代表插入成功，该ip今日未访问该网站
        if (add == 1){
            //4.浏览量增加
            hash.increment("PV_IP", "today", 1);
            hash.increment("PV_IP", "all", 1);
        }

        //通过Cookie实现网站浏览量统计分析 by wangl
        //1.获取用户Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            String value = cookies[0].getValue();
            Long a = set.add("user_cookie:" + newTime, value);
            // =1 代表插入成功，该ip今日未访问该网站
            if (a == 1){
                //4.浏览量增加
                hash.increment("PV_COOKIE", "today", 1);
                hash.increment("PV_COOKIE", "all", 1);
            }
        }
        User loginUser = (User) request.getSession().getAttribute("LOGIN_USER");
        if(loginUser != null){
            return true;  
        }  
        else{
            response.setStatus(405);
            return false;
        }  
    }

  
    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }  


    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        log.info("--------------------视图渲染之后的操作--------------------");
    }  
}  