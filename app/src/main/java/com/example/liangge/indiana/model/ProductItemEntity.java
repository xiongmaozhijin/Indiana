package com.example.liangge.indiana.model;

/**
 * 产品信息实体
 * Created by baoxing on 2015/12/15.
 */
public class ProductItemEntity {

    /** 产品展示图片 */
    private String productImgUrl;

    /** 产品名字 */
    private String name;

    /** 开奖进度 */
    private int bingoProgress;

    /** 开奖进度 */
    private String strBingoProgress;


    public ProductItemEntity(String productImgUrl, String name, int bingoProgress, String strBingoProgress) {
        this.productImgUrl = productImgUrl;
        this.name = name;
        this.bingoProgress = bingoProgress;
        this.strBingoProgress = strBingoProgress;
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

    @Override
    public String toString() {
        return "ProductItemEntity{" +
                "productImgUrl='" + productImgUrl + '\'' +
                ", name='" + name + '\'' +
                ", bingoProgress=" + bingoProgress +
                ", strBingoProgress='" + strBingoProgress + '\'' +
                '}';
    }
}
