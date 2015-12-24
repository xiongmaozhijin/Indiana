package com.example.liangge.indiana.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Json测试请求
 */
public class VolleyPostJsonActivityTest extends AppCompatActivity {

    private static final String TAG = VolleyPostJsonActivityTest.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_post_json_activity_test);
    }


    public void onBtnSendJson(View view) {
        LogUtils.w(TAG, "onBtnSendJson");

        RequestQueue mQueue = Volley.newRequestQueue(this);

        String url = "http://192.168.1.142/index.php/Admin/Api/goodslist";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.w(TAG, "onResponse().s=%s", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.w(TAG, "volleyError=%s", volleyError.getLocalizedMessage());
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return "{\"type\":\"libai\"}".getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                return headers;

            }
        };

        mQueue.add(stringRequest);
    }

}
