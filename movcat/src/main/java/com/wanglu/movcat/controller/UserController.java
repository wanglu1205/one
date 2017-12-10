package com.wanglu.movcat.controller;

import com.wanglu.movcat.model.GiftArticle;
import com.wanglu.movcat.model.Result;
import com.wanglu.movcat.model.User;
import com.wanglu.movcat.service.GiftArticleService;
import com.wanglu.movcat.service.UserService;
import com.wanglu.movcat.util.Put64;
import com.wanglu.movcat.util.ResultUtil;
import com.wanglu.movcat.util.constant.LocalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.UUID;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GiftArticleService giftArticleService;

    /**
     * 去写文章页面
     * @return
     */
    @GetMapping("/write")
    public String write(Model model) {
        return "/write";
    }


    @PostMapping("/upload")
    @ResponseBody
    public Result uoload(String image) throws Exception{
        return userService.uoload(image);
    }

    /**
     * 保存文章
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Result save(HttpServletRequest request, GiftArticle giftArticle) {
        if (!StringUtils.hasText(giftArticle.getTitle())){
            return ResultUtil.error("请输入标题");
        }
        if (!StringUtils.hasText(giftArticle.getImgUrl())){
            return ResultUtil.error("请上传展示图");
        }
        if (!StringUtils.hasText(giftArticle.getDetails())){
            return ResultUtil.error("富文本内容不能为空");
        }
        User user = (User) request.getSession().getAttribute(LocalConstant.LOGIN_USER);
        GiftArticle ga = new GiftArticle(giftArticle.getTitle(), giftArticle.getImgUrl(), user.getId(), 1, giftArticle.getDetails(), "Y", new Date());
        GiftArticle result = giftArticleService.save(ga);
        if (result !=null) {
            return ResultUtil.success("保存成功");
        }else {
            return ResultUtil.error("保存异常");
        }
    }

}
