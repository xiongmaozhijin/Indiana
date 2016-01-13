package com.bluelable.bluelog.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xinspace on 01/04/16.
 * 事件队列
 */
public class BaseEventList {

    //队列的ID，不写入日志文件，用于标识一次写入的日志队列
    private UUID listId;
    //用户ID
    private String userId;
    //产品版本号
    private int productVersion;
    //手机型号
    private String phoneCode;
    //事件列表
    private List<BaseEvent> eventList;

    public BaseEventList() {
        listId = UUID.randomUUID();
        eventList = new ArrayList<BaseEvent>();
    }

    /**
     * @author xinspace
     * created at 01/04/16 15:54
     * 增加事件
     * @param event 事件对象
     */
    public void addEvent(BaseEvent event) {
        eventList.add(event);
    }

    /**
     * @author xinspace
     * created at 01/09/16 18:11
     * @return 事件列表中的事件数
     */
    public int getEventCount() {
        return eventList.size();
    }

    /**
     * @author xinspace
     * created at 01/11/16 11:28
     * 清空事件队列
     */
    public void clearList() {
        eventList.clear();
        listId = UUID.randomUUID();
    }

    /**
     * @author xinspace
     * created at 01/11/16 11:38
     * 复制一个对象
     */
    public BaseEventList clone() {
        BaseEventList events = new BaseEventList();
        events.setProductVersion(productVersion);
        events.setListId(listId);
        events.setPhoneCode(phoneCode);
        events.setUserId(userId);
        events.copyEventList(eventList);
        return events;
    }

    /**
     * @author xinspace
     * created at 01/11/16 11:41
     * 全复制事件队列
     */
    public void copyEventList(List<BaseEvent> list) {
        eventList.addAll(list);
    }

    public UUID getListId() {
        return listId;
    }

    public void setListId(UUID listId) {
        this.listId = listId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(int productVersion) {
        this.productVersion = productVersion;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public List<BaseEvent> getEventList() {
        return eventList;
    }
}
