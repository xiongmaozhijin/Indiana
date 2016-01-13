package com.bluelable.bluelog.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.bluelable.bluelog.BlueLog;
import com.bluelable.bluelog.bean.BaseEvent;
import com.bluelable.bluelog.bean.BaseEventList;
import com.bluelable.bluelog.bean.Configuration;
import com.bluelable.bluelog.bean.CrashEventValue;
import com.bluelable.bluelog.wrapper.DeviceWrapper;
import com.bluelable.bluelog.wrapper.JsonWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by xinspace on 12/31/15.
 * 自动捕获崩溃
 */

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author xinspce
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {

    /*
    * 常量
    * */
    //debug标签
    private static final String TAG = CrashHandler.class.getSimpleName();
    //debug开关
    private static final boolean DEBUG = false;
    // CrashHandler 内部类实例
    private static final class InstanceHolder {
        public static final CrashHandler sInstance = new CrashHandler();
    }

    /*
    * 变量
    * */
    // 程序的 Context 对象
    private Context mContext;
    // 系统默认的 UncaughtException 处理类
    private UncaughtExceptionHandler mDefaultHandler;


    /** 保证只有一个 CrashHandler 实例 */
    private CrashHandler() {
    }

    /** 获取 CrashHandler 实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return InstanceHolder.sInstance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
        }
        System.exit(0);
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        String filePath = saveCrashInfo2File(ex);
        if(filePath != null) {
            return true;
        }
        return false;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        //用于保存崩溃事件的内容
        CrashEventValue eventValue = new CrashEventValue();
        //获取崩溃的包名
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            eventValue.setPkgName(pi.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //获取错误栈
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        eventValue.setError(writer.toString());
        //用于封装崩溃事件
        BaseEvent event = new BaseEvent();
        event.setEventId(BlueLog.EVENT_CRASH);
        event.setEventLevel(BlueLog.EVENT_LEVEL_5);
        event.setEventTag(BlueLog.SYS_TAG);
        JsonWrapper jsonWrapper = LogModuleInstance.getInstance().getJsonWrapper();
        event.setValue(jsonWrapper.toJson(eventValue));
        //把崩溃日志写入崩溃事件列表
        BaseEventList list = new BaseEventList();
        DeviceWrapper deviceWrapper = LogModuleInstance.getInstance().getDeviceWrapper();
        list.setUserId(deviceWrapper.getUserId());//获取用户ID
        list.setPhoneCode(deviceWrapper.getPhoneModel());//获取手机型号
        list.setProductVersion(deviceWrapper.getProductVersion());//获取产品版本号
        list.addEvent(event);
        //导出崩溃事件
        String eventString = jsonWrapper.toJson(list) + "\n";
        //写入日志文件
        String filePath = Configuration.get().getCrashFilePath();
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(filePath, true);
            fos.write(eventString.getBytes());
            fos.close();
            return filePath;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}
