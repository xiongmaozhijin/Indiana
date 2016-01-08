package com.example.liangge.indiana.model;

import com.example.liangge.indiana.model.user.UserInfoEntity;

/**
 * 返回的用户信息实体
 * Created by baoing on 2015/12/28.
 */
public class ResponseUserInfoEntitiy {

    private int status;
    private String msg;
    private UserInfoEntity data;

    public ResponseUserInfoEntitiy() {
    }

    public ResponseUserInfoEntitiy(int status, String msg, UserInfoEntity data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
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

    public UserInfoEntity getData() {
        return data;
    }

    public void setData(UserInfoEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseUserInfoEntitiy{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
