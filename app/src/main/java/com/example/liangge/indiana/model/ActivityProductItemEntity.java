package com.example.liangge.indiana.model;

import java.util.Random;

/**
 * 商品活动信息实体
 * Created by baoxing on 2015/12/15.
 */
public class ActivityProductItemEntity {

    /** 商品活动期次ID */
    private long activityId;

    /** 产品展示图片 */
    private String productImgUrl;

    /** 产品名字标题 */
    private String name;

    /** 清单标题描述 */
    private String titleDesc;

    /** 开奖进度 */
    private int bingoProgress;

    /** 开奖进度 */
    private String strBingoProgress;

    /** 所需人数 */
    private int needPeople;

    /** 剩余人数 */
    private int lackPeople;


    public ActivityProductItemEntity() {
    }

    public ActivityProductItemEntity(long activityId, String productImgUrl, String name, String titleDesc, int bingoProgress, String strBingoProgress, int needPeople, int lackPeople) {
        this.activityId = activityId;
        this.productImgUrl = productImgUrl;
        this.name = name;
        this.titleDesc = titleDesc;
        this.bingoProgress = bingoProgress;
        this.strBingoProgress = strBingoProgress;
        this.needPeople = needPeople;
        this.lackPeople = lackPeople;
    }

    /**
     * @deprecated
     * @param imgUrl
     * @param name
     * @param process
     * @param strProcess
     */
    public ActivityProductItemEntity(String imgUrl, String name, int process, String strProcess) {
        this( new Random().nextInt(10), imgUrl, name, "titleDesc1", process, strProcess, 1320, 567);

    }



    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBingoProgress() {
        return bingoProgress;
    }

    public void setBingoProgress(int bingoProgress) {
        this.bingoProgress = bingoProgress;
    }

    public String getStrBingoProgress() {
        return strBingoProgress;
    }

    public void setStrBingoProgress(String strBingoProgress) {
        this.strBingoProgress = strBingoProgress;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public int getNeedPeople() {
        return needPeople;
    }

    public void setNeedPeople(int needPeople) {
        this.needPeople = needPeople;
    }

    public int getLackPeople() {
        return lackPeople;
    }

    public void setLackPeople(int lackPeople) {
        this.lackPeople = lackPeople;
    }


    @Override
    public String toString() {
        return "ActivityProductItemEntity{" +
                "activityId=" + activityId +
                ", productImgUrl='" + productImgUrl + '\'' +
                ", name='" + name + '\'' +
                ", titleDesc='" + titleDesc + '\'' +
                ", bingoProgress=" + bingoProgress +
                ", strBingoProgress='" + strBingoProgress + '\'' +
                ", needPeople=" + needPeople +
                ", lackPeople=" + lackPeople +
                '}';
    }






}
