package com.wanglu.movcat.controller;

import com.wanglu.movcat.model.*;
import com.wanglu.movcat.service.*;
import com.wanglu.movcat.util.constant.LocalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private GiftArticleService giftArticleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CountService countService;

    @Autowired
    private BackgroundService backgroundService;

    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        List<GiftArticle> giftArticleList = giftArticleService.findByIsShow();
        List<GiftArticleVo> giftArticleVoList = new ArrayList<>();
        Background background = backgroundService.randomfindBackground();
        for (GiftArticle giftArticle:giftArticleList) {
            Integer id = giftArticle.getId();
            Count count = countService.get(LocalConstant.GiftArticle, id);
            Comment indexComment = commentService.findFirstByIsIndexAndAndGiftArticleId(id);
            GiftArticleVo giftArticleVo = null;
            if (indexComment != null){
                User user = userService.findOne(indexComment.getUserId());
                giftArticleVo = new GiftArticleVo(giftArticle.getId(), giftArticle.getImgUrl(),
                        giftArticle.getTitle(), count.getCommentCount(), count.getPraiseCount(), count.getShareCount(),
                        count.getTodayBrowsingCount(), count.getTotalBrowsingCount(),
                        user.getId(), user.getName(), user.getImgUrl(), indexComment.getContent());
            }else {
                giftArticleVo = new GiftArticleVo(giftArticle.getId(), giftArticle.getImgUrl(),
                        giftArticle.getTitle(), count.getCommentCount(), count.getPraiseCount(), count.getShareCount(),
                        count.getTodayBrowsingCount(), count.getTotalBrowsingCount());
            }

            giftArticleVoList.add(giftArticleVo);
        }
        model.addAttribute("giftArticleVoList", giftArticleVoList);
        model.addAttribute("background", background);
        return "/index";
    }

    /**
     * 详情页
     * @return
     */
    @GetMapping("/detail")
    public String detail(Model model) {
        return "/detail";
    }

    /**
     * 搜索页
     * @return
     */
    @GetMapping("/search")
    public String search(Model model) {
        return "/search";
    }

}
