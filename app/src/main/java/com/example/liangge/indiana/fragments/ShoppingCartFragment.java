package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.ShoppingCartBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.ui.HomeActivity;
import com.example.liangge.indiana.ui.widget.RotateImageView;

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

    private ShoppingCartBiz mShoppingCartBiz;

    /** 刷新加载图标 */
    private RotateImageView mIconRefreshLoading;

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();


    private UIReceiveBroadcat mReceive;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
    }

    private void initManager() {
        mShoppingCartBiz = ShoppingCartBiz.getInstance(getActivity());

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

        //TODO onListerner
        //马上去夺宝
        mBtnGoIndiana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).onFragmentBtnGoIndiana();
            }
        });




    }

    @Override
    protected void registerUIBroadCast() {
        LogUtils.w(TAG, "registerUIBroadCast()");
        if (mReceive == null) {
            mReceive = new UIReceiveBroadcat();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            getActivity().registerReceiver(mReceive, filter);
        }
    }

    @Override
    protected void unRegisterUIBroadCast() {
        LogUtils.w(TAG, "unRegisterUIBroadCast()");
        if (mReceive != null) {
            getActivity().unregisterReceiver(mReceive);
            mReceive = null;
        }

    }



    private class UIReceiveBroadcat extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String intetnAction = intent.getAction();
                if ( (intetnAction != null) && (intetnAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) ) {
                    handleUIMessage(intent);
                }
            }
        }
    }

    private void handleUIMessage(Intent intent) {
        String uiAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
        LogUtils.w(TAG, "onReceive ui message. action=%s", uiAction);

        if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_EMPTY_ORDERS)) {
            handleUIEmptyShoppingCart();

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_RESET_UPDATE_LISTS)) {
            handleUIResetUpdate();

        } else if ((uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_STARTS) || uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_SUCCESS)
                || uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_FAILED))) {
            handleUINetRequest(uiAction);

        } else if (uiAction.equals(UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK)) {
            handleUINotNetwork();

        }

    }

    private void handleUINotNetwork() {

    }

    private void handleUINetRequest(String uiAction) {
        if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_STARTS)) {
            mIconRefreshLoading.startRotateview();

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_SUCCESS)) {
             mIconRefreshLoading.stopRotateview();

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_FAILED)) {
            mIconRefreshLoading.stopRotateview();

        }


    }



    private void handleUIResetUpdate() {
        showContentView();

    }

    private void handleUIEmptyShoppingCart() {
        showEmptyShoppingCart();

    }


    private void showContentView() {
        mViewContentWrapper.setVisibility(View.VISIBLE);
        mViewEmptyWrapper.setVisibility(View.GONE);
        mViewLoadOrNotNetWrapper.setVisibility(View.GONE);
    }

    private void showNotNetWork() {
        mViewContentWrapper.setVisibility(View.GONE);
        mViewEmptyWrapper.setVisibility(View.GONE);
        mViewLoadOrNotNetWrapper.setVisibility(View.VISIBLE);
    }

    private void showEmptyShoppingCart() {
        mViewContentWrapper.setVisibility(View.GONE);
        mViewEmptyWrapper.setVisibility(View.VISIBLE);
        mViewLoadOrNotNetWrapper.setVisibility(View.GONE);
    }


    private void onThisFragment() {

    }

    private void initOnFirstEnter() {
        mIconRefreshLoading = ((HomeActivity)getActivity()).getShoppingCartRefrshIconView();
        mIconRefreshLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.w(TAG, "refresh icon click");
            }
        });


    }


    @Override
    protected String getDeugTag() {
        return TAG;
    }

    @Override
    public void onFirstEnter() {
        super.onFirstEnter();
        onThisFragment();
        initOnFirstEnter();
        mShoppingCartBiz.onFirstEnter();

    }



    @Override
    public void onEnter() {
        super.onEnter();
        onThisFragment();
        mShoppingCartBiz.onEnter();
    }

    @Override
    public void onLeft() {
        super.onLeft();
        mShoppingCartBiz.onLeave();
    }


}
