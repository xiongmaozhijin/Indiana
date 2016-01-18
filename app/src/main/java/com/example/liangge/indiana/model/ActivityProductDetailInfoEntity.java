package com.example.liangge.indiana.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 该期活动详细情况
 * Created by baoxing on 2015/12/23.
 */
public class ActivityProductDetailInfoEntity {

    /** 活动期次id */
    private long activityId;

    /** 商品ID */
    private long commodity_id;

    /** 商品轮播图片 */
    private String[] productImgUrls;

    /** 活动状态(进行中/揭晓中/已揭晓/计算中) */
    private int activityState;

    /** 标题描述 */
    private String titleDescribe;

    /** 期号 */
    private long activityNum;

    /** 总需人数 */
    private int needPeople;

    /** 剩余人数 */
    private int lackPeople;

    /** 是否参与 */
    private boolean isPlay;

    /** 我的夺宝号码 */
    private String myIndianaNum;

    /** 用户参与夺宝的次数 */
    private int myIndianaAmount;

    /** 图文详情 */
    private String[] productDetailImgs;

    /** 开奖时间 */
    private long runLotteryTime;

    /** 中奖用户头像 */
    private String bingoUserPortain;

    /** 中奖用户的用户名 */
    private String bingoUserName;

    /** 中奖用户地址 */
    private String bingoUserAddress;

    /** 中奖用户参与次数 */
    private int bingoBuyCnts;

    /** 幸运号码/中奖号码 */
    private String luckyNumber;

    /** 最少购买 */
    private int minimum_share = 1;

    /** 图文链接 */
    private String productDetailLink;

    /** 我的夺宝号码 */
    private String myIndianaNumberUrl;

    /** 获奖者的用户ID */
    private long bingoUserID;

    private static transient DateFormat mDateFormatAlreadyRunLottory = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public ActivityProductDetailInfoEntity() {
    }


