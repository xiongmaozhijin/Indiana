package com.example.liangge.indiana.fragments;

import android.view.View;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;

/**
 * 封装了网络状况的处理
 * Created by baoxing on 2016/1/6.
 */
public abstract class BaseNetUIFragment extends BaseFragment{


    /** 重新加载 */
    protected abstract void onBtnReload();

    /**
     * 处理加载时的网络状况
     * @param netState
     */
    protected void handleNetUI(int netState, View netViewWrapper, View allContentViewWrapper) {
        LogUtils.w(getDeugTag(), "netState=%d", netState);
        View view = netViewWrapper;

        View notNetHintWrapper = view.findViewById(R.id.not_network_hint);
        View loadingHintWrapper = view.findViewById(R.id.comm_loading_icon);

        if (netState== Constant.Comm.NET_FAILED_NO_NET) {
            if (allContentViewWrapper != null) {
                allContentViewWrapper.setVisibility(View.GONE);
            }

            if (view != null) {
                view.setVisibility(View.VISIBLE);
                notNetHintWrapper.setVisibility(View.VISIBLE);
                loadingHintWrapper.setVisibility(View.GONE);
                view.findViewById(R.id.comm_reload).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBtnReload();
                    }
                });
            }



        } else if (netState== Constant.Comm.NET_LOADING) {
            if (allContentViewWrapper != null) {
                allContentViewWrapper.setVisibility(View.GONE);
            }

            if (view != null) {
                view.setVisibility(View.VISIBLE);
                notNetHintWrapper.setVisibility(View.GONE);
                loadingHintWrapper.setVisibility(View.VISIBLE);
            }



        } else if (netState== Constant.Comm.NET_SUCCESS) {

            if (view != null) {
                view.setVisibility(View.GONE);
            }

            if (allContentViewWrapper != null) {
                allContentViewWrapper.setVisibility(View.VISIBLE);
            }


        }


    }


}
