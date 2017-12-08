package com.wanglu.movcat.service.impl;

import com.alibaba.fastjson.JSON;
import com.wanglu.movcat.model.User;
import com.wanglu.movcat.properties.qqLoginProperties;
import com.wanglu.movcat.service.QqLoginService;
import com.wanglu.movcat.service.UserService;
import com.wanglu.movcat.util.constant.LocalConstant;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Service
public class QqLoginServiceImpl implements QqLoginService{

    @Autowired
    private qqLoginProperties qqLoginProperties;

    @Autowired
    private UserService userService;

    @Override
    public void auth(String url, HttpServletResponse response) {
        StringBuffer authorizeURL = new StringBuffer(qqLoginProperties.getAuthorizeURL());
        try {
            authorizeURL.append("?response_type=" + qqLoginProperties.getResponseType())
                        .append("&client_id=" + qqLoginProperties.getAppID())
                        .append("&redirect_uri=")
                        .append(URLEncoder.encode(qqLoginProperties.getRedirectURI()+ "?redirecturi=" + url, "UTF-8"))
                        .append("&state=" + qqLoginProperties.getState());

            response.sendRedirect(authorizeURL.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(String code, String redirecturi, HttpServletResponse response, HttpServletRequest request) {
        StringBuffer accessTokenURL = new StringBuffer(qqLoginProperties.getAccessTokenURL());

        try {
            accessTokenURL.append("?grant_type=" + qqLoginProperties.getGrantType())
                          .append("&client_id=" + qqLoginProperties.getAppID())
                          .append("&client_secret=" + qqLoginProperties.getAppKEY())
                          .append("&redirect_uri=").append(URLEncoder.encode(redirecturi,"UTF-8"))
                          .append("&code=" + code);
            String result = httGet(accessTokenURL.toString());
            System.out.println("result:" + redirecturi);
            String access_token = result.split("&")[0].split("=")[1];
            System.out.println("access_token:" + access_token);
            String str = httGet(qqLoginProperties.getGetOpenIDURL() + "?access_token=" + access_token);
            Map map = JSON.parseObject(str.substring(9, str.length() - 3), Map.class);
            String openid = String.valueOf(map.get("openid"));
            User qqUser = userService.findOpenId(openid);
            if (qqUser == null){
                String getUserInfoUrl = qqLoginProperties.getGetUserInfoURL() + "?access_token=" + access_token
                        +"&oauth_consumer_key=" + qqLoginProperties.getAppID() + "&openid=" + openid + "&format=json";
                String userInfo = httGet(getUserInfoUrl);
                Map userInfoMap = JSON.parseObject(userInfo, Map.class);
                String nickname = String.valueOf(userInfoMap.get("nickname"));
                String gender = String.valueOf(userInfoMap.get("gender"));
                String province = String.valueOf(userInfoMap.get("province"));
                String city = String.valueOf(userInfoMap.get("city"));
                String imgUrl = String.valueOf(userInfoMap.get("figureurl_qq_2"));
                User user = new User(nickname, imgUrl, gender, province, city, openid);
                User u = userService.saveQqUser(user);
                request.getSession().setAttribute(LocalConstant.LOGIN_USER, u);
            }else {
                request.getSession().setAttribute(LocalConstant.LOGIN_USER, qqUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            response.sendRedirect(redirecturi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String httGet(String url){
        String result = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpResponse response=client.execute(get);
            if (response.getStatusLine().getStatusCode()== 200) {
                HttpEntity entity = response.getEntity();
                if (entity!=null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
