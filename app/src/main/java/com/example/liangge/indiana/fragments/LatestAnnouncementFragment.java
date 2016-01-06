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
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.LatestBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.LastestBingoEntity;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;
import com.example.liangge.indiana.ui.widget.ExGridView;
import com.example.liangge.indiana.ui.widget.ExScrollView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestAnnouncementFragment extends BaseRefreshFragment {


    private static final String TAG = LatestAnnouncementFragment.class.getSimpleName();

    /** 加载更多提示 */
    private View mViewLoadMoreHintWrapper;

    private View mViewLayoutWrapper;

    /** 没有网络时的布局包裹 */
    private View mViewNotNetWorkWrpper;

    /** 内容布局包裹 */
    private View mViewContentWrapper;

    private ExScrollView mExScrollView;

    private ExGridView mExGridView;

    private LatestProductGridViewAdapter mAdapter;


    private UIMessageReceive mUIMessageReceive;

    private LatestBiz mLatestBiz;

    private DetailInfoBiz mDetailInfoBiz;




    public LatestAnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onBtnReload() {
        LogUtils.w(TAG, "onBtnReload()");
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
                    LogUtils.e(TAG, "receive ui message. strAction=%s", strUIAction);

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

                        if (strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MSG_UPDATE_BINGO_INFO)) {
                            handleUIUpdateBingoUser();
                        }

                    } //!=null

                }


            }
        }


    }

    /**
     * 更新中奖用户信息（揭晓时间到了）
     */
    private void handleUIUpdateBingoUser() {
        LogUtils.i(TAG, "handleUIUpdateBingoUser()");
        mAdapter.notifyDataSetChanged();
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
            handleLoadStart();

        } else if (strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_FAILED)) {

            handleLoadFailed();

        } else if (strUIAction.equals(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCEED)){
            handleLoadSuccess();

        }

    }

    private void handleLoadStart() {
        int loadMode = mLatestBiz.getCurLoadMode();
        if (loadMode == Constant.Comm.ENTER) {
            handleNetUI(INetState.LOADING, mViewNotNetWorkWrpper, mViewContentWrapper);

        } else if (loadMode == Constant.Comm.LOAD_MORE) {
            handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_START, false);

        } else if (loadMode == Constant.Comm.REFRESH) {

        }
    }

    private void handleLoadFailed() {
        int loadMode = mLatestBiz.getCurLoadMode();
        if (loadMode == Constant.Comm.ENTER) {
            handleNetUI(INetState.FAILED_NO_NET, mViewNotNetWorkWrpper, mViewContentWrapper);


        } else if (loadMode == Constant.Comm.LOAD_MORE) {
            handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_FAILED, false);


        } else if (loadMode == Constant.Comm.REFRESH) {
            dismissRefreshUI();
            LogUtils.toastShortMsg(getActivity(), getResources().getString(R.string.activity_category_net_error));

        }
    }

    private void handleLoadSuccess() {
        int loadMode = mLatestBiz.getCurLoadMode();
        if (loadMode == Constant.Comm.ENTER) {
            handleNetUI(INetState.SUCCESS, mViewNotNetWorkWrpper, mViewContentWrapper);
            mAdapter.setDatasAndNotify(mLatestBiz.getProductsData());

        } else if (loadMode == Constant.Comm.LOAD_MORE) {
            List<LastestBingoEntity> list = mLatestBiz.getProductsData();
            boolean isEmpty = list.size() > 0 ? false : true;
            handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_SUCCESS, isEmpty);
            if (!isEmpty) {
                mAdapter.loadMoreDataAndNotify(list);
            }

        } else if (loadMode == Constant.Comm.REFRESH) {
            dismissRefreshUI();
            mViewNotNetWorkWrpper.setVisibility(View.GONE);
            mViewContentWrapper.setVisibility(View.VISIBLE);
            mAdapter.setDatasAndNotify(mLatestBiz.getProductsData());
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
    }

    @Override
    protected View getScrollViewWrapper() {
        return mExScrollView;
    }

    @Override
    protected void onRefreshLoadData() {
        //TODO 加载数据
        LogUtils.w(TAG, "onRefreshLoadData()");
        handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
        mLatestBiz.loadLastDataInfo(false, Constant.Comm.REFRESH);
    }

    @Override
    protected View getLayoutViewWrapper() {
        return mViewLayoutWrapper;
    }

    private void initManager() {
        mLatestBiz = LatestBiz.getInstance(getActivity());
        mDetailInfoBiz = DetailInfoBiz.getInstance(getActivity());

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
        mViewLayoutWrapper = view;

        mViewLoadMoreHintWrapper = view.findViewById(R.id.load_more_wrapper);
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
                LastestBingoEntity item = (LastestBingoEntity) parent.getAdapter().getItem(position);
                mDetailInfoBiz.setActivityId(item.getActivityId());
                Intent i = new Intent(getActivity(), ProductDetailInfoActivity.class);
                startActivity(i);
            }
        });

        mAdapter.setOnTimeUpListener(new LatestProductGridViewAdapter.OnTimeUpListener() {
            @Override
            public void onTimeUp(LastestBingoEntity lastestBingoEntity) {
                LogUtils.w(TAG, "onTimeUp(). itemInfo=%s", lastestBingoEntity.toString());
                mLatestBiz.onTimeUp(lastestBingoEntity);
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
                onScrollBottomLoadData();
            }
        });

        //TODO

    }

    /**
     * 滚动到底部加载更多
     */
    private void onScrollBottomLoadData() {
        LogUtils.i(TAG, "onScrollBottomLoadData()");
        mLatestBiz.onScrollBottomLoadData();
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

    @Override
    public void onFirstEnter() {
        super.onFirstEnter();
        mLatestBiz.onFirstEnter();

    }

    @Override
    public void onEnter() {
        super.onEnter();
//        mLatestBiz.onEnter();
        mExScrollView.smoothScrollTo(0, 0);
        onAutoRefreshUIShow();
    }

    @Override
    public void onLeft() {
        super.onLeft();
        mLatestBiz.onLeave();
    }

}
