package com.bluelable.bluelog.model;

import com.bluelable.bluelog.bean.BaseEventList;

import org.json.JSONException;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by xinspace on 11/20/15.
 * 日志模块写入本地模块
 */
public interface IOLayer {

    /**
     * @author xinspace
     * created at 11/20/15 14:04
     * 将事件列表写入日志文件
     * @param eventList 事件列表对象
     * @param listener 写文件监听器
     * @throws IOException JSONException
     */
    void writeLogs(BaseEventList eventList, WriteListener listener) throws IOException, JSONException;

    /**
     * @author xinspace
     *         created at 01/09/16 17:25
     *         写入事件的监听器
     */
    interface WriteListener {
        /**
         * @author xinspace
         * created at 01/09/16 17:25
         * 写入完成回调
         * @param listId 写入的队列ID
         * @param filePath 写入的文件路径
         */
        void writeSuccess(UUID listId, String filePath);

        /**
         * @author xinspace
         * created at 01/09/16 17:29
         * 写入失败时回调
         * @param listId 写入失败的队列ID
         */
        void writeError(UUID listId);
    }
}
