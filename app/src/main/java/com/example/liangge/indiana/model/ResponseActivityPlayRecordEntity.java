package com.example.liangge.indiana.model;

/**
 * 返回的某期特定的参与记录实体
 * Created by baoxing on 2015/12/28.
 */
public class ResponseActivityPlayRecordEntity {

    /** 参与份额 */
    private String own_share;

    /** 参与时间 */
    private String record_time;

    private String ip;

    private String city;

    private String nickname;

    private String photo;

    private long userID;

    public ResponseActivityPlayRecordEntity() {
    }

    public ResponseActivityPlayRecordEntity(String nickname, String photo, long userID) {
        this.nickname = nickname;
        this.photo = photo;
        this.userID = userID;
    }

    public String getOwn_share() {
        return own_share;
    }

    public void setOwn_share(String own_share) {
        this.own_share = own_share;
    }

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "ResponseActivityPlayRecordEntity{" +
                "own_share='" + own_share + '\'' +
                ", record_time='" + record_time + '\'' +
                ", ip='" + ip + '\'' +
                ", city='" + city + '\'' +
                ", nickname='" + nickname + '\'' +
                ", photo='" + photo + '\'' +
                ", userID=" + userID +
                '}';
    }
}
