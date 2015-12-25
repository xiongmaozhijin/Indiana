package com.example.liangge.indiana.comm.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 *
 * Created by baoxing on 2015/12/25.
 */
public abstract class NetRequestThread extends Thread{

    private String REQUEST_TAG;

    private VolleyBiz mVolleyBiz;

    private boolean mIsLoadMore = false;

    public NetRequestThread() {
        REQUEST_TAG = getRequestTag();
        mVolleyBiz = VolleyBiz.getInstance();
        mIsLoadMore  = isLoadMore();

    }

    @Override
    public void run() {
        super.run();
        notifyStart();
        String url = getWebServiceAPI();
        String jsonData = getJsonBody();

        JsonStringRequest request = new JsonStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
        }, jsonData);

        request.setTag(REQUEST_TAG);
        mVolleyBiz.addRequest(request);
    }


    public void cancelAll() {

    }

    private void notifyStart() {

    }

    private void notifySuccess() {

    }

    private void notifyFail() {

    }

    protected abstract String getJsonBody();

    protected  abstract void onResponseListener(String s);

    protected abstract void onResponseErrorListener(VolleyError volleyError);

    protected abstract String getRequestTag();

    protected abstract boolean isLoadMore();

    protected abstract int startPage();

    protected abstract String getWebServiceAPI();

}
