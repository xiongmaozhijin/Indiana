package com.example.liangge.indiana.model.user;

/**
 * 登录返回实体
 * Created by baoxing on 2015/12/26.
 */
public class ResponseLogEntity {

    private long id;

    private int status;

    private String msg;

    private String token;

    /** 余额 */
    private int balance;

    /** 用户头像 */
    private String photo;

    private String nickname;


    public ResponseLogEntity() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ResponseLogEntity{" +
                "id=" + id +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", balance=" + balance +
                ", photo='" + photo + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
