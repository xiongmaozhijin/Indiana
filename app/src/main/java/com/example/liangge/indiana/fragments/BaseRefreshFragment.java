package com.example.liangge.indiana.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
 * 封装了加载更多，上拉刷新处理
 * Created by baoxing on 2016/1/5.
 */
public abstract class BaseRefreshFragment extends BaseNetUIFragment {

    /** 下拉刷新 */
    private PtrClassicFrameLayout mPtrFrame;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRefreshView2();
    }

    private void initRefreshView2() {
        View view = getLayoutViewWrapper();
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);

        if (mPtrFrame == null) {
            return;
        }

        // header
        final MaterialHeader header = new MaterialHeader(getContext());
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

    /**
     * 加载更多提示
     * @param viewLoadMoreHintWrapper
     */
    protected void handleUILoadMore(View viewLoadMoreHintWrapper, int loadMoreState, boolean isEmptyData) {
        LogUtils.w(getDeugTag(), "handleUILoadMore(). loadMoreState=%d, isEmpty=%b", loadMoreState, isEmptyData);
        if (loadMoreState == Constant.Comm.LOAD_MORE_START) {
            viewLoadMoreHintWrapper.setVisibility(View.VISIBLE);
            viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_loading_wrapper).setVisibility(View.VISIBLE);
            viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_wrapper).setVisibility(View.GONE);

        } else if (loadMoreState == Constant.Comm.LOAD_MORE_FAILED) {
            viewLoadMoreHintWrapper.setVisibility(View.VISIBLE);
            TextView txvHint = (TextView) viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_text);
            String strHint = getResources().getString(R.string.comm_load_more_hint_fail);
            viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_loading_wrapper).setVisibility(View.GONE);
            viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_wrapper).setVisibility(View.VISIBLE);
            txvHint.setText(strHint);

        } else if (loadMoreState == Constant.Comm.LOAD_MORE_SUCCESS) {
            if (isEmptyData) {
                viewLoadMoreHintWrapper.setVisibility(View.VISIBLE);
                TextView txvHint = (TextView) viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_text);
                String strHint = getResources().getString(R.string.comm_load_more_hint_all_complete);
                viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_loading_wrapper).setVisibility(View.GONE);
                viewLoadMoreHintWrapper.findViewById(R.id.load_more_hint_wrapper).setVisibility(View.VISIBLE);
                txvHint.setText(strHint);

            } else {
                viewLoadMoreHintWrapper.setVisibility(View.GONE);

            }



        }


    }



    protected void onAutoRefreshUIShow() {
        if (mPtrFrame != null) {
            mPtrFrame.autoRefresh();
        }
    }

    protected void disableWhenHorizontalMove() {
        mPtrFrame.disableWhenHorizontalMove(true);
    }



    protected abstract View getScrollViewWrapper();

    protected abstract void onRefreshLoadData();

    protected abstract View getLayoutViewWrapper();

}
