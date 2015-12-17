package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.indiana.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestAnnouncementFragment extends BaseFragment {


    private static final String TAG = LatestAnnouncementFragment.class.getSimpleName();

    public LatestAnnouncementFragment() {
        // Required empty public constructor
    }


    /**
     * UI消息接收
     */
    private class UIMessageReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


        }


    }








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lastest_announcement, container, false);
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
