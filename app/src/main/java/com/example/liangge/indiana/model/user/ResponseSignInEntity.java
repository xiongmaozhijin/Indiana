package com.example.liangge.indiana.model.user;

/**
 * 注册响应返回实体
 * Created by baoxing on 2015/12/26.
 */
public class ResponseSignInEntity {

    private int status;

    private String msg;

    public ResponseSignInEntity() {
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


    @Override
    public String toString() {
        return "ResponseSignInEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }


}