    public ActivityProductDetailInfoEntity(long activityId, long commodity_id, String[] productImgUrls, int activityState, String titleDescribe, long activityNum, int needPeople, int lackPeople, boolean isPlay, String myIndianaNum, int myIndianaAmount, String[] productDetailImgs, long runLotteryTime, String bingoUserPortain, String bingoUserName, String bingoUserAddress, int bingoBuyCnts, String luckyNumber, int minimum_share, String productDetailLink, String myIndianaNumberUrl, long bingoUserID) {
        this.activityId = activityId;
        this.commodity_id = commodity_id;
        this.productImgUrls = productImgUrls;
        this.activityState = activityState;
        this.titleDescribe = titleDescribe;
        this.activityNum = activityNum;
        this.needPeople = needPeople;
        this.lackPeople = lackPeople;
        this.isPlay = isPlay;
        this.myIndianaNum = myIndianaNum;
        this.myIndianaAmount = myIndianaAmount;
        this.productDetailImgs = productDetailImgs;
        this.runLotteryTime = runLotteryTime;
        this.bingoUserPortain = bingoUserPortain;
        this.bingoUserName = bingoUserName;
        this.bingoUserAddress = bingoUserAddress;
        this.bingoBuyCnts = bingoBuyCnts;
        this.luckyNumber = luckyNumber;
        this.minimum_share = minimum_share;
        this.productDetailLink = productDetailLink;
        this.myIndianaNumberUrl = myIndianaNumberUrl;
        this.bingoUserID = bingoUserID;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(long commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String[] getProductImgUrls() {
        return productImgUrls;
    }

    public void setProductImgUrls(String[] productImgUrls) {
        this.productImgUrls = productImgUrls;
    }

    public int getActivityState() {
        return activityState;
    }

    public void setActivityState(int activityState) {
        this.activityState = activityState;
    }

    public String getTitleDescribe() {
        return titleDescribe;
    }

    public void setTitleDescribe(String titleDescribe) {
        this.titleDescribe = titleDescribe;
    }

    public long getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(long activityNum) {
        this.activityNum = activityNum;
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

    public boolean isPlay() {
        return isPlay;
    }

    public void setIsPlay(boolean isPlay) {
        this.isPlay = isPlay;
    }

    public String getMyIndianaNum() {
        return myIndianaNum;
    }

    public void setMyIndianaNum(String myIndianaNum) {
        this.myIndianaNum = myIndianaNum;
    }

    public String[] getProductDetailImgs() {
        return productDetailImgs;
    }

    public void setProductDetailImgs(String[] productDetailImgs) {
        this.productDetailImgs = productDetailImgs;
    }

    public long getRunLotteryTime() {
        return runLotteryTime;
    }

    public void setRunLotteryTime(long runLotteryTime) {
        this.runLotteryTime = runLotteryTime;
    }

    public String getBingoUserPortain() {
        return bingoUserPortain;
    }

    public void setBingoUserPortain(String bingoUserPortain) {
        this.bingoUserPortain = bingoUserPortain;
    }

    public String getBingoUserName() {
        return bingoUserName;
    }

    public void setBingoUserName(String bingoUserName) {
        this.bingoUserName = bingoUserName;
    }

    public String getBingoUserAddress() {
        return bingoUserAddress;
    }

    public void setBingoUserAddress(String bingoUserAddress) {
        this.bingoUserAddress = bingoUserAddress;
    }

    public int getBingoBuyCnts() {
        return bingoBuyCnts;
    }

    public void setBingoBuyCnts(int bingoBuyCnts) {
        this.bingoBuyCnts = bingoBuyCnts;
    }

    public String getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }


    public int getMyIndianaAmount() {
        return myIndianaAmount;
    }

    public void setMyIndianaAmount(int myIndianaAmount) {
        this.myIndianaAmount = myIndianaAmount;
    }


    public int getMinimum_share() {
        return minimum_share;
    }

    public void setMinimum_share(int minimum_share) {
        this.minimum_share = minimum_share;
    }

    public String getProductDetailLink() {
        return productDetailLink;
    }

    public void setProductDetailLink(String productDetailLink) {
        this.productDetailLink = productDetailLink;
    }

    public String getMyIndianaNumberUrl() {
        return myIndianaNumberUrl;
    }

    public void setMyIndianaNumberUrl(String myIndianaNumberUrl) {
        this.myIndianaNumberUrl = myIndianaNumberUrl;
    }

    public long getBingoUserID() {
        return bingoUserID;
    }

    public void setBingoUserID(long bingoUserID) {
        this.bingoUserID = bingoUserID;
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
        return "ActivityProductDetailInfoEntity{" +
                "activityId=" + activityId +
                ", commodity_id=" + commodity_id +
                ", productImgUrls=" + Arrays.toString(productImgUrls) +
                ", activityState=" + activityState +
                ", titleDescribe='" + titleDescribe + '\'' +
                ", activityNum=" + activityNum +
                ", needPeople=" + needPeople +
                ", lackPeople=" + lackPeople +
                ", isPlay=" + isPlay +
                ", myIndianaNum='" + myIndianaNum + '\'' +
                ", myIndianaAmount=" + myIndianaAmount +
                ", productDetailImgs=" + Arrays.toString(productDetailImgs) +
                ", runLotteryTime=" + runLotteryTime +
                ", bingoUserPortain='" + bingoUserPortain + '\'' +
                ", bingoUserName='" + bingoUserName + '\'' +
                ", bingoUserAddress='" + bingoUserAddress + '\'' +
                ", bingoBuyCnts=" + bingoBuyCnts +
                ", luckyNumber='" + luckyNumber + '\'' +
                ", minimum_share=" + minimum_share +
                ", productDetailLink='" + productDetailLink + '\'' +
                ", myIndianaNumberUrl='" + myIndianaNumberUrl + '\'' +
                ", bingoUserID=" + bingoUserID +
                '}';
    }
}
