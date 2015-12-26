package com.example.liangge.indiana.model;

import java.util.List;

/**
 * 清单支付返回实体
 * Created by baoxing on 2015/12/26.
 */
public class ResponsePayInventoryEntity {

    /** 返回状态码 */
    private int status;

    /** 状态码信息 */
    private String msg;

    private List<ResponsePayInventoryItemEntity> shopList;


    public ResponsePayInventoryEntity() {
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResponsePayInventoryItemEntity> getShopList() {
        return shopList;
    }

    public void setShopList(List<ResponsePayInventoryItemEntity> shopList) {
        this.shopList = shopList;
    }


    @Override
    public String toString() {
        return "ResponsePayInventoryEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", shopList=" + shopList +
                '}';
    }



}

