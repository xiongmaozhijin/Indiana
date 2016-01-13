package com.bluelable.bluelog.wrapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xinspace on 01/13/16.
 * 网络封装接口
 */
public interface NetworkWrapper {

    /**
     * @author xinspace
     * created at 01/13/16 14:57
     * 上传JSON数据
     * @param url 上传的URL
     * @param postData post数据
     * @param listener 网络回复监听器
     */
    void putJson(String url, String postData, NetResponse listener);

    /**
     * @author xinspace
     * created at 01/13/16 15:00
     * 上传文件
     * @param url 上传URL
     * @param filePath 文件的绝对路径
     * @param listener 上传文件监听器
     */
    void uploadFile(String url, String filePath, OnUploadListener listener);

    /**
     * @author xinspace
     * created at 11/20/15 14:05
     * 单次网络请求，put方法传递数据和接受数据
     * @param url 请求URL
     * @param postData 请求时post的数据
     * @param listener 回复监听器
     */
    void requestOnce(String url, String postData, NetResponse listener) throws JSONException;

    /**
     * @author xinspace
     *         created at 11/20/15 14:08
     *         网络回复接口
     */
    interface NetResponse {
        /**
         * @author xinspace
         * created at 11/20/15 14:08
         * 成功收到服务器回复时调用
         * @param response 回复的JSON
         */
        void netResponse(JSONObject response);

        /**
         * @author xinspace
         * created at 11/20/15 14:09
         * 服务器回复出错时调用
         * @param e 错误
         */
        void netError(Exception e);
    }

    /**
     * @author xinspace
     *         created at 01/13/16 15:13
     *         上传监听器.
     */
    public interface OnUploadListener {
        /**
         * 当上传成功后，调用这个方法.
         *
         * @param fileName 哪个文件上传成功了，绝对路径+文件名.
         */
        void onUploadFinished(String fileName);

        /**
         * 上传过程中出错时，调用这个方法.
         *
         * @param e 一场对象.
         */
        void onUploadErrorOccurrs(Exception e);
    }
}
