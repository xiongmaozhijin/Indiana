package com.bluelable.bluelog.util;

import com.bluelable.bluelog.wrapper.DeviceWrapper;
import com.bluelable.bluelog.wrapper.JsonWrapper;
import com.bluelable.bluelog.wrapper.NetworkWrapper;
import com.bluelable.bluelog.wrapper.ServiceWrapper;

/**
 * Created by xinspace on 01/13/16.
 */
public class LogModuleInstance {

    /*
    * 常量
    * */
    //debug标签
    private static final String TAG = LogModuleInstance.class.getSimpleName();
    //debug开关
    private static final boolean DEBUG = true;

    /*
    * 变量
    * */
    //单例
    private static LogModuleInstance sInstance;
    //获取设备信息
    private DeviceWrapper mDeviceWrapper;
    //JSON转换类
    private JsonWrapper mJsonWrapper;
    //网络接口封装类
    private NetworkWrapper mNetworkWrapper;
    //后台服务封装类
    private ServiceWrapper mServiceWrapper;

    public ServiceWrapper getServiceWrapper() {
        return mServiceWrapper;
    }

    public void setServiceWrapper(ServiceWrapper serviceWrapper) {
        mServiceWrapper = serviceWrapper;
    }

    /**
     * @author xinspace
     * created at 01/13/16 14:53
     * 单例
     */
    public static LogModuleInstance getInstance() {
        if(sInstance == null) {
            sInstance = new LogModuleInstance();
        }
        return sInstance;
    }

    public void setDeviceWrapper(DeviceWrapper wrapper) {
        mDeviceWrapper = wrapper;
    }

    public DeviceWrapper getDeviceWrapper() {
        return mDeviceWrapper;
    }

    public JsonWrapper getJsonWrapper() {
        return mJsonWrapper;
    }

    public void setJsonWrapper(JsonWrapper jsonWrapper) {
        mJsonWrapper = jsonWrapper;
    }

    public NetworkWrapper getNetworkWrapper() {
        return mNetworkWrapper;
    }

    public void setNetworkWrapper(NetworkWrapper networkWrapper) {
        mNetworkWrapper = networkWrapper;
    }
}
