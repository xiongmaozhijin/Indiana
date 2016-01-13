package com.bluelable.bluelog.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bluelable.bluelog.BlueLog;
import com.bluelable.bluelog.bean.Configuration;
import com.bluelable.bluelog.wrapper.NetworkWrapper;
import com.bluelable.bluelog.wrapper.ServiceWrapper;

import java.io.File;
import java.util.Random;

/**
 * Created by xinspace on 01/12/16.
 */
public class LogFileRunnable implements Runnable, Configuration.ConfigChanged, NetChangedReceiver.NetworkStatusChanged {

    /*
     * 常量
     * */
    //debug标签
    private static final String TAG = LogFileRunnable.class.getSimpleName();
    //调试开关
    private static final boolean DEBUG = true;
    //该任务的任务标识
    public static final String TASK_FLAG = "sendLogFile";
    //一天的毫秒数
    private static final long sADay = 1000 * 60 * 60 * 24;
    //一小时的毫秒数
    private static final long sAnHour = 1000 * 60 * 60;

    /*
    * 变量
    * */
    //Context
    private Context mContext;
    //配置中的上传时间点
    private long mTime;
    //上传失败次数
    private static int sFailedTimes = 0;
    //是否上传成功
    private static boolean sSentSucceed;
    //上传URL
    private String mUploadUrl;
    //上传文件的路径
    private String mFilePath;

    public LogFileRunnable(Context context, String uploadUrl, String filePath) {
        mContext = context.getApplicationContext();
        configChanged();
        sFailedTimes = 0;
        sSentSucceed = false;
        mUploadUrl = uploadUrl;
        mFilePath = filePath;
    }

    @Override
    public void configChanged() {
        mTime = BlueLog.getTodayTime();
    }

    @Override
    public void run() {
        tryToSendFile();
    }

    /**
     * @author xinspace
     * created at 01/12/16 16:14
     * 尝试发送日志文件
     */
    private void tryToSendFile() {
        if(isNetOk()) {
            //定义上传文件监听器
            NetworkWrapper.OnUploadListener uploadListener = new NetworkWrapper.OnUploadListener() {
                @Override
                public void onUploadFinished(String fileName) {
                    //上传成功
                    if(DEBUG) {
                        Log.i(TAG, "@@@ log file uploaded @@@");
                    }
                    //明天该时间点再次上传
                    tomorrowUpload();
                    //删除文件
                    File file = new File(fileName);
                    if(file.exists()) {
                        file.delete();
                    }
                    sSentSucceed = true;
                    if(DEBUG) {
                        Log.i(TAG, "@@@ crash file cleaned @@@");
                    }
                }

                @Override
                public void onUploadErrorOccurrs(Exception e) {
                    if(sFailedTimes < 3) {
                        //有网时上传出错,在服务器给定的时间点向后随机延迟最多一个小时的时间，连续三次
                        Random random = new Random();
                        long randomDelay = random.nextLong() % sAnHour;
                        long time = mTime + randomDelay;
                        //延迟最多一小时，再次启动该任务
                        ServiceWrapper wrapper = LogModuleInstance.getInstance().getServiceWrapper();
                        wrapper.startAtTimeTask();
                        sFailedTimes++;
                    } else {
                        //尝试三次，还失败，则明天该时间点上传
                        tomorrowUpload();
                        sFailedTimes = 0;
                    }
                }
            };
            //打开日志文件
            File file = new File(mFilePath);
            //上传文件
            try {
                if(file.exists()) {
                    NetworkWrapper wrapper = LogModuleInstance.getInstance().getNetworkWrapper();
                    wrapper.uploadFile(mUploadUrl, file.getAbsolutePath(), uploadListener);
                    if(DEBUG) {
                        Log.i(TAG, "@@@ log file ready to upload @@@");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //网络不符合要求
        }
    }

    /**
     * @author xinspace
     * created at 01/12/16 16:06
     * 设定时间为明天该时间点上传
     */
    private void tomorrowUpload() {
        //读取服务器给定的上传时间，加上一天的毫秒数，即明天的这个时刻再次上传
        long time = mTime + sADay;
        //明天的这个时刻再次启动发送日志线程
        ServiceWrapper wrapper = LogModuleInstance.getInstance().getServiceWrapper();
        wrapper.startAtTimeTask();
    }

    /**
     * @author xinspace
     * created at 01/12/16 15:40
     * 网络情况是否允许
     */
    private boolean isNetOk() {
        int netType = getNetWorkType();
        int configNetType = Configuration.get().getServerConfig().getNetConfiguration();
        if(configNetType == netType) {
            //网络配置符合
            return true;
        }
        return false;
    }

    /**
     * @author xinspace
     * created at 01/12/16 15:34
     * 获取本机网络情况
     */
    private int getNetWorkType() {
        //检测网络
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //网络连接
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return BlueLog.NET_WIFI_STATUS;
            } else {
                return BlueLog.NET_ALL_STATUS;
            }
        }
        return -1;
    }

    @Override
    public void networkStatus() {
        if(!sSentSucceed) {
            tryToSendFile();
        }
    }
}
