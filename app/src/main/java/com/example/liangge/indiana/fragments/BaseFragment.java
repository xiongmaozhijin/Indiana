package com.example.liangge.indiana.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.indiana.R;

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
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerUIBroadCast();
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterUIBroadCast();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            unRegisterUIBroadCast();
        } else {
            registerUIBroadCast();
        }
    }

    /**
     * 注册UI广播
     */
    protected abstract void registerUIBroadCast();

    /**
     * 解除注册UI广播
     */
    protected abstract void unRegisterUIBroadCast();

}
