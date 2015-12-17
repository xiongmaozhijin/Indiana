package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.IndianaProductGridViewAdapter;
import com.example.liangge.indiana.biz.IndianaBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.ProductItemEntity;
import com.example.liangge.indiana.ui.widget.BannerView;
import com.example.liangge.indiana.ui.widget.ExScrollView;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndianaFragment extends BaseFragment {

    private static final String TAG = IndianaFragment.class.getSimpleName();

    /** 图片轮播 */
    private BannerView mBannerView;

    private IndianaBiz mIndianaBiz;

    private UIReceive mUIReceive;

    /** 产品展示的包裹View */
    private View mViewProductContentWrapper;

    /** 浮动菜单 */
    private View mViewFitFloatMenu;

    /** 产品显示列表 */
    private GridView mGridviewProducts;

    /** 适配器*/
    private IndianaProductGridViewAdapter mAdapter;

    /** Main ScrollView */
    private ExScrollView mScrollViewMain;

    private View mViewProductLoading;

    public IndianaFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_indiana, container, false);

        initWidget(view);


        return view;
    }

    @Override
    protected void registerUIBroadCast() {
        LogUtils.i(TAG, "registerUIBroadCast()");

        if (mUIReceive == null) {
            mUIReceive = new UIReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            getActivity().registerReceiver(mUIReceive, filter);
        }
    }

    @Override
    protected void unRegisterUIBroadCast() {
        LogUtils.i(TAG, "unRegisterUIBroadCast()");

        if (mUIReceive != null) {
            getActivity().unregisterReceiver(mUIReceive);
            mUIReceive = null;
        }
    }

    private void initWidget(View view) {
        mViewProductLoading = view.findViewById(R.id.f_indiana_product_loading_more_wrapper);

        mBannerView = (BannerView) view.findViewById(R.id.main_banner_view);
        mViewProductContentWrapper = view.findViewById(R.id.f_indiana_product_content_wrapper);
        mViewFitFloatMenu = view.findViewById(R.id.f_indiana_product_fit_float_menu);
        mScrollViewMain = (ExScrollView) view.findViewById(R.id.f_indiana_main_scrollview);

        mGridviewProducts = (GridView) view.findViewById(R.id.f_indiana_product_content_gridview);
        mAdapter = new IndianaProductGridViewAdapter(getActivity() );
        mGridviewProducts.setAdapter(mAdapter);



        mBannerView.setOnClickListener(new BannerView.OnClickListener() {
            @Override
            public void onClick(BannerInfo item) {
                LogUtils.i(TAG, "onBannerInfo click. item=%s", item.toString());
            }
        });

        mAdapter.setOnShoppingCartClickListener(new IndianaProductGridViewAdapter.OnShoppingCartClickListener() {
            @Override
            public void onShoppoingCartClick(ProductItemEntity item) {
                LogUtils.i(TAG, "shopping cart click. item=%s", item.toString());
            }
        });

        mGridviewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(TAG, "item click. position=%d", position);
            }
        });


        mScrollViewMain.setOnScrollDoneListener(new ExScrollView.OnScrollDoneListener() {
            @Override
            public void onScrollTop() {
                LogUtils.w(TAG, "onScrollTop()");

            }

            @Override
            public void onScrollBottom() {
                LogUtils.w(TAG, "onScrollBottom()");
                mIndianaBiz.loadProduct();
            }
        });

        mScrollViewMain.setOnFloatMenuHiddenListener(new ExScrollView.OnFloatMenuHiddenListener() {
            @Override
            public void shouldHiddle(boolean bShouldHiddle) {
                if (bShouldHiddle) {
                    mViewFitFloatMenu.setVisibility(View.GONE);

                } else {
                    mViewFitFloatMenu.setVisibility(View.VISIBLE);

                }
            }
        }, mViewProductContentWrapper);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
        initInfo();
    }

    private void initManager() {
        mIndianaBiz = IndianaBiz.getInstance(getActivity());
    }


    private void initInfo() {
        mIndianaBiz.init();
    }


    /**
     * 消息驱动
     */
    private class UIReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
           handleUIMessage(intent);
        }

        private void handleUIMessage(Intent intent) {
            LogUtils.i(TAG, "handleUIMessage().");
            if (intent != null) {
                String strBCAction = intent.getAction();
                if (strBCAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) {
                    final String strUIAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                    LogUtils.e(TAG, "receive ui message action=%s", strUIAction);
                    if (strUIAction != null) {
                        if ( (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_FAIL) ) || (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_SUCCESS) ) ||
                                (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER) ) )  {
                            handleUIBanner(strUIAction);

                        } else if ( (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_PRODUCT_DATA_FAIL)) || (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCESS)) ||
                                (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_PRODUCT_DATA))) {
                            handleUIProduct(strUIAction);

                        } else if (strUIAction.equals(UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK)) {
                            handleCommResponse();
                        }


                    }

                }
            }
        }
    }

    /**
     *
     * @param isLoading 是否要提示正在加载
     * @param isSuccess 是否加载成功
     */
    public void setProductLoadingUI(boolean isLoading, boolean isSuccess) {
        LogUtils.w(TAG, "isLoading=%b, isSuccess=%b", isLoading, isSuccess);

        if (isSuccess) {
            mViewProductLoading.setVisibility(View.GONE);
        } else {
            if (isLoading) {
                mViewProductLoading.setVisibility(View.VISIBLE);

            } else {
                ((TextView)mViewProductLoading.findViewById(R.id.product_loading_txv_hint)).setText(getResources().getString(R.string.f_indian_product_load_fail_hint));

            }
        }


    }



    /**
     * 处理公共问题，比如没有联网
     */
    private void handleCommResponse() {

    }

    /**
     * 处理产品数据UI
     * @param strUIAction
     */
    private void handleUIProduct(String strUIAction) {
        LogUtils.i(TAG, "handleUIProduct()");

        if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCESS)) {
            mAdapter.setDataAndNotify(mIndianaBiz.getListProducts());
            setProductLoadingUI(false, true);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_PRODUCT_DATA)) {
            setProductLoadingUI(true, false);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_PRODUCT_DATA_FAIL)) {
            setProductLoadingUI(false, false);
        }

    }

    /**
     * 处理图片轮播ui驱动响应
     * @param strUIAction
     */
    private void handleUIBanner(String strUIAction) {
        LogUtils.i(TAG, "handleUIBanner()");

        if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_FAIL)) {

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER)) {

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_SUCCESS)) {
            mBannerView.setDatasAndNotify(mIndianaBiz.getListBanners());

        }
    }



    @Override
    public void onStop() {
        super.onStop();
        mIndianaBiz.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIndianaBiz.onDestroy();

    }


    @Override
    protected String getDeugTag() {
        return TAG;
    }

    @Override
    public void onFirstEnter() {
        LogUtils.w(TAG, "onFirstEnter()");
    }

    @Override
    public void onEnter() {
        LogUtils.w(TAG, "onEnter()");
    }

    @Override
    public void onLeft() {
        LogUtils.w(TAG, "onLeft()");
    }


}
