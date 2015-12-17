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
import android.widget.AdapterView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.LatestProductGridViewAdapter;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.LastestBingoEntity;
import com.example.liangge.indiana.ui.widget.ExGridView;
import com.example.liangge.indiana.ui.widget.ExScrollView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestAnnouncementFragment extends BaseFragment {


    private static final String TAG = LatestAnnouncementFragment.class.getSimpleName();

    /** 没有网络时的布局包裹 */
    private View mViewNotNetWorkWrpper;

    /** 内容布局包裹 */
    private View mViewContentWrapper;

    private ExScrollView mExScrollView;

    private ExGridView mExGridView;

    private LatestProductGridViewAdapter mAdapter;


    private UIMessageReceive mUIMessageReceive;


    public LatestAnnouncementFragment() {
        // Required empty public constructor
    }


    /**
     * UI消息接收
     */
    private class UIMessageReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            handleUIMessage(intent);

        }


    }

    /**
     * 处理UI Message
     * @param intent
     */
    private void handleUIMessage(Intent intent) {
        if (intent != null) {
            String intentAction = intent.getAction();
            if (intentAction != null) {
                if (intentAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) {
                    String strUIAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                    if (strUIAction != null) {
                        boolean bIsResponseLoadingProduct =  strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_FAILED)
                                                                || strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCEED)
                                                                || strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOADING_PRODUCT_DATA);

                        boolean bIsResponseNetConn = strUIAction.equals(UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK);

                        if (bIsResponseLoadingProduct) {
                            handleUIProductData(strUIAction);

                        } else if (bIsResponseNetConn) {
                            handleUINetConn(strUIAction);

                        }

                    }

                }


            }
        }


    }

    /**
     * 处理网络状态连接响应
     * @param strUIAction
     */
    private void handleUINetConn(String strUIAction) {

    }

    /**
     * 处理产品数据加载请求响应
     * @param strUIAction
     */
    private void handleUIProductData(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOADING_PRODUCT_DATA)) {


        } else if (strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_FAILED)) {


        } else if (strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCEED)){

        }

    }












    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lastest_announcement, container, false);
        initView(view);

        return view;
    }

    /**
     * 实例化view组件
     * @param view
     */
    private void initView(View view) {
        mViewNotNetWorkWrpper = view.findViewById(R.id.f_latest_not_network_wrapper);
        mViewContentWrapper = view.findViewById(R.id.f_latest_content_wrapper);

        mExScrollView = (ExScrollView) view.findViewById(R.id.f_latest_content_scrollview);

        mExGridView = (ExGridView) view.findViewById(R.id.f_latest_gridview_product_data);
        mAdapter = new LatestProductGridViewAdapter(getActivity());
        mExGridView.setAdapter(mAdapter);


        mExGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.w(TAG, "onItemClick(). position=%d, info=", position, parent.getAdapter().getItem(position).toString());

            }
        });

        mAdapter.setOnTimeUpListener(new LatestProductGridViewAdapter.OnTimeUpListener() {
            @Override
            public void onTimeUp(LastestBingoEntity lastestBingoEntity) {
                LogUtils.w(TAG, "onTimeUp(). itemInfo=%s", lastestBingoEntity.toString());

            }
        });

        mExScrollView.setOnScrollDoneListener(new ExScrollView.OnScrollDoneListener() {
            @Override
            public void onScrollTop() {
                LogUtils.w(TAG, "onScrollTop()");
            }

            @Override
            public void onScrollBottom() {
                LogUtils.w(TAG, "onScrollBottom()");
            }
        });

        //TODO

    }

    @Override
    protected void registerUIBroadCast() {
        if (mUIMessageReceive == null) {
            mUIMessageReceive = new UIMessageReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            getActivity().registerReceiver(mUIMessageReceive, filter);
        }

    }

    @Override
    protected void unRegisterUIBroadCast() {
        if (mUIMessageReceive != null) {
            getActivity().unregisterReceiver(mUIMessageReceive);
            mUIMessageReceive = null;
        }

    }


    @Override
    protected String getDeugTag() {
        return TAG;
    }

}
