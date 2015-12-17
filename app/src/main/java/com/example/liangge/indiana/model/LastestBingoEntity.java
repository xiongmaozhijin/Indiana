package com.example.liangge.indiana.model;

import android.content.Context;

import com.example.liangge.indiana.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 最新揭晓实体
 * Created by baoxing on 2015/12/16.
 */
public class LastestBingoEntity {

    /** 产品图片url */
    private String productUrl;

    /** 标题描述 */
    private String titleDescribe;

    /** 中奖用户 */
    private String bingoUser;

    /** 幸运号码 */
    private String lunckyNumeber;

    /** 参与次数 */
    private int buyTimes;

    /** 开奖(揭晓)时间 */
    private long runLotteryTime;

    private static DateFormat mDateFormatAlreadyRunLottory = new SimpleDateFormat("yyyy-MM-dd");


    public LastestBingoEntity(String productUrl, String titleDescribe, String bingoUser, String lunckyNumeber, int buyTimes, long runLotteryTime) {
        this.productUrl = productUrl;
        this.titleDescribe = titleDescribe;
        this.bingoUser = bingoUser;
        this.lunckyNumeber = lunckyNumeber;
        this.buyTimes = buyTimes;
        this.runLotteryTime = runLotteryTime;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getTitleDescribe() {
        return titleDescribe;
    }

    public void setTitleDescribe(String titleDescribe) {
        this.titleDescribe = titleDescribe;
    }

    public String getBingoUser() {
        return bingoUser;
    }

    public void setBingoUser(String bingoUser) {
        this.bingoUser = bingoUser;
    }

    public String getLunckyNumeber() {
        return lunckyNumeber;
    }

    public void setLunckyNumeber(String lunckyNumeber) {
        this.lunckyNumeber = lunckyNumeber;
    }

    /**
     * @deprecated
     * @return
     */
    public int getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(int buyTimes) {
        this.buyTimes = buyTimes;
    }


    public String getHumanBuyTimes() {
        return getBuyTimes() + "";
    }

    /**
     * 返回开奖(揭晓)时间
     * @return
     */
    public long getRunLotteryTime() {
        return runLotteryTime;
    }

    public void setRunLotteryTime(long runLotteryTime) {
        this.runLotteryTime = runLotteryTime;
    }

    /**
     * 是否已经揭晓
     * @return
     */
    public boolean isAlreadyRunLottory() {
        return ( System.currentTimeMillis() - getRunLotteryTime() ) > 0 ? true : false;

    }

    /**
     * 返回可读的已揭晓时间
     * @return
     */
    public String getHumanAlreadyRunLotteryTime() {
        return mDateFormatAlreadyRunLottory.format(new Date(getRunLotteryTime()));

    }

    /**
     * 返回人类可读的产品描述，title显示
     * @return
     */
    public String getHumanProductDescribe() {
        String str = getTitleDescribe();

        if (str.length() > 18) {
            str = str.substring(0, 18);
            str += "...";
        }

        return str;
    }


    @Override
    public String toString() {
        return "LastestBingoEntity{" +
                "productUrl='" + productUrl + '\'' +
                ", titleDescribe='" + titleDescribe + '\'' +
                ", bingoUser='" + bingoUser + '\'' +
                ", lunckyNumeber='" + lunckyNumeber + '\'' +
                ", buyTimes=" + buyTimes +
                ", runLotteryTime=" + runLotteryTime +
                '}';
    }
}
