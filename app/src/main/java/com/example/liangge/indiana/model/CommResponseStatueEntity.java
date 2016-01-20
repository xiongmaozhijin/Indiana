package com.example.liangge.indiana.model;

/**
 * Created by baoxing on 2016/1/20.
 */
public class CommResponseStatueEntity {

    private int status;
    private String msg;

    public CommResponseStatueEntity() {
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
        return "CommResponseStatueEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
