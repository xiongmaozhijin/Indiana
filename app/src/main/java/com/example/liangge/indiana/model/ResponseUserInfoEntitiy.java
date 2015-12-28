package com.example.liangge.indiana.model;

/**
 * 返回的用户信息实体
 * Created by baoing on 2015/12/28.
 */
public class ResponseUserInfoEntitiy {

    /** 返回的状态码 */
    private int status;

    private String nickname;

    private String msg;

    private int balance;

    public ResponseUserInfoEntitiy() {
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ResponseUserInfoEntitiy{" +
                "status=" + status +
                ", nickname='" + nickname + '\'' +
                ", msg='" + msg + '\'' +
                ", balance=" + balance +
                '}';
    }
}
