package com.example.liangge.indiana.model;

import com.example.liangge.indiana.model.user.BingoRecordEntity;

import java.util.List;

/**
 * 晒单实体
 * Created by baoxing on 2016/1/20.
 */
public class ShareOrdersEntity {

    private String userPortraitUrl;
    private String username;
    private long userID;

    /** 晒单时间 */
    private String shareDate;
    private String shareTitle;
    private String shareContent;
    private List<String> shareImgs;

    private BingoRecordEntity bingoRecordEntity;


    public ShareOrdersEntity() {
    }

    public ShareOrdersEntity(String userPortraitUrl, String username, long userID, String shareDate, String shareTitle, String shareContent, List<String> shareImgs, BingoRecordEntity bingoRecordEntity) {
        this.userPortraitUrl = userPortraitUrl;
        this.username = username;
        this.userID = userID;
        this.shareDate = shareDate;
        this.shareTitle = shareTitle;
        this.shareContent = shareContent;
        this.shareImgs = shareImgs;
        this.bingoRecordEntity = bingoRecordEntity;
    }

    public String getUserPortraitUrl() {
        return userPortraitUrl;
    }

    public void setUserPortraitUrl(String userPortraitUrl) {
        this.userPortraitUrl = userPortraitUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getShareDate() {
        return shareDate;
    }

    public void setShareDate(String shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public List<String> getShareImgs() {
        return shareImgs;
    }

    public void setShareImgs(List<String> shareImgs) {
        this.shareImgs = shareImgs;
    }

    public BingoRecordEntity getBingoRecordEntity() {
        return bingoRecordEntity;
    }

    public void setBingoRecordEntity(BingoRecordEntity bingoRecordEntity) {
        this.bingoRecordEntity = bingoRecordEntity;
    }

    @Override
    public String toString() {
        return "ShareOrdersEntity{" +
                "userPortraitUrl='" + userPortraitUrl + '\'' +
                ", username='" + username + '\'' +
                ", userID=" + userID +
                ", shareDate='" + shareDate + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                ", shareContent='" + shareContent + '\'' +
                ", shareImgs=" + shareImgs +
                ", bingoRecordEntity=" + bingoRecordEntity +
                '}';
    }
}
