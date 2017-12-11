package com.wanglu.movcat.service.impl;

import com.wanglu.movcat.model.GiftArticle;
import com.wanglu.movcat.model.Result;
import com.wanglu.movcat.model.User;
import com.wanglu.movcat.repository.UserRepository;
import com.wanglu.movcat.service.GiftArticleService;
import com.wanglu.movcat.service.UserService;
import com.wanglu.movcat.util.AccountValidatorUtil;
import com.wanglu.movcat.util.Put64;
import com.wanglu.movcat.util.RedisTemplateUtil;
import com.wanglu.movcat.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GiftArticleService giftArticleService;

    @Override
    public boolean findByTelOrEmail(String telOrEmail) {
        User user = userRepository.findByTelOrEmail(telOrEmail, telOrEmail);
        if (user == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public User findByPasswordAndTelOrEmail(String telOrEmail, String password) {
        return userRepository.findByPasswordAndTelOrEmail(password, telOrEmail, telOrEmail);
    }

    @Override
    public User saveUser(User user) {
        User u = null;
        if (AccountValidatorUtil.isMobile(user.getTel())){
            user.setEmail(null);
            u = userRepository.save(user);
        }else if (AccountValidatorUtil.isEmail(user.getEmail())){
            user.setTel(null);
            u = userRepository.save(user);
        }
        return u;
    }

    @Override
    public User saveQqUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Result uoload(String image) throws Exception{
        if(StringUtils.isEmpty(image)){
            return null;
        }

        /*BASE64Decoder decoder = new BASE64Decoder();
        String s = null;
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(image);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成图片
            File filePath = new File("F:\\one\\movcat\\src\\main\\resources\\static\\upload\\");
            //File filePath = new File("/root/cat/apache-tomcat-8.5.16/webapps/ROOT/static/head/");
            //因为图表的图片后缀是png，所以后台生成的图片也是它了
            String imgFilePath = filePath+ "/"+ UUID.randomUUID().toString() +".png";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            Put64 put64 = new Put64();
            s = put64.put64image(imgFilePath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/


        BASE64Decoder decoder = new BASE64Decoder();
        String[] arr = image.split("base64,");
        File filePath = new File("F:\\one\\movcat\\src\\main\\resources\\static\\upload\\");
        //File filePath = new File("/root/cat/apache-tomcat-8.5.16/webapps/ROOT/static/head/");
        //因为图表的图片后缀是png，所以后台生成的图片也是它了
        String picPath = filePath+ "/"+ UUID.randomUUID().toString() +".png";
        String s = null;
        try {
            byte[] buffer = decoder.decodeBuffer(arr[1]);
            OutputStream os = new FileOutputStream(picPath);
            os.write(buffer);
            os.flush();
            os.close();
            Put64 put64 = new Put64();
            s = put64.put64image(picPath);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return ResultUtil.success(s);
    }

    @Override
    public Result addPraiseList(Integer userId, Integer giftArticleId)throws Exception {
        ZSetOperations data = RedisTemplateUtil.setRedisTemplate(redisTemplate).opsForZSet();
        String key = "praise:" + userId;
        Set set = data.range(key, 0, -1);
        List<String> list = new ArrayList<String>(set);
        for(String str: list){
            if (giftArticleId == Integer.valueOf(str)){
                return ResultUtil.error("已收藏过该商品");
            }
        }
        Date newTime = new Date();
        String time = "2017-09-13 00:00:00";
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date lastTime = bartDateFormat.parse(time);
        double l = (newTime.getTime() - lastTime.getTime()) / 1000;
        Boolean b = data.add(key, String.valueOf(giftArticleId), l);
        if (b){
            return ResultUtil.success("分享成功");
        }else {
            return ResultUtil.success("分享失败");
        }
    }

    @Override
    public List<GiftArticle> praiseList(Integer userId) {
        ZSetOperations data = RedisTemplateUtil.setRedisTemplate(redisTemplate).opsForZSet();
        String key = "praise:" + userId;
        Set set = data.range(key, 0, -1);
        List<String> list = new ArrayList<String>(set);
        List<GiftArticle> giftArticleList = new ArrayList<>();
        for(String str: list){
            GiftArticle giftArticle = giftArticleService.findOne(Integer.valueOf(str));
            giftArticleList.add(giftArticle);
            Collections.reverse(giftArticleList);
        }
        return giftArticleList;
    }


    @Override
    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }
}
