package com.example.liangge.indiana.model;

import android.content.Context;

import com.example.liangge.indiana.R;

/**
 * 清单实体 <br/>
 * Created by baoxing on 2015/12/17.
 */
public class InventoryEntity {

    /** 产品ID */
    private long productId;

    /** 清单图片地址 */
    private String invertoryImgUrl;

    /** 标题名字 */
    private String titleName;

    /** 清单标题描述 */
    private String titleDescribe;

    /** 总需人数 */
    private int needPeopleCounts;

    /** 剩余人数 */
    private int lackPeopleCounts;

    /** 购买次数 */
    private int buyCounts;


    public InventoryEntity(long productId, String invertoryImgUrl, String titleName, String titleDescribe, int needPeopleCounts, int lackPeopleCounts, int buyCounts) {
        this.productId = productId;
        this.invertoryImgUrl = invertoryImgUrl;
        this.titleName = titleName;
        this.titleDescribe = titleDescribe;
        this.needPeopleCounts = needPeopleCounts;
        this.lackPeopleCounts = lackPeopleCounts;
        this.buyCounts = buyCounts;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

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


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    /**
     * 返回参与描述
     * @return
     */
    public String getJoinDescribe(Context context) {
        String strJoninDescribeT = context.getResources().getString(R.string.f_shoppingcart_join_describe);
        String strJoinDescribe = String.format(strJoninDescribeT, getNeedPeopleCounts(), getLackPeopleCounts());

        return strJoinDescribe;
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
