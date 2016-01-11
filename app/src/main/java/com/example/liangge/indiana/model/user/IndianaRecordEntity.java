package com.example.liangge.indiana.model.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by baoxing on 2015/12/25.
 */
public class IndianaRecordEntity {

    /** 活动期号（id）*/
    private long activityId;

    /** 活动状态(进行中/揭晓中/已揭晓/计算中) */
    private int activityState;

    /** 产品图片url */
    private String productImgUrl;

    /** 标题描述 */
    private String titleDescribe;

    /** 参与期号 */
    private String joinActivityNum;

    /** 总需 */
    private int needPeople;

    /** 剩余 */
    private int lackPeople;

    /** 进度 */
    private int progress;

    /** 购买次数/参与次数 */
    private int buyCnt;

    /**  我的夺宝号码 */
    private String myIndianaNum;

    /** 获奖者 */
    private String bingoUser;

    /** 获奖者购买次数 */
    private int bingoBuyCnt;

    /** 幸运号码 */
    private String luckyNumber;

    /** 开奖时间 */
    private long runLotteryTime;

    /** 最小购买 */
    private int minimum_share = 1;

    private static transient DateFormat mDateFormatAlreadyRunLottory = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public IndianaRecordEntity() {
    }

    public IndianaRecordEntity(long activityId, int activityState, String productImgUrl, String titleDescribe, String joinActivityNum, int needPeople, int lackPeople, int progress, int buyCnt, String myIndianaNum, String bingoUser, int bingoBuyCnt, String luckyNumber, long runLotteryTime, int minimum_share) {
        this.activityId = activityId;
        this.activityState = activityState;
        this.productImgUrl = productImgUrl;
        this.titleDescribe = titleDescribe;
        this.joinActivityNum = joinActivityNum;
        this.needPeople = needPeople;
        this.lackPeople = lackPeople;
        this.progress = progress;
        this.buyCnt = buyCnt;
        this.myIndianaNum = myIndianaNum;
        this.bingoUser = bingoUser;
        this.bingoBuyCnt = bingoBuyCnt;
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

    public int getActivityState() {
        return activityState;
    }

    public void setActivityState(int activityState) {
        this.activityState = activityState;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getTitleDescribe() {
        return titleDescribe;
    }

    public void setTitleDescribe(String titleDescribe) {
        this.titleDescribe = titleDescribe;
    }

    public String getJoinActivityNum() {
        return joinActivityNum;
    }

    public void setJoinActivityNum(String joinActivityNum) {
        this.joinActivityNum = joinActivityNum;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getBuyCnt() {
        return buyCnt;
    }

    public void setBuyCnt(int buyCnt) {
        this.buyCnt = buyCnt;
    }

    public String getMyIndianaNum() {
        return myIndianaNum;
    }

    public void setMyIndianaNum(String myIndianaNum) {
        this.myIndianaNum = myIndianaNum;
    }

    public String getBingoUser() {
        return bingoUser;
    }

    public void setBingoUser(String bingoUser) {
        this.bingoUser = bingoUser;
    }

    public int getBingoBuyCnt() {
        return bingoBuyCnt;
    }

    public void setBingoBuyCnt(int bingoBuyCnt) {
        this.bingoBuyCnt = bingoBuyCnt;
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

    /**
     * 返回可读的进度提示
     * @return
     */
    public String getHumanReadableProcessHint() {
        return this.progress + "%";
    }


    @Override
    public String toString() {
        return "IndianaRecordEntity{" +
                "activityId=" + activityId +
                ", activityState=" + activityState +
                ", productImgUrl='" + productImgUrl + '\'' +
                ", titleDescribe='" + titleDescribe + '\'' +
                ", joinActivityNum='" + joinActivityNum + '\'' +
                ", needPeople=" + needPeople +
                ", lackPeople=" + lackPeople +
                ", progress=" + progress +
                ", buyCnt=" + buyCnt +
                ", myIndianaNum='" + myIndianaNum + '\'' +
                ", bingoUser='" + bingoUser + '\'' +
                ", bingoBuyCnt=" + bingoBuyCnt +
                ", luckyNumber='" + luckyNumber + '\'' +
                ", runLotteryTime=" + runLotteryTime +
                ", minimum_share=" + minimum_share +
                '}';
    }
}
