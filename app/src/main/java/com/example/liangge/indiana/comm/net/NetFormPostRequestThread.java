package com.example.liangge.indiana.comm.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.liangge.indiana.comm.LogUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @deprecated
 * 表单上传，包含文件
 * Created by baoxing on 2016/1/20.
 */
public abstract class NetFormPostRequestThread extends Thread{


    private String REQUEST_TAG;

    private VolleyBiz mVolleyBiz;

    private Map<String, String> mAttrs;
    private List<String> mFilePaths;

    /** 是否正在工作 */
    private boolean mIsWorking = false;

    public NetFormPostRequestThread(Map<String, String> attrs, List<String> filePath) {
        REQUEST_TAG = getRequestTag();
        mVolleyBiz = VolleyBiz.getInstance();

        mAttrs = attrs;
        mFilePaths = filePath;
    }

    @Override
    public void run() {
        super.run();
        preDoDump();
        notifyStart();
        String url = getWebServiceAPI();
        String mulData = getMulBody();

        MulDataPostRequest request = new MulDataPostRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                onResponseListener(s);
                notifySuccess();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                notifyFail();
                onResponseErrorListener(volleyError);

            }
        }, mulData);

        request.setTag(REQUEST_TAG);
        mVolleyBiz.addRequest(request);
    }

    protected String getMulBody() {
        StringBuilder sb = new StringBuilder("");
        Set<Map.Entry<String, String>> entries = mAttrs.entrySet();
        for (Map.Entry<String, String> item : entries) {
            sb.append(MulDataPostRequest.getFormItem(item.getKey(), item.getValue()) );
        }

//        LogUtils.i(REQUEST_TAG, "mulBody1=%s", sb.toString());

        for (int i=0, len=mFilePaths.size(); i<len; i++) {
           sb.append(MulDataPostRequest.getFormFile(mFilePaths.get(i), "imgFile[]", "shareOrderImg"+i+".jpg" ) );
        }

        sb.append(MulDataPostRequest.getItemBoundEnd());

//        LogUtils.w(REQUEST_TAG, "mulBody2=%s", sb.toString());
        return sb.toString();
    }



    protected void setImgPaths(List<String> mListPaths) {
        debugFilePath(mListPaths);
        mFilePaths = mListPaths;
    }

    private void debugFilePath(List<String> filePaths) {
        for (String item : filePaths) {
            LogUtils.i(REQUEST_TAG, item);
        }
    }

    protected List<String> getFilePath() {
        return mFilePaths;
    }

    /**
     * 预先的处理工作
     */
    protected void preDoDump() {

    }


    public void cancelAll() {
        mVolleyBiz.cancelAll(REQUEST_TAG);
    }

    public boolean isWorking() {
        return this.mIsWorking;
    }

    protected void notifyStart() {
        this.mIsWorking = true;
    }

    protected void notifySuccess() {
        this.mIsWorking = false;
    }

    protected void notifyFail() {
        this.mIsWorking = false;
    }

    protected  abstract void onResponseListener(String s);

    protected abstract void onResponseErrorListener(VolleyError volleyError);

    protected abstract String getRequestTag();

    protected abstract String getWebServiceAPI();


}
