package com.example.liangge.indiana.model.inner;

/**
 * App版本实体
 * Created by baoxing on 2016/1/18.
 */
public class AppVersionInfo {

    private String app_name;
    private String version_name;
    private int version_code;
    private String app_url;
    private String update_msg;

    public AppVersionInfo() {
    }

    public AppVersionInfo(String app_name, String version_name, int version_code, String app_url, String update_msg) {
        this.app_name = app_name;
        this.version_name = version_name;
        this.version_code = version_code;
        this.app_url = app_url;
        this.update_msg = update_msg;
    }


    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getUpdate_msg() {
        return update_msg;
    }

    public void setUpdate_msg(String update_msg) {
        this.update_msg = update_msg;
    }

    @Override
    public String toString() {
        return "AppVersionInfo{" +
                "app_name='" + app_name + '\'' +
                ", version_name='" + version_name + '\'' +
                ", version_code=" + version_code +
                ", app_url='" + app_url + '\'' +
                ", update_msg='" + update_msg + '\'' +
                '}';
    }
}
