package com.example.liangge.indiana.ui.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.AppBiz;

public class ShareTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_test);

    }


    public void onShareWeiXin(View view) {
        shareText("分享微信 test http://121.201.63.76/Uploads/commodity/2015-12-31/1451558035_880046357.jpg", "com.tencent.mm");
    }


    public void onShareWeibo(View view) {
        shareText("分享微博 test http://121.201.63.76/Uploads/commodity/2015-12-31/1451558035_880046357.jpg", "com.sina.weibog3");

    }

    public void onShare(View view) {
        shareText("分享呀分享 http://121.201.63.76/Uploads/commodity/2015-12-31/1451558035_880046357.jpg");
    }

    public void onShare2(View view) {
        AppBiz.getInstance(this).showShareDialog(this, "分享呀呀分享");
    }


    public void shareText(String shareContent) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    public void shareText(String shareContent, String packageName) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType("text/plain");
        sendIntent.setPackage(packageName);
        startActivity(sendIntent);
    }



}
