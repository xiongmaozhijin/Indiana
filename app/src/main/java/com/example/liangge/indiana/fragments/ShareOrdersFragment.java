package com.example.liangge.indiana.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.ShareOrdersListViewAdapter;
import com.example.liangge.indiana.biz.ShareOrdersBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.ShareOrdersEntity;
import com.example.liangge.indiana.ui.widget.ExListViewScrollDone;
import com.example.liangge.indiana.ui.widget.ExScrollView;

import java.util.List;

/**
 * 晒单
 * Created by baoxing on 2016/1/20.
 */
public class ShareOrdersFragment extends BaseRefreshFragment {

    private static final String TAG = ShareOrdersFragment.class.getSimpleName();

    private View mViewLayout;

    private ExListViewScrollDone mListView;
    private ShareOrdersListViewAdapter mAdapter;

    private View mViewNotWrapper;

    private View mViewAllContentWrapper;

    private View mViewLoadMoreWrapper;

    private UIMessageReceive mUIMessageReceive;


    private ShareOrdersBiz mShareOrdersBiz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_shareorders_new, null);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mViewLayout = view;
        mListView = (ExListViewScrollDone) view.findViewById(R.id.listview);
        mAdapter = new ShareOrdersListViewAdapter(getActivity());

        mViewLoadMoreWrapper = View.inflate(getActivity(), R.layout.layout_load_more_wrapper, null);
//        mViewLoadMoreWrapper.setVisibility(View.GONE);
//        mListView.addFooterView(mViewLoadMoreWrapper, null, false);
        mListView.setAdapter(mAdapter);

        mViewNotWrapper = view.findViewById(R.id.not_net_wrapper);
        mViewAllContentWrapper = view.findViewById(R.id.all_content_wrapper);



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getAdapter().getItem(position);
                if ((object != null) && (object instanceof ShareOrdersEntity)) {
                    ShareOrdersEntity item = (ShareOrdersEntity) parent.getAdapter().getItem(position);

                    mShareOrdersBiz.setDatailEntity(item);
                    mShareOrdersBiz.startShareOrderDetailActivity(getActivity());

                }

            }
        });

        mListView.setOnTouchScrollDoneListener(new ExListViewScrollDone.OnTouchScrollDoneListener() {
            @Override
            public void onTouchScrollBottom() {
                onScrollBottomLoadMore();
            }

            @Override
            public void onTouchScrollTop() {

            }
        });

    }




    /**
     * 滚动到底部加载更多
     */
    private void onScrollBottomLoadMore() {
        LogUtils.i(TAG, "onScrollBottomLoadMore()");
        mShareOrdersBiz.onScrollBottomLoadData();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
    }

    private void initManager() {
        mShareOrdersBiz = ShareOrdersBiz.getInstance(getActivity());

    }

    @Override
    protected View getScrollViewWrapper() {
        return mListView;
    }

    @Override
    protected void onRefreshLoadData() {
        mShareOrdersBiz.onRefreshLoadData();
    }

    @Override
    protected View getLayoutViewWrapper() {
        return mViewLayout;
    }

    @Override
    protected void onBtnReload() {
        mShareOrdersBiz.onReload();
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





    /**
     * UI消息接收
     */
    private class UIMessageReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            handleUIMessage(intent);

        }
    }

    private void handleUIMessage(Intent intent) {
        if (intent != null) {
            String intentAction = intent.getAction();
            if (intentAction!=null && intentAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) {
                String uiAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                LogUtils.i(TAG, "handleUIMessage().uiAction=%s", uiAction);
                if (uiAction.equals(UIMessageConts.ShareOrdersMessage.NET_FAILED) ||
                        uiAction.equals(UIMessageConts.ShareOrdersMessage.NET_START) ||
                        uiAction.equals(UIMessageConts.ShareOrdersMessage.NET_SUCCESS)) {

                    handleUILoadData(uiAction);

                } else if (uiAction.equals(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_FAILED) ||
                        uiAction.equals(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_START) ||
                        uiAction.equals(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_SUCCESS)) {
                    handleUILoadMoreData(uiAction);

                } else if (uiAction.equals(UIMessageConts.ShareOrdersMessage.REFRESH_FAILED) ||
                        uiAction.equals(UIMessageConts.ShareOrdersMessage.REFRESH_SUCCESS)) {
                    handleUIRefresh(uiAction);

                }

            }

        }

    }

    private void handleUIRefresh(String uiAction) {
        LogUtils.i(TAG, "handleUIRefresh()");
        if (uiAction.equals(UIMessageConts.ShareOrdersMessage.REFRESH_FAILED)) {
            dismissRefreshUI();
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
            if (mListView != null) {
                mListView.removeFooterView(mViewLoadMoreWrapper);
            }

            String hint = getActivity().getResources().getString(R.string.refresh_failed);
            LogUtils.toastShortMsg(getActivity(), hint);
        } else if (uiAction.equals(UIMessageConts.ShareOrdersMessage.REFRESH_SUCCESS)) {
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
            if (mListView != null) {
                mListView.removeFooterView(mViewLoadMoreWrapper);
            }
            
            dismissRefreshUI();
            mAdapter.setDataAndNotify(mShareOrdersBiz.getShareOrdersList());
        }
    }

    private void handleUILoadData(String uiAction) {
        LogUtils.i(TAG, "handleUILoadData()");
        handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
        if (mListView != null) {
            mListView.removeFooterView(mViewLoadMoreWrapper);
        }

        if (uiAction.equals(UIMessageConts.ShareOrdersMessage.NET_START)) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNotWrapper, mViewAllContentWrapper);

        } else if (uiAction.equals(UIMessageConts.ShareOrdersMessage.NET_FAILED)) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNotWrapper, mViewAllContentWrapper);

        } else if (uiAction.equals(UIMessageConts.ShareOrdersMessage.NET_SUCCESS)) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNotWrapper, mViewAllContentWrapper);
            mAdapter.setDataAndNotify(mShareOrdersBiz.getShareOrdersList());
        }

    }

    private void handleUILoadMoreData(String uiAction) {
        LogUtils.i(TAG, "handleUILoadMoreData()");
        if (uiAction.equals(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_START)) {
            if (mListView != null) {
                mListView.removeFooterView(mViewLoadMoreWrapper);
                mListView.addFooterView(mViewLoadMoreWrapper);
            }

            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_START, false);

        } else if (uiAction.equals(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_FAILED)) {

            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_FAILED, false);

        } else if (uiAction.equals(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_SUCCESS)) {
            boolean isEmpty = mShareOrdersBiz.getShareOrdersList().size()<=0;
            if (mListView != null) {
               if (isEmpty) {


               } else {
                mListView.removeFooterView(mViewLoadMoreWrapper);

               }

            }


            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, isEmpty);
            mAdapter.loadMoreDataAndNotify(mShareOrdersBiz.getShareOrdersList());
        }

    }


    @Override
    public void onFirstEnter() {
        super.onFirstEnter();
        mShareOrdersBiz.onFirstEnter();
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onLeft() {
        super.onLeft();
    }


    @Override
    public void onDoubleClick() {
        super.onDoubleClick();
        if ( canDoubleClick(mListView, mViewNotWrapper, mViewAllContentWrapper) ) {
            if (mListView != null) {
                if (mListView.getChildCount() >= 1) {
                    mListView.setSelection(0);
                }
            }

            if (!isRefreshing()) {
                onAutoRefreshUIShow();
            }
        }
    }


    @Override
    protected String getDeugTag() {
        return TAG;
    }
}
