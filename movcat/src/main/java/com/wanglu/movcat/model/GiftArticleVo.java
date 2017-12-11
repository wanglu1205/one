package com.wanglu.movcat.model;

public class GiftArticleVo {

    private Integer giftArticleId;

    private String giftArticleImgUrl;

    private String giftArticleTitle;

    private Integer commentCount;

    private Integer praiseCount;

    private Integer shareCount;

    private Integer todayBrowsingCount;

    private Integer totalBrowsingCount;

    private Integer userId;

    private String userName;

    private String userImgUrl;

    private String commentContent;

    public GiftArticleVo() {
    }

    public GiftArticleVo(Integer giftArticleId, String giftArticleImgUrl, String giftArticleTitle, Integer commentCount, Integer praiseCount, Integer shareCount, Integer todayBrowsingCount, Integer totalBrowsingCount) {
        this.giftArticleId = giftArticleId;
        this.giftArticleImgUrl = giftArticleImgUrl;
        this.giftArticleTitle = giftArticleTitle;
        this.commentCount = commentCount;
        this.praiseCount = praiseCount;
        this.shareCount = shareCount;
        this.todayBrowsingCount = todayBrowsingCount;
        this.totalBrowsingCount = totalBrowsingCount;
    }

    public GiftArticleVo(Integer giftArticleId, String giftArticleImgUrl, String giftArticleTitle, Integer commentCount, Integer praiseCount, Integer shareCount,  Integer todayBrowsingCount, Integer totalBrowsingCount, Integer userId, String userName, String userImgUrl, String commentContent) {
        this.giftArticleId = giftArticleId;
        this.giftArticleImgUrl = giftArticleImgUrl;
        this.giftArticleTitle = giftArticleTitle;
        this.commentCount = commentCount;
        this.praiseCount = praiseCount;
        this.shareCount = shareCount;
        this.todayBrowsingCount = todayBrowsingCount;
        this.totalBrowsingCount = totalBrowsingCount;
        this.userId = userId;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.commentContent = commentContent;
    }

    public Integer getGiftArticleId() {
        return giftArticleId;
    }

    public void setGiftArticleId(Integer giftArticleId) {
        this.giftArticleId = giftArticleId;
    }

    public String getGiftArticleImgUrl() {
        return giftArticleImgUrl;
    }

    public void setGiftArticleImgUrl(String giftArticleImgUrl) {
        this.giftArticleImgUrl = giftArticleImgUrl;
    }

    public String getGiftArticleTitle() {
        return giftArticleTitle;
    }

    public void setGiftArticleTitle(String giftArticleTitle) {
        this.giftArticleTitle = giftArticleTitle;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getTodayBrowsingCount() {
        return todayBrowsingCount;
    }

    public void setTodayBrowsingCount(Integer todayBrowsingCount) {
        this.todayBrowsingCount = todayBrowsingCount;
    }

    public Integer getTotalBrowsingCount() {
        return totalBrowsingCount;
    }

    public void setTotalBrowsingCount(Integer totalBrowsingCount) {
        this.totalBrowsingCount = totalBrowsingCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Override
    public String toString() {
        return "GiftArticleVo{" +
                "giftArticleId=" + giftArticleId +
                ", giftArticleImgUrl='" + giftArticleImgUrl + '\'' +
                ", giftArticleTitle='" + giftArticleTitle + '\'' +
                ", commentCount=" + commentCount +
                ", praiseCount=" + praiseCount +
                ", shareCount=" + shareCount +
                ", todayBrowsingCount=" + todayBrowsingCount +
                ", totalBrowsingCount=" + totalBrowsingCount +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userImgUrl='" + userImgUrl + '\'' +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }
}
