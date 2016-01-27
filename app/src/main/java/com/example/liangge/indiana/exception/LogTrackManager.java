package com.example.liangge.indiana.exception;

import android.content.Context;

import com.example.liangge.indiana.comm.FileOperateUtils;
import com.example.liangge.indiana.comm.LogUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Created by baoxing on 2015/12/11.
 */
public class LogTrackManager {

    private static final String LOG_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final String TAG = LogTrackManager.class.getSimpleName();

    private static DateFormat mDateFormat;

    /** Log文件大小上限 */
    private static final long L_LIMIT_FILE_SIZE = 1024 * 1024 * 8;  // 8M

    static {
        mDateFormat = new SimpleDateFormat(LOG_DATE_FORMAT);
    }

    public interface LOGFILE_CONSTANT {

        /** 崩溃的log */
        public static final String CRASH_LOG = "crash_log.txt";

        /** LOG 文件夹 */
        public static final String LOG_DIR = "LOG";
    }

    public LogTrackManager(Context context) {
        createLogDir(context);
        createLogFile(getCrashLogFile(context));
    }

    private void createLogFile(File dirPath, String filename) {
        File file = new File(dirPath, filename);
        createLogFile(file);
    }

    private void createLogFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
        }
    }

    private void createLogDir(Context context) {
        File file = new File(context.getExternalFilesDir(null), LOGFILE_CONSTANT.LOG_DIR);
        if (!FileOperateUtils.isFileExisted(file) ) {
            FileOperateUtils.makeDirs(file);
        }
    }

    private File getCrashLogFile(Context context) {
        File dir = new File(context.getExternalFilesDir(null), LOGFILE_CONSTANT.LOG_DIR);
        File file = new File(dir, LOGFILE_CONSTANT.CRASH_LOG);
        return file;
    }

    /**
     * 记录崩溃的log
     * @param context
     * @param logMsg
     */
    public void recordCrashLog(Context context, String logMsg)  {
        FileOperateUtils.appendFileContent(getCrashLogFile(context), logMsg);
    }

    /**
     * 处理log资源
     * @param context
     */
    public void onDestroy(Context context) {
        //大于一定的大小，就删除
        LogUtils.e(TAG, "onDestroy()");
        String date = mDateFormat.format(new Date());
        String exitInfo = String.format("\n- - - - exit app [%s]- - - - - ", date);
        FileOperateUtils.appendFileContent(getCrashLogFile(context), exitInfo);

        long lCrashLogSize = FileOperateUtils.getFileSize(getCrashLogFile(context));

        if (lCrashLogSize >= L_LIMIT_FILE_SIZE) {
            FileOperateUtils.deleteFile(getCrashLogFile(context));
        }

    }

    /**
     * 启动app时
     * @param context
     */
    public void onCreate(Context context) {
        //add some flag info
        LogUtils.e(TAG, "onCreate()");

    }



}
