package com.example.liangge.indiana.model;

import com.example.liangge.indiana.comm.NetworkUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传晒单的实体
 * Created by baoxing on 2016/1/19.
 */
public class PostShareOrderEntity {

    private long userID;
    private String token;
    private String title;
    private String content;
    private List<String> shareImgBase64;

    private long activityID;
    private String token2;
    private long time;

    public PostShareOrderEntity() {
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getShareImgBase64() {
        return shareImgBase64;
    }

    public void setShareImgBase64(List<String> shareImgBase64) {
        this.shareImgBase64 = shareImgBase64;
    }

    public long getActivityID() {
        return activityID;
    }

    public void setActivityID(long activityID) {
        this.activityID = activityID;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, String> getPostMapEntity() {
        Map<String, String> map = new HashMap<>();
        long time1 = System.currentTimeMillis();
        time = time1;
        map.put("userID", userID+"");
        map.put("token", token);
        map.put("title", title);
        map.put("content", content);
        map.put("activityID", activityID+"");
        map.put("token2", NetworkUtils.getToken2(time1, userID, token));
        map.put("time", time1+"");
        return map;
    }


    @Override
    public String toString() {
        return "PostShareOrderEntity{" +
                "userID=" + userID +
                ", token='" + token + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", shareImgBase64=" + shareImgBase64 +
                ", activityID=" + activityID +
                ", token2='" + token2 + '\'' +
                ", time=" + time +
                '}';
    }
}
