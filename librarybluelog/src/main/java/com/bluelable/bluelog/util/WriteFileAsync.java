package com.bluelable.bluelog.util;

import android.os.AsyncTask;

import com.bluelable.bluelog.bean.BaseEventList;
import com.bluelable.bluelog.bean.Configuration;
import com.bluelable.bluelog.model.IOLayer;
import com.bluelable.bluelog.wrapper.JsonWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by xinspace on 11/20/15.
 */
public class WriteFileAsync extends AsyncTask<Object, Integer, String> {

    /*
    * 常量
    * */
    //调试标签
    private static final String TAG = WriteFileAsync.class.getSimpleName();
    //调试开关
    private static final boolean DEBUG = false;

    /**
     * @author xinspace
     * created at 11/20/15 17:18
     * 数据
     */
    //写文件监听器
    private IOLayer.WriteListener mListener;
    //事件队列
    private BaseEventList mEventList;

    /**
     * @author xinspace
     * created at 11/20/15 17:15
     * 构造方法
     */
    public WriteFileAsync(BaseEventList eventList, IOLayer.WriteListener listener) {
        mListener = listener;
        mEventList = eventList;
    }

    @Override
    protected String doInBackground(Object[] params) {

        try {
            String filePath = Configuration.get().getLogFilePath();
            File file = new File(filePath);
            BufferedWriter writer = null;
            FileOutputStream out = new FileOutputStream(file, true);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            if(mEventList.getEventList() == null || mEventList.getEventCount() <= 0) {
                return filePath;
            }
            JsonWrapper wrapper = LogModuleInstance.getInstance().getJsonWrapper();
            String eventListString = wrapper.toJson(mEventList);
            writer.write(eventListString);
            writer.write("\n");
            writer.flush();
            writer.close();
            return filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String message) {
        if(message != null) {
            if (mListener != null) {
                mListener.writeSuccess(mEventList.getListId(), message);
            }
        } else {
            if (mListener != null) {
                mListener.writeError(mEventList.getListId());
            }
        }
    }
}
