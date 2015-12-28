package com.example.liangge.indiana.model.user;

/**
 * 用户信息实体
 * Created by baoxing on 2015/12/26.
 */
public class UserInfoEntity {

    private long userId;

    private String nickname;

    private String token;

    private int balance;

    private String photo;


    public UserInfoEntity() {
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", token='" + token + '\'' +
                ", balance=" + balance +
                ", photo='" + photo + '\'' +
                '}';
    }


}
