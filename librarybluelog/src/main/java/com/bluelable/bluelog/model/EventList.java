package com.bluelable.bluelog.model;


import com.bluelable.bluelog.bean.BaseEvent;

import java.util.UUID;

/**
 * Created by xinspace on 11/18/15.
 * 向日志事件列表中添加事件
 */
public interface EventList {

    /**
     * @author xinspace
     * created at 01/09/16 17:40
     * 添加日志事件
     * @param event 事件对象
     */
    void addEvent(BaseEvent event);

    /**
     * @author xinspace
     * created at 11/20/15 14:40
     * 清除日志事件列表
     */
    void clearEventList();

    /**
     * @author xinspace
     * created at 01/09/16 17:46
     * 获取事件列表的ID
     * @return 事件列表ID
     */
    UUID getListId();
}
