package com.example.liangge.indiana.model.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 中奖记录
 * Created by baoxing on 2015/12/25.
 */
public class BingoRecordEntity {

    /** 活动期号 */
    private long activityId;

    /** 产品图片 */
    private String productImgUrl;

    /** 标题 */
    private String title;

    /** 标题描述 */
    private String titleDesc;

    /** 所需人数 */
    public int needPeople;

    /** 剩余人数 */
    private int lackPeople;

    /** （用户）购买次数 */
    private int buyCnt;

    /** 幸运号码（开奖号码）*/
    private String luckyNumber;

    /** 开奖时间 */
    private long runLotteryTime;

    /** 最小购买数 */
    private int minimum_share;

    private static transient DateFormat mDateFormatAlreadyRunLottory = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public BingoRecordEntity() {
    }


    public BingoRecordEntity(long activityId, String productImgUrl, String title, String titleDesc, int needPeople, int lackPeople, int buyCnt, String luckyNumber, long runLotteryTime, int minimum_share) {
        this.activityId = activityId;
        this.productImgUrl = productImgUrl;
        this.title = title;
        this.titleDesc = titleDesc;
        this.needPeople = needPeople;
        this.lackPeople = lackPeople;
        this.buyCnt = buyCnt;
        this.luckyNumber = luckyNumber;
        this.runLotteryTime = runLotteryTime;
        this.minimum_share = minimum_share;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getBuyCnt() {
        return buyCnt;
    }

    public void setBuyCnt(int buyCnt) {
        this.buyCnt = buyCnt;
    }

    public String getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public long getRunLotteryTime() {
        return runLotteryTime;
    }

    public void setRunLotteryTime(long runLotteryTime) {
        this.runLotteryTime = runLotteryTime;
    }

    public int getMinimum_share() {
        return minimum_share;
    }

    public void setMinimum_share(int minimum_share) {
        this.minimum_share = minimum_share;
    }

    /**
     * 返回可读的已揭晓时间
     * @return
     */
    public String getHumanAlreadyRunLotteryTime() {
        return mDateFormatAlreadyRunLottory.format(new Date(getRunLotteryTime()));

    }


    @Override
    public String toString() {
        return "BingoRecordEntity{" +
                "activityId=" + activityId +
                ", productImgUrl='" + productImgUrl + '\'' +
                ", title='" + title + '\'' +
                ", titleDesc='" + titleDesc + '\'' +
                ", needPeople=" + needPeople +
                ", lackPeople=" + lackPeople +
                ", buyCnt=" + buyCnt +
                ", luckyNumber='" + luckyNumber + '\'' +
                ", runLotteryTime=" + runLotteryTime +
                ", minimum_share=" + minimum_share +
                '}';
    }
}
