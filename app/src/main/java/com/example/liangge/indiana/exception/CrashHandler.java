package com.example.liangge.indiana.exception;

import android.content.Context;
import android.util.Log;

import com.example.liangge.indiana.comm.PhoneUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 不受检异常处理类
 * Created by baoxing on 2015/12/12.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CrashHandler.class.getSimpleName();

    private Thread.UncaughtExceptionHandler mDefaultUEH;

    private static CrashHandler mInstance;

    private Context mContext;

    private LogTrackManager mLogTrackManager;

    private CrashHandler(Context context) {
        this.mContext = context;
        this.mDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        initManager(context);
    }

    private void initManager(Context context) {
        mLogTrackManager = new LogTrackManager(context);
    }

    public synchronized static void init(Context context) {
        if (mInstance == null) {
            mInstance = new CrashHandler(context);
        }

        Thread.setDefaultUncaughtExceptionHandler(mInstance);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "uncaughtException()");

        handleUnCaughtException(thread, ex);
        this.mDefaultUEH.uncaughtException(thread, ex);
    }

    private void handleUnCaughtException(Thread thread, Throwable ex) {
        String logInfo = getInfoHeader() + PhoneUtils.getPhoneInfo() + getExceptionInfo(thread, ex);
        saveToFile(logInfo);
    }

    private void saveToFile(String logInfo) {
        mLogTrackManager.recordCrashLog(mContext, logInfo);
    }

    /**
     * 得到信息的头部信息
     * @return
     */
    private String getInfoHeader() {
        String strHeader = "\n";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        strHeader += date;
        strHeader += "\n";
        Log.e(TAG, strHeader);
        return strHeader;
    }

    /**
     * 得到崩溃的信息
     * @param thread
     * @param ex
     * @return
     */
    private String getExceptionInfo(Thread thread, Throwable ex) {
        String strExceptionInfo = "\n";
        Writer rWrite = new StringWriter();
        PrintWriter printWriter = new PrintWriter(rWrite);
        ex.printStackTrace(printWriter);
        strExceptionInfo += rWrite.toString();
        strExceptionInfo += "\n";

        Log.e(TAG, strExceptionInfo);
        return strExceptionInfo;
    }


}
