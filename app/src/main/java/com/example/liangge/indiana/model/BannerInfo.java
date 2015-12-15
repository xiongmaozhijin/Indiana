package com.example.liangge.indiana.model;

/**
 * 图片墙数据实体
 * Created by baoxing on 2015/12/14.
 */
public class BannerInfo {

    /** 图片地址 */
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    @Override
    public String toString() {
        return "BannerInfo{" +
                "imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
