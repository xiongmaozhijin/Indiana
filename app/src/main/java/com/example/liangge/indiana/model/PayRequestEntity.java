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

    private long time;

    private String token2;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    @Override
    public String toString() {
        return "PayRequestEntity{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", payList=" + payList +
                ", time=" + time +
                ", token2='" + token2 + '\'' +
                '}';
    }
}
