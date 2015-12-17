package com.example.liangge.indiana.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtils.w(getDeugTag(), "onCreate()");

        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.w(getDeugTag(), "onActivityCreate()");

        registerUIBroadCast();
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.w(getDeugTag(), "onStop()");

        unRegisterUIBroadCast();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtils.w(getDeugTag(), "onHiddenChanged().hidden=%b", hidden);

        super.onHiddenChanged(hidden);
        if (hidden) {
            unRegisterUIBroadCast();
        } else {
            registerUIBroadCast();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.w(getDeugTag(), "onAttach()");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.w(getDeugTag(), "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.w(getDeugTag(), "onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.w(getDeugTag(), "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.w(getDeugTag(), "onDetach()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.w(getDeugTag(), "onDestoryView()");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.w(getDeugTag(), "onStart()");
    }

    /**
     * 注册UI广播
     */
    protected abstract void registerUIBroadCast();

    /**
     * 解除注册UI广播
     */
    protected abstract void unRegisterUIBroadCast();


    protected abstract String getDeugTag();


    /**
     * 第一次进入这个界面
     */
    public void onFirstEnter() {
        LogUtils.w(getDeugTag(), "onFirstEnter()");
        registerUIBroadCast();
    }

    /**
     * 再次进入这个界面
     */
    public void onEnter() {
        LogUtils.w(getDeugTag(), "onEnter()");

    }

    /**
     * 离开这个界面
     */
    public void onLeft() {
        LogUtils.w(getDeugTag(), "onLeft()");
    }


}
