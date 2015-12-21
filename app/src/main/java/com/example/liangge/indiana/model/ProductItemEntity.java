package com.example.liangge.indiana.model;

import java.util.Random;

/**
 * 产品信息实体
 * Created by baoxing on 2015/12/15.
 */
public class ProductItemEntity {

    /** 产品ID */
    private long productId;

    /** 产品展示图片 */
    private String productImgUrl;

    /** 产品名字 */
    private String name;

    /** 清单标题描述 */
    private String tilteDesc;

    /** 开奖进度 */
    private int bingoProgress;

    /** 开奖进度 */
    private String strBingoProgress;

    /** 所需人数 */
    private int needPeople;

    /** 剩余人数 */
    private int lackPeople;

    public ProductItemEntity(long productId, String productImgUrl, String name, String tilteDesc, int bingoProgress, String strBingoProgress, int needPeople, int lackPeople) {
        this.productId = productId;
        this.productImgUrl = productImgUrl;
        this.name = name;
        this.tilteDesc = tilteDesc;
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
    public ProductItemEntity(String imgUrl, String name, int process, String strProcess) {
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getTilteDesc() {
        return tilteDesc;
    }

    public void setTilteDesc(String tilteDesc) {
        this.tilteDesc = tilteDesc;
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
        return "ProductItemEntity{" +
                "productId=" + productId +
                ", productImgUrl='" + productImgUrl + '\'' +
                ", name='" + name + '\'' +
                ", tilteDesc='" + tilteDesc + '\'' +
                ", bingoProgress=" + bingoProgress +
                ", strBingoProgress='" + strBingoProgress + '\'' +
                ", needPeople=" + needPeople +
                ", lackPeople=" + lackPeople +
                '}';
    }






}
