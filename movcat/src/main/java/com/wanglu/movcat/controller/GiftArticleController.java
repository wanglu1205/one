package com.wanglu.movcat.controller;

import com.wanglu.movcat.model.*;
import com.wanglu.movcat.repository.GiftRepository;
import com.wanglu.movcat.service.CommentService;
import com.wanglu.movcat.service.CountService;
import com.wanglu.movcat.service.GiftArticleService;
import com.wanglu.movcat.service.UserService;
import com.wanglu.movcat.util.constant.CountEnum;
import com.wanglu.movcat.util.constant.LocalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/giftArticle")
public class GiftArticleController {

    @Autowired
    private GiftArticleService giftArticleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private CountService countService;

    /**
     * 文章详情
     * @return
     */
    @GetMapping("/detail")
    public String detail(Model model, Integer id) {
        GiftArticle giftArticle = giftArticleService.findOne(id);
        User user = userService.findOne(giftArticle.getUserId());
        Gift gift = giftRepository.findOne(giftArticle.getGiftId());
        List<Comment> commentList = commentService.findByIsSonAndIsGrandsonAndGiftArticleId("N", "N", id);
        Count count = countService.get(LocalConstant.GiftArticle, id);
        List<CommentVo> commentVoList = new ArrayList<>();

        GiftArticleDetailVo giftArticleDetailVo = new GiftArticleDetailVo(giftArticle.getId(), giftArticle.getImgUrl(), giftArticle.getTitle(),
                giftArticle.getCreateTime(), giftArticle.getDetails(), gift.getId(), gift.getName(),
                gift.getImgUrl(), gift.getVideoUrl(), gift.getPrice(), gift.getIntro(),
                gift.getMallUrl(), count.getCommentCount(), count.getPraiseCount(), count.getShareCount(),
                count.getTodayBrowsingCount(), count.getTotalBrowsingCount(),
                user.getId(), user.getName(), user.getImgUrl());


        for (Comment comment:commentList) {
            User u = userService.findOne(comment.getUserId());
            CommentVo commentVo = null;
            commentVo = new CommentVo(comment.getId(), comment.getContent(), comment.getFloor(), comment.getCreateTime(),
                    u.getId(), u.getName(), u.getImgUrl());
            commentVoList.add(commentVo);
        }

        List<SonCommentVo> sonCommentVoList = new ArrayList<>();
        List<GrandsonCommentVo> grandsonCommentVoList = new ArrayList<>();
        List<CommentVo> newCommentVoList = new ArrayList<>();
        for (CommentVo commentVo:commentVoList) {
            List<Comment> sonCommentList = commentService.findByFatherIdAndIsSon(commentVo.getId());
            User u = userService.findOne(commentVo.getUserId());
            for (Comment c:sonCommentList) {
                User us = userService.findOne(c.getUserId());
                SonCommentVo sonCommentVo = new SonCommentVo(c.getContent(), c.getFloor(), c.getCreateTime(), us.getId(),
                        us.getName(), us.getImgUrl(), c.getFatherId());
                Integer id1 = c.getId();
                List<Comment> grandsonCommentList = commentService.findByFatherIdAndIsGrandson(id1);
                for (Comment gc:grandsonCommentList) {
                    User use = userService.findOne(gc.getUserId());
                    Comment comment = commentService.findOne(gc.getReplyId());
                    User replyUser = userService.findOne(comment.getUserId());
                    GrandsonCommentVo grandsonCommentVo = new GrandsonCommentVo(gc.getId(), gc.getContent(), gc.getFloor(),
                            gc.getCreateTime(), use.getId(), use.getName(),
                            replyUser.getId(), replyUser.getName(), gc.getFatherId(), gc.getReplyId());
                    grandsonCommentVoList.add(grandsonCommentVo);
                }
                sonCommentVo.setGrandsonCommentVoList(grandsonCommentVoList);
                grandsonCommentVoList = new ArrayList<>();
                sonCommentVoList.add(sonCommentVo);
            }
            newCommentVoList.add(new CommentVo(commentVo.getId(), commentVo.getContent(), commentVo.getFloor(), commentVo.getCreateTime(),
                    u.getId(), u.getName(), u.getImgUrl(), sonCommentVoList));
            giftArticleDetailVo.setCommentVoList(newCommentVoList);
            sonCommentVoList = new ArrayList();
        }
        countService.incr(LocalConstant.GiftArticle, id, CountEnum.todayBrowsingCount.getValue());
        countService.incr(LocalConstant.GiftArticle, id, CountEnum.totalBrowsingCount.getValue());
        model.addAttribute("giftArticleDetailVo", giftArticleDetailVo);
        return "/detail";
    }


    /**
     * 去写文章页面
     * @return
     */
    @GetMapping("/write")
    public String write() {
        return "/write";
    }

}
