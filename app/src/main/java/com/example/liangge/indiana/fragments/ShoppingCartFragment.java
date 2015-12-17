package com.example.liangge.indiana.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangge.indiana.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends BaseFragment {


    private static final String TAG = ShoppingCartFragment.class.getSimpleName();


    public ShoppingCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
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

    @Override
    public void onFirstEnter() {
        super.onFirstEnter();
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onLeft() {
        super.onLeft();
    }


}
