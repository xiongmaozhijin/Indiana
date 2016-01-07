package com.example.liangge.indiana.ui;

import android.view.View;

/**
 * 只处理消息接收
 * Created by baoxing on 2016/1/7.
 */
public abstract class SimpleAdapterBaseActivity2 extends BaseActivity2 {

    @Override
    protected void onBtnReload() {

    }

    @Override
    protected View getScrollViewWrapper() {
        return null;
    }

    @Override
    protected void onRefreshLoadData() {

    }

    @Override
    protected View getLayoutViewWrapper() {
        return null;
    }
}
