package com.example.liangge.indiana.comm.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.liangge.indiana.comm.FileOperateUtils;
import com.example.liangge.indiana.comm.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baoxing on 2016/1/20.
 */
public class MulDataPostRequest extends StringRequest {

    private static final String BOUNDER = "-----------------------------7db372eb000e2";

    private String mMulDatas;

    public MulDataPostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String mulDatas) {
        super(method, url, listener, errorListener);
        this.mMulDatas = mulDatas;
    }

    public MulDataPostRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String mulDatas) {
        super(url, listener, errorListener);
        this.mMulDatas = mulDatas;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mMulDatas.getBytes();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;boundary="+getBounder() );
        return headers;
    }



    private  String getBounder() {
        return BOUNDER;
    }

    protected static String getItemBounderBegin() {
        return "\r\n" + "--" + BOUNDER + "\r\n";
    }

    protected static  String getItemBoundEnd() {
        return "\r\n" + "--" + BOUNDER + "--" + "\r\n\r\n";
    }

    protected static String getFormItem(String name, String value) {
        String itemStr = String.format("%sContent-Disposition: form-data; name=\"%s\"\r\n\r\n%s",
                    getItemBounderBegin(), name, value);

//        LogUtils.w("Post", itemStr);
        return itemStr;
    }

    protected static String getFormFile(String filePath, String nameAttr, String filename) {

        return getFormFile(FileOperateUtils.file2byte(filePath), nameAttr, filename);
    }

    protected static String getFormFile(byte[] fileContent, String nameAttr, String filename) {
        String octetData = new String(fileContent);
        String itemStr = String.format("%sContent-Disposition:form-data;name=\"%s\"; filename=\"%s\"\r\nContent-Type:application/octet-stream\r\n\r\n%s",
                                    getItemBounderBegin(), nameAttr, filename, octetData);

//        LogUtils.w("Post", itemStr);
        return itemStr;
    }

}
