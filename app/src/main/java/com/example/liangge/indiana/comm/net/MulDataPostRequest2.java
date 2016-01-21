package com.example.liangge.indiana.comm.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baoxing on 2016/1/21.
 */
public class MulDataPostRequest2 extends StringRequest{

    private MultipartEntity mMultipartEntity = new MultipartEntity();

    public MulDataPostRequest2(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mMultipartEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");

        }

        return bos.toByteArray();
    }

/*
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;boundary="+getBounder() );

        return headers;
    }

    private String getBounder() {
        mMultipartEntity.getContentType().getValue();
        return "";
    }
*/

    @Override
    public String getBodyContentType() {
        org.apache.http.Header contentType = mMultipartEntity.getContentType();

        return contentType.getValue();
//        return super.getBodyContentType();

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }


        return headers;
    }


    public void buildMultiPartEntity(Map<String, String> attrs, List<String> mFilePaths) {
        if (attrs != null ) {
            for (Map.Entry<String, String> item : attrs.entrySet()) {
                try {
                    mMultipartEntity.addPart(item.getKey(), new StringBody(item.getValue(), Charset.forName("UTF-8")) );

                } catch (UnsupportedEncodingException e) {

                }
            }
        }


        if (mFilePaths != null) {
            for (String item : mFilePaths) {
                mMultipartEntity.addPart("imgFile[]", new FileBody(new File(item)));
            }
        }
    }









}
