package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
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

    private ProgressBar mProgressBar;

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
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        WebSettings webSettings = mWebView.getSettings();
        mWebView.requestFocusFromTouch();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });
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


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();

        } else {
            super.onBackPressed();

        }

    }
}
