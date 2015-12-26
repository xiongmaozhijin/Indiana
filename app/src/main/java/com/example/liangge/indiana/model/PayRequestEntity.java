package com.example.liangge.indiana.model;

import java.util.List;

/**
 * 订单支付请求实体
 * Created by baoxing on 2015/12/26.
 */
public class PayRequestEntity {

    private long id;

    private String token;

    private List<PayRequestItemEntitiy> payList;

    public PayRequestEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<PayRequestItemEntitiy> getPayList() {
        return payList;
    }

    public void setPayList(List<PayRequestItemEntitiy> payList) {
        this.payList = payList;
    }


    @Override
    public String toString() {
        return "PayRequestEntity{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", payList=" + payList +
                '}';
    }
}
