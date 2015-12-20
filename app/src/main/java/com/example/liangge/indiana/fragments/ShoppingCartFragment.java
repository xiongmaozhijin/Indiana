package com.example.liangge.indiana.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.indiana.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends BaseFragment {


    /** 没有清单时的View */
    private View mViewEmptyWrapper;

    /** 加载或没有网络时显示View */
    private View mViewLoadOrNotNetWrapper;

    /** 购物车内容显示View */
    private View mViewContentWrapper;

    /** 马上夺宝 */
    private Button mBtnGoIndiana;

    /** 确认支付 */
    private Button mBtnCommitPay;

    /** 描述1，共参与？件 */
    private TextView mTxvContentItemDesc1;

    /** 描述2， 共？夺宝币 */
    private TextView mTxvContentItemDesc2;


    private static final String TAG = ShoppingCartFragment.class.getSimpleName();


    public ShoppingCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        initView(view);

        return view;
    }

    /**
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        mViewEmptyWrapper = view.findViewById(R.id.f_shoppingcart_empty_wrapper);
        mViewLoadOrNotNetWrapper = view.findViewById(R.id.f_shopping_not_network_wrapper);
        mViewContentWrapper = view.findViewById(R.id.f_shoppingcart_content_wrapper);

        mBtnGoIndiana = (Button) view.findViewById(R.id.f_shoppingcart_btn_go_indiana);


        mBtnCommitPay = (Button) view.findViewById(R.id.f_shopping_content_item_btn_commit_pay);
        mTxvContentItemDesc1 = (TextView) view.findViewById(R.id.f_shopping_content_item_txvdesc1);
        mTxvContentItemDesc2 = (TextView) view.findViewById(R.id.f_shopping_content_item_txvdesc2);
        //TODO

    }

    @Override
    protected void registerUIBroadCast() {

    }

    @Override
    protected void unRegisterUIBroadCast() {

    }


    private void onThisFragment() {

    }


    @Override
    protected String getDeugTag() {
        return TAG;
    }

    @Override
    public void onFirstEnter() {
        super.onFirstEnter();
        onThisFragment();

    }

    @Override
    public void onEnter() {
        super.onEnter();
        onThisFragment();

    }

    @Override
    public void onLeft() {
        super.onLeft();
    }


}
