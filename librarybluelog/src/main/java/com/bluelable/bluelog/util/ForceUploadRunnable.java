package com.bluelable.bluelog.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bluelable.bluelog.bean.Configuration;
import com.bluelable.bluelog.wrapper.NetworkWrapper;
import com.bluelable.bluelog.wrapper.ServiceWrapper;

import java.io.File;
import java.util.Random;

/**
 * Created by xinspace on 01/12/16.
 */
public class ForceUploadRunnable implements Runnable {

    /*
     * 常量
     * */
    //debug标签
    private static final String TAG = ForceUploadRunnable.class.getSimpleName();
    //调试开关
    private static final boolean DEBUG = true;
    //该任务的任务标识
    public static final String TASK_FLAG = "forceSendLogFile";
    //一小时的毫秒数
    private static final long sAnHour = 1000 * 60 * 60;

    /*
    * 变量
    * */
    //Context
    private Context mContext;
    //上传URL
    private String mUploadUrl;
    //上传文件的路径
    private String mFilePath;

    public ForceUploadRunnable(Context context, String uploadUrl, String filePath) {
        mContext = context.getApplicationContext();
        mUploadUrl = uploadUrl;
        mFilePath = filePath;
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
        //检测网络
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //网络连接
            //定义上传文件监听器
            NetworkWrapper.OnUploadListener uploadListener = new NetworkWrapper.OnUploadListener() {
                @Override
                public void onUploadFinished(String fileName) {
                    //上传成功
                    if(DEBUG) {
                        Log.i(TAG, "@@@ log file uploaded @@@");
                    }
                    //删除文件
                    File file = new File(fileName);
                    if(file.exists()) {
                        file.delete();
                    }
                    Configuration.get().getServerConfig().setForceWrite(false);
                    if(DEBUG) {
                        Log.i(TAG, "@@@ crash file cleaned @@@");
                    }
                }

                @Override
                public void onUploadErrorOccurrs(Exception e) {
                    //有网时上传出错,在服务器给定的时间点向后随机延迟最多一个小时的时间，连续三次
                    Random random = new Random();
                    long randomDelay = random.nextLong() % sAnHour;
                    long time = System.currentTimeMillis() + randomDelay;
                    //延迟最多一小时，再次启动该任务
                    ServiceWrapper wrapper = LogModuleInstance.getInstance().getServiceWrapper();
                    wrapper.startAtTimeTask();
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
        }
    }
}
