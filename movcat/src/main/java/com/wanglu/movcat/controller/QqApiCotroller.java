package com.wanglu.movcat.controller;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


@Controller
@RequestMapping("/qq")
public class QqApiCotroller {
    private String client_id="101420839";
    private String client_secret="eff4e7f658edc1b840ae008c68e7cf84";

   /* @Autowired
    private CloudQueryRunner run;*/

    @RequestMapping(value = "/auth")
    public void auth(String url, HttpServletResponse response) {
        StringBuffer codeUrl = new StringBuffer("https://graph.qq.com/oauth2.0/authorize");
        try {
            codeUrl.append("?response_type="+"code")
                   .append("&client_id="+this.client_id)
                   .append("&redirect_uri=")
                   .append(URLEncoder.encode("http://1r674k4848.iask.in:18743/qq/findUser?redirecturi=" + url, "UTF-8"))
                   .append("&state="+"1389");
            response.sendRedirect(codeUrl.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /***
     * 重定向跳转链接url
     */
    @RequestMapping(value = "/findUser")
    public void getUserInfo(String code, String redirecturi, HttpServletResponse response){
        System.out.println("重定向进来了："+code);
        System.out.println("redirecturi进来了："+redirecturi);

        StringBuffer codeUrl=new StringBuffer("https://graph.qq.com/oauth2.0/token");

        try {
            codeUrl.append("?grant_type="+"authorization_code")
                   .append("&client_id="+this.client_id)
                   .append("&client_secret="+this.client_secret)
                   .append("&redirect_uri=").append(URLEncoder.encode(redirecturi,"UTF-8"))
                   .append("&code="+code);
            System.out.println("重定向地址url:"+codeUrl);
            String result = get(codeUrl.toString());
            String[] split = result.split("&");
            System.out.println(split);
            String s1 = split[0];
            String[] split1 = s1.split("=");
            String access_token=split1[1];

//            JsonElement Json=new JsonParser().parse(result);
//            Map map=new Gson().fromJson(Json,Map.class);
//            String access_token = Objects.toString(map.get("access_token"));

            String s_openid = get("https://graph.qq.com/oauth2.0/me?access_token=" + access_token);
            String substring = s_openid.substring(9, s_openid.length()-3);
            System.out.println("s_openid截取后："+substring);
            /*JsonElement Json_openid=new JsonParser().parse(substring);
            Map map_openid=new Gson().fromJson(Json_openid,Map.class);
            String openid = Objects.toString(map_openid.get("openid"));
            String get_user_url="https://graph.qq.com/user/get_user_info?access_token="+access_token
                    +"&oauth_consumer_key="+this.client_id+"&openid="+openid+"&format=json";
            String s_user = get(get_user_url);
            JsonElement Json_user=new JsonParser().parse(s_user);
           *//* Map map_user=new Gson().fromJson(Json_user,Map.class);
            System.out.println("用户信息"+map_user);*//*
            QqMemberVo vo = new Gson().fromJson(Json_user,QqMemberVo.class);
            run.insert("QqMemberInfo",vo);
            //response.sendRedirect(url);  //处理完成后提交到前台*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            response.sendRedirect(redirecturi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String url){
        String cont=null;
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("Request Url is null");
        }
        System.out.println("POST request URL:"+url);
        //非上传资源接口，统一打印请求参数,方便日志查看纠错
        CloseableHttpClient client= HttpClients.createDefault();
        HttpGet get=new HttpGet(url);
        try {
            CloseableHttpResponse response=client.execute(get);
            if (response.getStatusLine().getStatusCode()== 200) {
                HttpEntity entity = response.getEntity();
                if (entity!=null) {
                    cont= EntityUtils.toString(entity, "UTF-8");
                    System.out.println("post response Str:"+cont);
                }
            }
            response.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }finally{
            try {
                client.close();
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return cont;
    }
}


