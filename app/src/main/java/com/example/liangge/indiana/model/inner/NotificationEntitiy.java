package com.example.liangge.indiana.model.inner;

/**
 * 夺宝页请求返回的消息通知实体
 * Created by baoxing on 2016/1/13.
 */
public class NotificationEntitiy {

    private long issue_id;
    private String account_name;
    private String commodity_name;

    public NotificationEntitiy() {
    }

    public long getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(long issue_id) {
        this.issue_id = issue_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    @Override
    public String toString() {
        return "NotificationEntitiy{" +
                "issue_id=" + issue_id +
                ", account_name='" + account_name + '\'' +
                ", commodity_name='" + commodity_name + '\'' +
                '}';
    }

}
