package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.WebViewBiz;

/**
 * 显示传入的url地址
 */
public class WebViewActivity extends Activity {

    /** 标题栏标题 */
    private TextView mTxvTitlebarTitle;

    private WebView mWebView;

    private WebViewBiz mWebViewBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initRes();
    }

    private void initRes() {
        mWebViewBiz = WebViewBiz.getInstance(this);
    }

    private void initView() {
        mTxvTitlebarTitle = (TextView) findViewById(R.id.activity_webview_titlebar_title);
        mWebView = (WebView) findViewById(R.id.activity_webview_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //TODO 这里要做出错的提示处理
        setTitle(mWebViewBiz.getDataInfo().getTitle());
        loadUrl(mWebViewBiz.getDataInfo().getUrl());

    }

    private void setTitle(String title) {
        mTxvTitlebarTitle.setText(title);
    }


    public void onBtnBack(View view) {
        finish();
    }

    private void loadUrl(String url) {
        //TODO 加载提示
        mWebView.loadUrl(url);
    }

}
