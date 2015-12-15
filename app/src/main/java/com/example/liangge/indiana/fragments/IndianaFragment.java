package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.IndianaBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.ui.widget.BannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndianaFragment extends BaseFragment {

    private static final String TAG = IndianaFragment.class.getSimpleName();

    /** 图片轮播 */
    private BannerView mBannerView;

    private IndianaBiz mIndianaBiz;

    private UIReceive mUIReceive;

    public IndianaFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_indiana, container, false);

        initWidget(view);


        return view;
    }

    @Override
    protected void registerUIBroadCast() {
        LogUtils.i(TAG, "registerUIBroadCast()");

        if (mUIReceive == null) {
            mUIReceive = new UIReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            getActivity().registerReceiver(mUIReceive, filter);
        }
    }

    @Override
    protected void unRegisterUIBroadCast() {
        LogUtils.i(TAG, "unRegisterUIBroadCast()");

        if (mUIReceive != null) {
            getActivity().unregisterReceiver(mUIReceive);
            mUIReceive = null;
        }
    }

    private void initWidget(View view) {
        mBannerView = (BannerView) view.findViewById(R.id.main_banner_view);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
        initInfo();
    }

    private void initManager() {
        mIndianaBiz = IndianaBiz.getInstance(getActivity());
    }


    private void initInfo() {
        mIndianaBiz.init();
    }


    /**
     * 消息驱动
     */
    private class UIReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
           handleUIMessage(intent);
        }

        private void handleUIMessage(Intent intent) {
            LogUtils.i(TAG, "handleUIMessage().");
            if (intent != null) {
                String strBCAction = intent.getAction();
                if (strBCAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) {
                    final String strUIAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                    LogUtils.e(TAG, "receive ui message action=%s", strUIAction);
                    if (strUIAction != null) {
                        if ( (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_FAIL) ) || (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_SUCCESS) ) ||
                                (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER) ) )  {
                            handleUIBanner(strUIAction);

                        } else if ( (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_PRODUCT_DATA_FAIL)) || (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCESS)) ||
                                (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_PRODUCT_DATA))) {
                            handleUIProduct(strUIAction);

                        } else if (strUIAction.equals(UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK)) {
                            handleCommResponse();
                        }


                    }

                }
            }
        }
    }

    /**
     * 处理公共问题，比如没有联网
     */
    private void handleCommResponse() {

    }

    /**
     * 处理产品数据
     * @param strUIAction
     */
    private void handleUIProduct(String strUIAction) {

    }

    /**
     * 处理图片轮播ui驱动响应
     * @param strUIAction
     */
    private void handleUIBanner(String strUIAction) {
        LogUtils.i(TAG, "handleUIBanner()");

        if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_FAIL)) {

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER)) {

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_SUCCESS)) {
            mBannerView.setDatasAndNotify(mIndianaBiz.getListBanners());

        }
    }



    @Override
    public void onStop() {
        super.onStop();
        mIndianaBiz.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIndianaBiz.onDestroy();

    }
}
