package com.example.liangge.indiana.model;

/**
 * 清单实体 <br/>
 * Created by baoxing on 2015/12/17.
 */
public class InventoryEntity {

    /** 产品ID */
    private int productId;

    /** 清单图片地址 */
    private String invertoryImgUrl;

    /** 清单标题描述 */
    private String titleDescribe;

    /** 总需人数 */
    private int needPeopleCounts;

    /** 剩余人数 */
    private int lackPeopleCounts;

    /** 购买次数 */
    private int buyCounts;


    public int getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(int buyCounts) {
        this.buyCounts = buyCounts;
    }


    public String getInvertoryImgUrl() {
        return invertoryImgUrl;
    }

    public void setInvertoryImgUrl(String invertoryImgUrl) {
        this.invertoryImgUrl = invertoryImgUrl;
    }

    public String getTitleDescribe() {
        return titleDescribe;
    }

    public void setTitleDescribe(String titleDescribe) {
        this.titleDescribe = titleDescribe;
    }

    public int getNeedPeopleCounts() {
        return needPeopleCounts;
    }

    public void setNeedPeopleCounts(int needPeopleCounts) {
        this.needPeopleCounts = needPeopleCounts;
    }

    public int getLackPeopleCounts() {
        return lackPeopleCounts;
    }

    public void setLackPeopleCounts(int lackPeopleCounts) {
        this.lackPeopleCounts = lackPeopleCounts;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "InventoryEntity{" +
                "productId=" + productId +
                ", invertoryImgUrl='" + invertoryImgUrl + '\'' +
                ", titleDescribe='" + titleDescribe + '\'' +
                ", needPeopleCounts=" + needPeopleCounts +
                ", lackPeopleCounts=" + lackPeopleCounts +
                ", buyCounts=" + buyCounts +
                '}';
    }



}
