package com.example.liangge.indiana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.ui.widget.ExScrollView;

/**
 * 晒单
 * Created by baoxing on 2016/1/20.
 */
public class ShareOrdersFragment extends BaseRefreshFragment {

    private static final String TAG = ShareOrdersFragment.class.getSimpleName();

    private View mViewLayout;

    private ExScrollView mExScrollView;

    private ListView mListView;


    private View mViewNotWrapper;

    private View mViewAllContent;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_shareorders, null);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mViewLayout = view;
        mExScrollView = (ExScrollView) view.findViewById(R.id.scrollview);
        mListView = (ListView) view.findViewById(R.id.listview);
        mViewNotWrapper = view.findViewById(R.id.not_net_wrapper);
        mViewAllContent = view.findViewById(R.id.all_content_wrapper);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected View getScrollViewWrapper() {
        return mExScrollView;
    }

    @Override
    protected void onRefreshLoadData() {

    }

    @Override
    protected View getLayoutViewWrapper() {
        return mViewLayout;
    }

    @Override
    protected void onBtnReload() {

    }

    @Override
    protected void registerUIBroadCast() {

    }

    @Override
    protected void unRegisterUIBroadCast() {

    }

    @Override
    protected String getDeugTag() {
        return TAG;
    }
}
