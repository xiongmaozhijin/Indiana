package com.bluelable.bluelog.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bluelable.bluelog.wrapper.NetworkWrapper;
import com.bluelable.bluelog.wrapper.ServiceWrapper;

import java.io.File;

/**
 * Created by xinspace on 12/14/15.
 * 该对象用于后台服务定时调用，用于传递日志文件
 */
public class CrashRunnable implements Runnable, NetChangedReceiver.NetworkStatusChanged {

    /*
    * 常量
    * */
    //debug标签
    private static final String TAG = CrashRunnable.class.getSimpleName();
    //调试开关
    private static final boolean DEBUG = true;
    //该任务的任务标识
    public static final String TASK_FLAG = "sendCrashLog";

    /*
    * 变量
    * */
    //Context
    private Context mContext;
    //是否发送成功
    private static boolean sSentSucceed;
    //上传的url
    private String mUploadUrl;
    //上传的文件绝对路径
    private String mFilePath;

    /**
     * @author xinspace
     * created at 12/14/15 15:14
     * 构造方法
     */
    public CrashRunnable(Context context, String url, String filePath) {
        mContext = context.getApplicationContext();
        sSentSucceed = false;
        mUploadUrl = url;
        mFilePath = filePath;
    }

    @Override
    public void run() {
        sendLogFile();
    }

    /**
     * @author xinspace
     * created at 12/14/15 15:24
     * 发送日志
     */
    private void sendLogFile() {
        //检测网络
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        final ServiceWrapper serviceWrapper = LogModuleInstance.getInstance().getServiceWrapper();
        if (networkInfo != null && networkInfo.isConnected()) {
            //联网，发送崩溃日志文件
            //定义上传文件监听器
            NetworkWrapper.OnUploadListener listener = new NetworkWrapper.OnUploadListener() {
                @Override
                public void onUploadFinished(String fileName) {
                    //上传成功
                    if(DEBUG) {
                        Log.i(TAG, "@@@ crash file uploaded @@@");
                    }
                    //停止上传崩溃日志的任务
                    serviceWrapper.cancelRepeatTask();
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
                }
            };
            //打开崩溃日志文件
            File file = new File(mFilePath);
            //获取网络封装类
            NetworkWrapper wrapper = LogModuleInstance.getInstance().getNetworkWrapper();
            if(wrapper == null) {
                return;
            }
            //上传文件
            try {
                if(file.exists()) {
                    wrapper.uploadFile(mUploadUrl, file.getAbsolutePath(), listener);
                    if(DEBUG) {
                        Log.i(TAG, "@@@ crash file ready to upload @@@");
                    }
                } else {
                    serviceWrapper.cancelRepeatTask();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void networkStatus() {
        if(!sSentSucceed) {
            sendLogFile();
        }
    }
}
