package com.bluelable.bluelog.model;

import android.util.Log;

import com.bluelable.bluelog.BlueLog;
import com.bluelable.bluelog.bean.BaseEvent;
import com.bluelable.bluelog.bean.BaseEventList;

import org.json.JSONException;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by xinspace on 01/12/16.
 * 写入终端的队列
 */
public class ConsoleEventListImpl implements EventList, IOLayer {

    /*
    * 常量
    * */
    //debug标签
    private static final String TAG = ConsoleEventListImpl.class.getSimpleName();
    //debug开关
    private static final boolean DEBUG = true;

    /*
    * 变量
    * */
    //单例
    private static ConsoleEventListImpl sInstance;

    private ConsoleEventListImpl() {}

    public static ConsoleEventListImpl getInstance() {
        if(sInstance == null) {
            sInstance = new ConsoleEventListImpl();
        }
        return sInstance;
    }

    @Override
    public void addEvent(BaseEvent event) {
        int level = event.getEventLevel();
        String tag = event.getEventTag();
        if(!tag.equals(BlueLog.SYS_TAG)) {
            //符合条件,不输出系统事件
            Log.w(tag, tag + " level" + level + ", eventId:" + event.getEventId() + ", eventValue:" +
                    event.getValue() + ";");
        }
    }

    @Override
    public void clearEventList() {
    }

    @Override
    public UUID getListId() {
        return null;
    }

    @Override
    public void writeLogs(BaseEventList eventList, WriteListener listener) throws IOException, JSONException {
    }
}
