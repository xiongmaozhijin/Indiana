package com.bluelable.bluelog;

import android.content.Context;
import android.util.Log;

import com.bluelable.bluelog.bean.BaseEvent;
import com.bluelable.bluelog.bean.Configuration;
import com.bluelable.bluelog.bean.InitEventValue;
import com.bluelable.bluelog.bean.ServerConfiguration;
import com.bluelable.bluelog.model.ConsoleEventListImpl;
import com.bluelable.bluelog.model.EventList;
import com.bluelable.bluelog.model.FileEventListImpl;
import com.bluelable.bluelog.util.ForceUploadRunnable;
import com.bluelable.bluelog.util.LogFileRunnable;
import com.bluelable.bluelog.wrapper.DeviceWrapper;
import com.bluelable.bluelog.wrapper.JsonWrapper;
import com.bluelable.bluelog.util.LogModuleInstance;
import com.bluelable.bluelog.wrapper.NetworkWrapper;
import com.bluelable.bluelog.wrapper.ServiceWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xinspace on 11/12/15.
 * 日志模块主文件，实现日志模块的所有功能接口：
 * 1.初始化：发送设备信息、用户ID、产品ID、渠道号、网络情况，接收日志输出标识和日志发送策略
 * 2.页面时长统计：在每个Activity的onResume中调用xx开始统计，在onPause中调用xx停止统计
 * 3.页面跳转统计：在每个View的onResume中调用xx开始统计，在onPause中调用xx停止统计
 * 4.统计后台申请的自定义事件：先在服务器申请自定义事件，并生成自定义ID，调用xx统计该事件的发生的次数或发生的时长
 * 5.统计客户端申请的自定义事件：未在服务端申请事件，直接在客户端定义事件，并调用xx统计事件发生的次数或发生的时长
 * 6.获取在线参数：在服务器后台动态添加参数，并从客户端调用xx获取这些参数的值
 * 7.严重错误提交：当发生错误时，调用xx接口统计错误发生次数
 * <p/>
 * 8.记录热词点击信息：记录用户点击搜索热词的信息
 * 9.记录新闻点击信息：记录用户点击新闻的名称、停留的时间
 * 10.记录广告点击信息： 记录用户点击广告的信息
 * 11.记录搜索信息： 记录用户使用搜索的信息，搜索的关键字和使用的引擎
 */
public class BlueLog implements Configuration.ConfigChanged {

    /*
    * 常量
    * */
    private static final String TAG = BlueLog.class.getSimpleName();//debug 标签
    //调试开关
    private static final boolean DEBUG = true;
    /*
     * 系统事件ID取值
     */
    //统计时长开始事件
    public static final int EVENT_SESSION_START = -1;
    //统计时长结束事件
    public static final int EVENT_SESSION_END = -2;
    //页面跳转开始事件
    public static final int EVENT_PAGE_JUMP_START = -3;
    //页面跳转结束事件
    public static final int EVENT_PAGE_JUMP_END = -4;
    //客户端自定义事件
    public static final int EVENT_CLIENT_CUSTOM = -5;
    //服务器自定义事件
    public static final int EVENT_SERVER_CUSTOM = -6;
    //获取在线参数事件
    public static final int EVENT_ONLINE_PARAMS = -7;
    //崩溃事件
    public static final int EVENT_CRASH = -8;
    //初始化事件
    public static final int EVENT_INITIAL = -9;
    //日志事件级别，1最低，5最高。
    public static final int EVENT_LEVEL_1 = 1;
    public static final int EVENT_LEVEL_2 = 2;
    public static final int EVENT_LEVEL_3 = 3;
    public static final int EVENT_LEVEL_4 = 4;
    public static final int EVENT_LEVEL_5 = 5;
    //系统标签，不会过滤掉拥有该标签的事件
    public static final String SYS_TAG = "ALL";
    /*
    * 网络状态值
    * */
    //wifi下上传
    public static final int NET_WIFI_STATUS = 31;
    //有网时上传
    public static final int NET_ALL_STATUS = 32;
    /*
    * 页面跳转、页面时长统计事件用到的页面类名的json的key
    * */
    public static final String PAGE_NAME_KEY = "pageName";

    /*
    * 变量
    * */
    //Context
    private Context mContext;
    //单例
    private static BlueLog sInstance;
    //事件队列
    private EventList mEventList;
    //配置文件
    private static Configuration sConfiguration;
    //服务封装类
    private ServiceWrapper mServiceWrapper;
    //网络封装类
    private NetworkWrapper mNetworkWrapper;
    //设备封装类
    private DeviceWrapper mDeviceWrapper;
    //JSON封装类
    private JsonWrapper mJsonWrapper;

    /**
     * @author xinspace
     * created at 11/12/15 17:52
     * 私有构造方法
     */
    private BlueLog(Context context) {
        mContext = context.getApplicationContext();
//        Configuration.getInstance().setConfigChangedListener(this);
        sConfiguration = Configuration.get();
        mEventList = FileEventListImpl.getInstance();
        mServiceWrapper = LogModuleInstance.getInstance().getServiceWrapper();
        mNetworkWrapper = LogModuleInstance.getInstance().getNetworkWrapper();
        mDeviceWrapper = LogModuleInstance.getInstance().getDeviceWrapper();
        mJsonWrapper = LogModuleInstance.getInstance().getJsonWrapper();
    }

