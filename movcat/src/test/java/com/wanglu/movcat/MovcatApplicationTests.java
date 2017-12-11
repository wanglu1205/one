package com.wanglu.movcat;

import com.alibaba.fastjson.JSON;
import com.wanglu.movcat.model.GiftArticle;
import com.wanglu.movcat.model.IdentifyingCode;
import com.wanglu.movcat.model.User;
import com.wanglu.movcat.service.CountService;
import com.wanglu.movcat.service.UserService;
import com.wanglu.movcat.util.RedisTemplateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovcatApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private CountService countService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {

	}

	//用户点赞列表
	@Test
	public void praiseList() {
		List<GiftArticle> giftArticleList = userService.praiseList(1);
		System.out.println(giftArticleList);
	}

	//注册
	@Test
	public void register() {
		User user = new User("xiaofa", "1241933832@qq.com", "1241933832@qq.com", "c4ca4238a0b923820dcc509a6f75849b");
		User user1 = userService.saveUser(user);
		System.out.println(user1);
	}

	//验证码
	@Test
	public void sendCode() {
		ValueOperations data = RedisTemplateUtil.setRedisTemplate(redisTemplate).opsForValue();
		String key =  "IdentifyingCode:38F333D1CB3ACB0A46D9B14F1114A7B1";
		IdentifyingCode identifyingCode = JSON.parseObject(String.valueOf(data.get(key)), IdentifyingCode.class);
		System.out.println(identifyingCode);
	}
}
