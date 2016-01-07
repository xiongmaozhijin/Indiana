package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.LogUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 封装了下拉刷新，网络状况
 */
public abstract class BaseUIActivity extends Activity {

    private static final String TAG = BaseUIActivity.class.getSimpleName();

    /** 下拉刷新 */
    private PtrClassicFrameLayout mPtrFrame;


    private boolean bIsEnter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initRefreshView2();
    }

    private void initRefreshView2() {
        LogUtils.w(TAG, "initRefreshView2()");
        View view = getLayoutViewWrapper();

        if (view == null) {
            return;
        }

        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);

        if (mPtrFrame == null) {
            LogUtils.e(TAG, "mPtrFrame = null");
            return;
        }

        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
//        mPtrFrame.setPinContent(true);

//        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, getScrollViewWrapper(), header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //请求刷新
                onRefreshLoadData();

            }
        });

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);


    }

    /**
     * 取消下拉的UI提示
     */
    protected void dismissRefreshUI() {
        if (this.mPtrFrame != null) {
            if (mPtrFrame.isRefreshing()) {
                this.mPtrFrame.refreshComplete();
            }

        }

    }


    protected void onAutoRefreshUIShow() {
        if (mPtrFrame != null) {
            mPtrFrame.autoRefresh();
        }
    }



    /**
     * 处理加载时的网络状况
     * @param netState
     */
    protected void handleNetUI(int netState, View netViewWrapper, View allContentViewWrapper) {
        View view = netViewWrapper;

        View notNetHintWrapper = view.findViewById(R.id.not_network_hint);
        View loadingHintWrapper = view.findViewById(R.id.comm_loading_icon);

        if (netState== Constant.Comm.NET_FAILED_NO_NET) {
            allContentViewWrapper.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            notNetHintWrapper.setVisibility(View.VISIBLE);
            loadingHintWrapper.setVisibility(View.GONE);
            view.findViewById(R.id.comm_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnReload();
                }
            });

        } else if (netState== Constant.Comm.NET_LOADING) {
            allContentViewWrapper.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            notNetHintWrapper.setVisibility(View.GONE);
            loadingHintWrapper.setVisibility(View.VISIBLE);

        } else if (netState== Constant.Comm.NET_SUCCESS) {
            view.setVisibility(View.GONE);
            allContentViewWrapper.setVisibility(View.VISIBLE);

        }


    }



    /** 重新加载 */
    protected abstract void onBtnReload();


    /**
     * ScrollView Wrapper
     * @return
     */
    protected abstract View getScrollViewWrapper();

    /**
     * 开始加载数据
     */
    protected abstract void onRefreshLoadData();

    /**
     * 整个View Wrapper，如果不想要下拉刷新，传入null
     * @return
     */
    protected abstract View getLayoutViewWrapper();





}
