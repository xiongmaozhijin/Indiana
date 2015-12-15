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
public class LastestAnnouncementFragment extends BaseFragment {


    public LastestAnnouncementFragment() {
        // Required empty public constructor
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


}
