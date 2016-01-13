package com.bluelable.bluelog.bean;

import com.bluelable.bluelog.BlueLog;

/**
 * Created by xinspace on 01/09/16.
 * 日志模块配置类
 */
public class Configuration {

    /*
    * 常量
    * */
    //日志初始化和获取在线参数接口
    public static final String REQUEST_ONCE_URL = "http://203.195.213.105:8080/blue-mobile-log-server/logEvent";//中国地址
    //    public static final String REQUEST_ONCE_URL = "http://192.168.1.107:8484/blue-mobile-log-server/logEvent";//本地测试
    /*
     * 日志文件上传路径
     * #######注意##########
     * 如果要修改地址前缀的话，请同时修改com.bluemobile.dataresourcecenter.NetworkUtil.HOST_PORT的地址。
     * 如，要使用中国的地址，则相应的com.bluemobile.dataresourcecenter.NetworkUtil.HOST_PORT也应该使用中国的地址
     * ###########################
     */
    //中国地址,修改之前请看上面描述
    public static final String UPLOAD_FILE_URL = "http://203.195.213.105:8080/blue-mobile-log-server/saveLogFile";
    //泰国地址,修改之前请看上面描述
    //    public static final String UPLOAD_FILE_URL = "http://202.6.18.37:8080/blue-mobile-log-server/saveLogFile";
    //本地测试地址
    //        public static final String UPLOAD_FILE_URL = "http://192.168.1.107:8080/blue-mobile-log-server/saveLogFile";

    /*
    * 变量
    * */
    //日志写入类型,0是写入文件，1是写入终端
    private boolean debugMode;
    //服务器配置
    private ServerConfiguration serverConfig;
    //接口
    private ConfigChanged mListener;
    //单例
    private static Configuration sInstance;
    //崩溃日志文件路径
    private String mCrashFilePath;
    //正常日志文件路径
    private String mLogFilePath;
    //崩溃日志上传路径

    private Configuration(boolean debugMode, String crashFilePath, String logFilePath) {
        this.debugMode = debugMode;
        serverConfig = new ServerConfiguration();
        serverConfig.setForceWrite(false);
        serverConfig.setEventListMaxLength(20);
        serverConfig.setNetConfiguration(BlueLog.NET_WIFI_STATUS);
        long anHour = 1 * 60 * 60 * 1000;//一小时
        serverConfig.setTime(System.currentTimeMillis() + anHour);
        mCrashFilePath = crashFilePath;
        mLogFilePath = logFilePath;
    }

    public static Configuration init(boolean debugMode, String crashFilePath, String logFilePath) {
        if(sInstance == null) {
            sInstance = new Configuration(debugMode, crashFilePath, logFilePath);
        }
        return sInstance;
    }

    public static Configuration get() {
        return sInstance;
    }

    /**
     * @author xinspace
     * created at 01/12/16 12:16
     * 设置监听器
     */
    public void setConfigChangedListener(ConfigChanged listener) {
        mListener = listener;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        if(mListener != null) {
            mListener.configChanged();
        }
    }

    public ServerConfiguration getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfiguration serverConfig) {
        this.serverConfig = serverConfig;
        if(mListener != null) {
            mListener.configChanged();
        }
    }

    public String getCrashFilePath() {
        return mCrashFilePath;
    }

    public void setCrashFilePath(String crashFilePath) {
        mCrashFilePath = crashFilePath;
        mListener.configChanged();
    }

    public String getLogFilePath() {
        return mLogFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        mLogFilePath = logFilePath;
        mListener.configChanged();
    }

    /**
     * @author xinspace
     *         created at 01/12/16 12:16
     *         配置文件发生变化时回调接口
     */
    public interface ConfigChanged {
        void configChanged();
    }
}
