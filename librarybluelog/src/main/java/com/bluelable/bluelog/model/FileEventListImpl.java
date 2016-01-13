package com.bluelable.bluelog.model;

import android.util.Log;

import com.bluelable.bluelog.bean.BaseEvent;
import com.bluelable.bluelog.bean.BaseEventList;
import com.bluelable.bluelog.bean.Configuration;
import com.bluelable.bluelog.util.WriteFileAsync;
import com.bluelable.bluelog.wrapper.DeviceWrapper;
import com.bluelable.bluelog.util.LogModuleInstance;

import org.json.JSONException;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xinspace on 01/11/16.
 * 写入文件的事件队列
 */
public class FileEventListImpl implements EventList, IOLayer, Configuration.ConfigChanged {

    /*常量*/
    //debug标签
    private static final String TAG = FileEventListImpl.class.getSimpleName();
    //debug开关
    private static final boolean DEBUG = true;

    /*
    * 变量
    * */
    //事件队列
    private BaseEventList mEventList;
    //单例
    private static FileEventListImpl sInstance;
    //互斥锁，保证不同时写和读日志列表
    private Lock mLock;
    //事件队列长度
    private int mMaxLength;

    private FileEventListImpl() {
        mEventList = new BaseEventList();
        //获取手机信息
        DeviceWrapper mDeviceWrapper = LogModuleInstance.getInstance().getDeviceWrapper();
        mEventList.setUserId(mDeviceWrapper.getUserId());
        mEventList.setPhoneCode(mDeviceWrapper.getPhoneModel());
        mEventList.setProductVersion(mDeviceWrapper.getProductVersion());
        mLock = new ReentrantLock();//初始化互斥锁
        getEventListMaxLength();
    }

    public static EventList getInstance() {
        if(sInstance == null) {
            sInstance = new FileEventListImpl();
        }
        return sInstance;
    }

    private void getEventListMaxLength() {
        mMaxLength = Configuration.get().getServerConfig().getEventListMaxLength();
    }

    @Override
    public void addEvent(BaseEvent event) {
        mLock.lock();
        mEventList.addEvent(event);
        mLock.unlock();
        tryToWrite();
    }

    @Override
    public void clearEventList() {
        mEventList.clearList();
    }

    @Override
    public UUID getListId() {
        return mEventList.getListId();
    }

    /**
     * @author xinspace
     * created at 01/11/16 11:34
     * 尝试写入文件
     */
    private void tryToWrite() {
        //配置文件中允许的最大队列长度
        if(mEventList.getEventCount() >= mMaxLength) {
            mLock.lock();
            BaseEventList list = mEventList.clone();
            mEventList.clearList();
            mLock.unlock();
            try {
                writeLogs(list, new WriteListener() {
                    @Override
                    public void writeSuccess(UUID listId, String filePath) {
                        if (DEBUG) {
                            Log.i(TAG, "@@@ listId=" + listId.toString() + ", filePath=" + filePath + " @@@");
                        }
                    }

                    @Override
                    public void writeError(UUID listId) {
                        if (DEBUG) {
                            Log.e(TAG, "@@@ Error!! listId=" + listId.toString() + " @@@");
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeLogs(BaseEventList eventList, WriteListener listener) throws IOException, JSONException {
        if(DEBUG) {
            Log.i(TAG, "@@@ event list size = " + eventList.getEventCount() + "@@@");
            Log.i(TAG, "@@@ write file @@@");
        }
        new WriteFileAsync(eventList, listener).execute();
    }

    @Override
    public void configChanged() {
        getEventListMaxLength();
    }
}
