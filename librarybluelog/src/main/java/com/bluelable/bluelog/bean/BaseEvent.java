package com.bluelable.bluelog.bean;

/**
 * Created by xinspace on 12/31/15.
 * 日志事件类
 */
public class BaseEvent {

    //事件所属的系统事件ID
    private int eventId;
    //事件的标签，用于过滤事件
    private String eventTag;
    //事件的级别，用于过滤事件
    private int eventLevel;
    //事件的内容
    private String value;

    //getters and setters

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public int getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
