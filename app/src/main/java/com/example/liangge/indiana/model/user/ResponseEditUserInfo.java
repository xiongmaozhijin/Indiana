package com.example.liangge.indiana.model.user;

/**
 * Created by baoxing on 2016/1/11.
 */
public class ResponseEditUserInfo {

    private int status;
    private String success;

    public ResponseEditUserInfo() {
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


    @Override
    public String toString() {
        return "ResponseEditUserInfo{" +
                "status=" + status +
                ", success='" + success + '\'' +
                '}';
    }



}
