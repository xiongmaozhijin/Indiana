package com.example.liangge.indiana.comm.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Post发送JSON数据请求Request
 * Created by baoxing on 2015/12/24.
 */
public class JsonStringRequest extends StringRequest {

    private String mStrRequestJsonData;

    public JsonStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String jsonData) {
        super(method, url, listener, errorListener);
        this.mStrRequestJsonData = jsonData;
    }

    public JsonStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String jsonData) {
        super(url, listener, errorListener);
        this.mStrRequestJsonData = jsonData;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mStrRequestJsonData.getBytes();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        return headers;
    }



}