    /**
     * @author xinspace
     * created at 11/12/15 17:55
     * 静态工厂方法
     * @param context Context
     */
    public static BlueLog getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new BlueLog(context);
        }
        return sInstance;
    }

    /**
     * @author xinspace
     * created at 01/12/16 12:07
     * 设置日志模块是否是debug输出到终端
     */
    public void setDebugable(boolean debugable) {
        sConfiguration.setDebugMode(debugable);
    }

    /**
     * @author xinspace
     * created at 01/12/16 12:29
     * 读取配置，创建事件队列
     */
    private void init() {
        boolean debug = sConfiguration.isDebugMode();
        if(debug) {
            mEventList = ConsoleEventListImpl.getInstance();
        } else {
            mEventList = FileEventListImpl.getInstance();
            sConfiguration.getServerConfig().setForceWrite(true);
            //是否要强制上传日志
            if(sConfiguration.getServerConfig().isForceWrite()) {
                //启动发送日志文件的后台线程
                long time = getTodayTime();
                ForceUploadRunnable runnable = new ForceUploadRunnable(mContext, Configuration.UPLOAD_FILE_URL,
                        sConfiguration.getLogFilePath());
//                ServiceTaskLab.getInstance(mContext).addAtTimeTask(time, runnable, ForceUploadRunnable.TASK_FLAG);
                mServiceWrapper.startAtTimeTask();
            }
            //启动发送日志文件的后台线程
            long time = getTodayTime();
            LogFileRunnable runnable = new LogFileRunnable(mContext, Configuration.UPLOAD_FILE_URL,
                    sConfiguration.getLogFilePath());
//            ServiceTaskLab.getInstance(mContext).addAtTimeTask(time, runnable, LogFileRunnable.TASK_FLAG);
            mServiceWrapper.startAtTimeTask();
        }
    }

    /**
     * @author xinspace
     * created at 01/12/16 18:38
     * 修改时间为今天的时分秒
     */
    public static long getTodayTime() {
        //获取时间戳配置
        long serverTime = sConfiguration.getServerConfig().getTime();
        Date serverDate = new Date(serverTime);
        Calendar serverCalendar = Calendar.getInstance();
        serverCalendar.setTime(serverDate);
        int serverHour = serverCalendar.get(Calendar.HOUR_OF_DAY);
        int serverMin = serverCalendar.get(Calendar.MINUTE);
        int serverSec = serverCalendar.get(Calendar.SECOND);
        int serverMill = serverCalendar.get(Calendar.MILLISECOND);
        //当前时间
        Calendar currCalendar = Calendar.getInstance();
        Date currDate = new Date();
        currCalendar.setTime(currDate);
        currCalendar.set(Calendar.HOUR_OF_DAY, serverHour);
        currCalendar.set(Calendar.MINUTE, serverMin);
        currCalendar.set(Calendar.SECOND, serverSec);
        currCalendar.set(Calendar.MILLISECOND, serverMill);
        return currCalendar.getTimeInMillis();
    }

    /**
     * @author xinspace
     * created at 11/20/15 15:28
     * 初始化日志模块
     * @param channelId 发布渠道的ID，这个ID客户端自己定义
     * @param productId 使用本日志模块的用户的ID，客户端自己定义
     */
    public void initLog(int channelId, int productId) throws JSONException, IOException {
        if(DEBUG) {
            Log.i(TAG, "@@@ init log @@@");
        }
//        Configuration.getInstance().setWritePlace(writePlace);
        BaseEvent initEvent = new BaseEvent();
        initEvent.setEventId(EVENT_INITIAL);
        initEvent.setEventLevel(EVENT_LEVEL_5);
        initEvent.setEventTag(SYS_TAG);
        InitEventValue initEventValue = new InitEventValue();
//        BaseInfoBean bean = BaseInfoBean.getInstance(mContext);
//        initEventValue.setUserId(bean.getUserId());
        initEventValue.setUserId(mDeviceWrapper.getUserId());
        initEventValue.setChannelId(channelId);
        initEventValue.setProductId(productId);
//        initEvent.setValue(mGson.toJson(initEventValue));
        initEvent.setValue(mJsonWrapper.toJson(initEventValue));
        mNetworkWrapper.requestOnce(Configuration.REQUEST_ONCE_URL, mJsonWrapper.toJson(initEvent), new NetworkWrapper.NetResponse() {
            @Override
            public void netResponse(JSONObject response) {
                try {
                    JSONObject obj = response.getJSONObject("result");
//                    ServerConfiguration configuration = mGson.fromJson(obj.toString(), ServerConfiguration.class);
                    ServerConfiguration configuration = (ServerConfiguration) mJsonWrapper.fromJson(obj.toString(), ServerConfiguration.class);
                    sConfiguration.setServerConfig(configuration);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void netError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @author xinspace
     * created at 11/20/15 15:34
     * 添加开始统计页面时长事件
     * @param level 日志级别
     * @param clsName 要统计的类的完整类名,如com.example.example.ClsName
     */
    public void addSessionStartEvent(int level, String clsName) throws JSONException, IOException {
        if(DEBUG) {
            Log.i(TAG, "@@@ addSessionStartEvent @@@");
        }
        BaseEvent event = new BaseEvent();
        event.setEventId(EVENT_SESSION_START);
        event.setEventTag(SYS_TAG);
        event.setEventLevel(level);
        JSONObject object = new JSONObject();
        object.put(PAGE_NAME_KEY, clsName);
        event.setValue(object.toString());
        mEventList.addEvent(event);
    }

    /**
     * @author xinspace
     * created at 11/20/15 15:36
     * 添加结束统计页面时长事件
     * @param level 日志级别
     * @param clsName 要统计的类的完整类名,如com.example.example.ClsName
     */
    public void addSessionEndEvent(int level, String clsName) throws JSONException, IOException {
        if(DEBUG) {
            Log.i(TAG, "@@@ addSessionEndEvent @@@");
        }
        BaseEvent event = new BaseEvent();
        event.setEventId(EVENT_SESSION_END);
        event.setEventTag(SYS_TAG);
        event.setEventLevel(level);
        JSONObject object = new JSONObject();
        object.put(PAGE_NAME_KEY, clsName);
        event.setValue(object.toString());
        mEventList.addEvent(event);
    }

    /**
     * @author xinspace
     * created at 11/20/15 15:42
     * 添加页面跳转开始事件
     * @param level 日志级别
     * @param clsName 要统计的类的完整类名,如com.example.example.ClsName
     */
    public void addPageJumpStartEvent (int level, String clsName) throws JSONException, IOException {
        if(DEBUG) {
            Log.i(TAG, "@@@ addPageJumpStartEvent @@@");
        }
        BaseEvent event = new BaseEvent();
        event.setEventId(EVENT_PAGE_JUMP_START);
        event.setEventTag(SYS_TAG);
        event.setEventLevel(level);
        JSONObject object = new JSONObject();
        object.put(PAGE_NAME_KEY, clsName);
        event.setValue(object.toString());
        mEventList.addEvent(event);
    }

    /**
     * @author xinspace
     * created at 11/20/15 15:42
     * 添加页面跳转结束事件
     * @param level 日志级别
     * @param clsName 要统计的类的完整类名,如com.example.example.ClsName
     */
    public void addPageJumpEndEvent (int level, String clsName) throws JSONException, IOException {
        if(DEBUG) {
            Log.i(TAG, "@@@ addPageJumpEndEvent @@@");
        }
        BaseEvent event = new BaseEvent();
        event.setEventId(EVENT_PAGE_JUMP_END);
        event.setEventTag(SYS_TAG);
        event.setEventLevel(level);
        JSONObject object = new JSONObject();
        object.put(PAGE_NAME_KEY, clsName);
        event.setValue(object.toString());
        mEventList.addEvent(event);
    }

    /**
     * @author xinspace
     * created at 11/20/15 15:44
     * 添加客户端自定义事件
     * @param level 日志级别
     * @param eventTag 客户端自定义事件的key，服务器以此进行统计
     * @param clientEventValue 客户端自定义事件
     */
    public void addClientEvent(int level, String eventTag, String clientEventValue) throws JSONException, IOException {
        if(DEBUG) {
            Log.i(TAG, "@@@ addClientCustomEvent @@@");
        }
        BaseEvent event = new BaseEvent();
        event.setEventTag(eventTag);
        event.setEventLevel(level);
        event.setEventId(EVENT_CLIENT_CUSTOM);
        event.setValue(clientEventValue);
        mEventList.addEvent(event);
    }

    /**
     * @author xinspace
     * created at 11/20/15 15:44
     * 添加服务器自定义事件
     * @param level 日志级别
     * @param eventKey 服务器自定义事件的key，服务器以此进行统计，该key必须是服务器分配的key
     * @param values 服务器自定义事件数值，是键值对形式
     */
    public void addServerCustomEvent(int level, String eventKey, String values) throws JSONException, IOException {
        if(DEBUG) {
            Log.i(TAG, "@@@ addServerCustomEvent @@@");
        }
        BaseEvent event = new BaseEvent();
        event.setEventId(EVENT_SERVER_CUSTOM);
        event.setEventTag(eventKey);
        event.setEventLevel(level);
        event.setValue(values);
        mEventList.addEvent(event);
    }

    /**
     * @author xinspace
     * created at 11/20/15 16:10
     * 获取在线参数
     */
    public void getOnlineParams(List<String> keys, NetworkWrapper.NetResponse listener) throws JSONException {
        if(DEBUG) {
            Log.i(TAG, "@@@ getOnlineParams @@@");
        }
        JSONObject object = new JSONObject();
        for(String key : keys) {
            object.put(key, " ");
        }
        mNetworkWrapper.requestOnce(Configuration.REQUEST_ONCE_URL, mJsonWrapper.toJson(object), listener);
    }

    @Override
    public void configChanged() {
        //配置文件发生变化
        init();
    }
}
