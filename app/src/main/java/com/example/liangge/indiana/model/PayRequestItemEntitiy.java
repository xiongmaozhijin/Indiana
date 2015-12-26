package com.example.liangge.indiana.model;

/**
 * 订单请求实体，单项
 * Created by baoxing on 2015/12/26.
 */
public class PayRequestItemEntitiy {

    /** 期号 */
    private long issue_id;

    /** 购买数量 */
    private int amount;

    public PayRequestItemEntitiy() {
    }

    public PayRequestItemEntitiy(long issue_id, int amount) {
        this.issue_id = issue_id;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "PayRequestItemEntitiy{" +
                "issue_id=" + issue_id +
                ", amount=" + amount +
                '}';
    }
}
