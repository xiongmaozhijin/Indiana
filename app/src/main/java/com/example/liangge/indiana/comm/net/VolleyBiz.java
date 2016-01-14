package com.example.liangge.indiana.comm.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.liangge.indiana.comm.FileOperateUtils;

import java.io.File;

/**
 * Created by baoxing on 2015/12/24.
 */
public class VolleyBiz {

    private static RequestQueue mRequestQueue;

    private Context mContext;

    private static VolleyBiz mInstance;

    private VolleyBiz(Context context) {
        this.mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);

    }

    public static void init(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyBiz(context);

        }

    }

    public static VolleyBiz getInstance() {
        return mInstance;
    }


    public void addRequest(Request request) {
        mRequestQueue.add(request);
    }

    public void cancelAll(String tag) {
        mRequestQueue.cancelAll(tag);
    }


    /**
     * 返回缓存目录
     * @return
     */
    public File getCacheFileDir() {
        File cacheDir = new File(mContext.getCacheDir(), "volley");
        return cacheDir;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        mRequestQueue.getCache().clear();
    }

}
