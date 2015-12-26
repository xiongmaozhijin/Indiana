package com.example.liangge.indiana.model;

/**
 * 图片墙数据实体
 * Created by baoxing on 2015/12/14.
 */
public class BannerInfo {

    /** 图片地址 */
    private String imgUrl;

    /** 链接url */
    private String linkUrl;

    /** 标题 */
    private String title;

    /** 搜索关键字 */
    private String keyword;

    /** 奖品活动期数 */
    private long activityId;

    /** 执行操作，打开网页/搜索/商品详情 */
    private int action;


    public BannerInfo() {
    }

    public BannerInfo(String imgUrl, String linkUrl, String title, String keyword, long activityId, int action) {
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
        this.title = title;
        this.keyword = keyword;
        this.activityId = activityId;
        this.action = action;
    }

    public BannerInfo(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }


    @Override
    public String toString() {
        return "BannerInfo{" +
                "imgUrl='" + imgUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", title='" + title + '\'' +
                ", keyword='" + keyword + '\'' +
                ", activityId=" + activityId +
                ", action=" + action +
                '}';
    }



}
