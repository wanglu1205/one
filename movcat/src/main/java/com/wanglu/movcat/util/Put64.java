package com.wanglu.movcat.util;

import com.qiniu.util.*;
import okhttp3.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

public class Put64 {
    String ak = "bTsY0bxaZZkYHuwYxKNqbsBtMkskEbLLwxfENMAP";
    String sk = "4D0YAGlZqNDlTDZYKwPNQ81UTMqos2170xgVMx8b";    // 密钥配置
    Auth auth = Auth.create(ak, sk);    // TODO Auto-generated constructor stub
    String bucketname = "head";    //空间名
    String key = UUID.randomUUID().toString() +".png";    //上传的图片名
    public String getUpToken() {
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }
    public String put64image(String file) throws Exception {
        FileInputStream fis = null;
        int l = (int) (new File(file).length());
        byte[] src = new byte[l];
        fis = new FileInputStream(new File(file));
        fis.read(src);
        String file64 = Base64.encodeToString(src, 0);
        String url = "http://upload.qiniu.com/putb64/" + l+"/key/"+ UrlSafeBase64.encodeToString(key);
   //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        if (response.code() == 200 && response.isSuccessful()){
            return "http://ou9egk5k7.bkt.clouddn.com/"+key;
        }else {
            return null;
        }
    }
     public static void main(String[] args) throws Exception {
         Put64 put64 = new Put64();
         String s = put64.put64image("F:\\one\\movcat\\src\\main\\resources\\static\\img\\1.jpg");
         System.out.println(s);
     }
}