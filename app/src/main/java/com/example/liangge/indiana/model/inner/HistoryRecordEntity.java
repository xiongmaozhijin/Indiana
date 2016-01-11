package com.example.liangge.indiana.model.inner;

/**
 * 往期揭晓
 * Created by baoxing on 2016/1/11.
 */
public class HistoryRecordEntity {

    private long issue_id;
    private String out_time;
    private String winner;
    private String ip;
    private String city;
    private long account_id;
    private String luckyNumber;
    private int own_share;
    private String photo;

    public HistoryRecordEntity() {
    }

    public HistoryRecordEntity(long issue_id, String out_time, String winner, String ip, String city, long account_id, String luckyNumber, int own_share, String photo) {
        this.issue_id = issue_id;
        this.out_time = out_time;
        this.winner = winner;
        this.ip = ip;
        this.city = city;
        this.account_id = account_id;
        this.luckyNumber = luckyNumber;
        this.own_share = own_share;
        this.photo = photo;
    }

    public long getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(long issue_id) {
        this.issue_id = issue_id;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public int getOwn_share() {
        return own_share;
    }

    public void setOwn_share(int own_share) {
        this.own_share = own_share;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    @Override
    public String toString() {
        return "HistoryRecordEntity{" +
                "issue_id=" + issue_id +
                ", out_time='" + out_time + '\'' +
                ", winner='" + winner + '\'' +
                ", ip='" + ip + '\'' +
                ", city='" + city + '\'' +
                ", account_id=" + account_id +
                ", luckyNumber='" + luckyNumber + '\'' +
                ", own_share=" + own_share +
                ", photo='" + photo + '\'' +
                '}';
    }
}
