package com.bluelable.bluelog.bean;

/**
 * Created by xinspace on 01/09/16.
 * 与服务器相关的配置，从服务器获取
 */
public class ServerConfiguration {

    //是否强制写入
    private boolean forceWrite;
    //日志列表保存最大数目
    private int eventListMaxLength;
    //网络情况配置，在哪种网络情况下发送日志文件
    private int netConfiguration;
    //日志上传的时间点，每天的这个时间上传日志文件，上传成功后，自动延时一天
    private long time;

    public boolean isForceWrite() {
        return forceWrite;
    }

    public void setForceWrite(boolean forceWrite) {
        this.forceWrite = forceWrite;
    }

    public int getEventListMaxLength() {
        return eventListMaxLength;
    }

    public void setEventListMaxLength(int eventListMaxLength) {
        this.eventListMaxLength = eventListMaxLength;
    }

    public int getNetConfiguration() {
        return netConfiguration;
    }

    public void setNetConfiguration(int netConfiguration) {
        this.netConfiguration = netConfiguration;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
