package com.example.liangge.indiana.model;

/**
 * 返回的用户信息实体
 * Created by baoing on 2015/12/28.
 */
public class ResponseUserInfoEntitiy {

    /** 返回的状态码 */
    private int status;

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


    @Override
    public String toString() {
        return "ResponseUserInfoEntitiy{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", balance=" + balance +
                '}';
    }




}
