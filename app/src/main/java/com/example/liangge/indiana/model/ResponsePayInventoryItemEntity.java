package com.example.liangge.indiana.model;

/**
 * Created by baoing on 2015/12/26.
 */
public class ResponsePayInventoryItemEntity {

    /** 期次 */
    private long issue_id;

    /** 请求购买数量 */
    private int amount;

    /** 成功购买数量 */
    private int amount_bought;

    private String titleDesc;

    private String productImgUrl;


    public ResponsePayInventoryItemEntity() {
    }

    public long getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(long issue_id) {
        this.issue_id = issue_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_bought() {
        return amount_bought;
    }

    public void setAmount_bought(int amount_bought) {
        this.amount_bought = amount_bought;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    @Override
    public String toString() {
        return "ResponsePayInventoryItemEntity{" +
                "issue_id=" + issue_id +
                ", amount=" + amount +
                ", amount_bought=" + amount_bought +
                ", titleDesc='" + titleDesc + '\'' +
                ", productImgUrl='" + productImgUrl + '\'' +
                '}';
    }



}
