package com.example.liangge.indiana.fragments;

import android.view.View;

import com.example.liangge.indiana.R;

/**
 * Created by baoxing on 2016/1/6.
 */
public abstract class BaseNetUIFragment extends BaseFragment{


    public interface INetState {
        int NO_NET = -1;
        int LOADING = 1;
        int SUCCESS = 2;
    }



    /** 重新加载 */
    protected abstract void onBtnReload();

    /**
     * 处理加载时的网络状况
     * @param netState
     */
    protected void handleNetUI(int netState, View netViewWrapper, View allContentViewWrapper) {
        View view = netViewWrapper;

        View notNetHintWrapper = view.findViewById(R.id.not_network_hint);
        View loadingHintWrapper = view.findViewById(R.id.comm_loading_icon);

        if (netState==INetState.NO_NET) {
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

        } else if (netState==INetState.LOADING) {
            allContentViewWrapper.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            notNetHintWrapper.setVisibility(View.GONE);
            loadingHintWrapper.setVisibility(View.VISIBLE);

        } else if (netState==INetState.SUCCESS) {
            view.setVisibility(View.GONE);
            allContentViewWrapper.setVisibility(View.VISIBLE);

        }


    }


}
