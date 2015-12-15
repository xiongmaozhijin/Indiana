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
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.ui.widget.BannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndianaFragment extends BaseFragment {

    /** 图片轮播 */
    private BannerView mBannerView;

    private IndianaBiz mIndianaBiz;

    private UIReceive mUIReceive;

    public IndianaFragment() {
        // Required empty public constructor
        mIndianaBiz = IndianaBiz.getInstance(getActivity());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_indiana, container, false);

        initWidget(view);
        registerUIBroadCast();
        initInfo();
        return view;
    }

    private void initInfo() {
        mIndianaBiz.init();
    }

    private void registerUIBroadCast() {
        if (mUIReceive == null) {
            mUIReceive = new UIReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            getActivity().registerReceiver(mUIReceive, filter);
        }
    }

    private void unRegisterUIBroadCast() {
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
    }

    /**
     * 消息驱动
     */
    private class UIReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        registerUIBroadCast();
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterUIBroadCast();
        mIndianaBiz.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIndianaBiz.onDestroy();

    }
}
