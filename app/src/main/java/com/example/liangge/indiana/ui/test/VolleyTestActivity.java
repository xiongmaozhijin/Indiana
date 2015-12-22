package com.example.liangge.indiana.ui.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;

public class VolleyTestActivity extends Activity {

    private static final String TAG = VolleyTestActivity.class.getSimpleName();

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);

        initRes();
    }

    private void initRes() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }



    private void strRequest() {
        LogUtils.w(TAG, "strRequest()");
//        String url = "www.baidu.com";
//        StringRequest strRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                LogUtils.w(TAG, "s=%s", s);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                LogUtils.e(TAG, "error=%s", volleyError.getLocalizedMessage());
//            }
//        });
//
//        mRequestQueue.add(strRequest);

        StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.w(TAG, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.e(TAG, error.getMessage(), error);
            }
        });

        mRequestQueue.add(stringRequest);
    }



    public void onStrRequest(View view) {
        strRequest();
    }



}
