package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ScrollView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.IndianaProductGridViewAdapter;
import com.example.liangge.indiana.biz.BannerInfoBiz;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.IndianaBiz;
import com.example.liangge.indiana.biz.ShoppingCartBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;
import com.example.liangge.indiana.ui.widget.BannerView;
import com.example.liangge.indiana.ui.widget.ExScrollView;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndianaFragment extends BaseFragment {

    private static final String TAG = IndianaFragment.class.getSimpleName();

    /** 图片轮播 */
    private BannerView mBannerView;

    private IndianaBiz mIndianaBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private BannerInfoBiz mBannerInfoBiz;

    private UIReceive mUIReceive;

    /** 产品展示的包裹View */
    private View mViewProductContentWrapper;

    /** 浮动菜单 */
//    private View mViewFitFloatMenu;

    /** 产品显示列表 */
    private GridView mGridviewProducts;

    /** 适配器*/
    private IndianaProductGridViewAdapter mAdapter;

    /** Main ScrollView */
    private ExScrollView mScrollViewMain;

    /** GridView底部加载更多提示 */
    private View mViewProductLoading;

    private View mViewNotNetworkOrFirstLoadWrapper;

    private View mViewAllContentWrapper;

    /** 加载子标签时的网络加载提示 */
    private View mViewTagNetInfoDataInfoWrapper;

    /** 下拉刷新 */
//    protected PtrFrameLayout mPtrFrameLayout;

    private PtrClassicFrameLayout mPtrFrame;

    public IndianaFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_indiana, container, false);

        initWidget(view);
        initRefreshView2(view);
        initMenuBtn(view);

        return view;
    }

/*
    private void initRefreshView(View view) {
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.material_style_ptr_frame);

        // header
        final MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(false);
            }
        }, 100);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 3000);
            }
        });



    }

  */


    private void initRefreshView2(View view) {
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_web_view_frame);

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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollViewMain, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 3000);
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






    private void initMenuBtn(View view) {
        View fixMenuWrapper = view.findViewById(R.id.f_indiana_fix_menu);
//        View fixFloatMenuWrapper = view.findViewById(R.id.f_indiana_product_fit_float_menu);

        fixMenuWrapper.findViewById(R.id.indian_product_category_rb_hots).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnHots();
            }
        });

        fixMenuWrapper.findViewById(R.id.indian_product_category_rb_lastest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLatest();
            }
        });
        fixMenuWrapper.findViewById(R.id.indian_product_category_rb_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSchedule();
            }
        });
        fixMenuWrapper.findViewById(R.id.indian_product_category_rb_need_peoples).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnNeed();
            }
        });
        fixMenuWrapper.findViewById(R.id.indian_product_category_rb_category_items).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnCategory();
            }
        });
        //

     /*
        fixFloatMenuWrapper.findViewById(R.id.indian_product_category_rb_hots).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnHots();
            }
        });

        fixFloatMenuWrapper.findViewById(R.id.indian_product_category_rb_lastest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLatest();
            }
        });
        fixFloatMenuWrapper.findViewById(R.id.indian_product_category_rb_schedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSchedule();
            }
        });
        fixFloatMenuWrapper.findViewById(R.id.indian_product_category_rb_need_peoples).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnNeed();
            }
        });
        fixFloatMenuWrapper.findViewById(R.id.indian_product_category_rb_category_items).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnCategory();
            }
        });

        */

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
        mViewNotNetworkOrFirstLoadWrapper = view.findViewById(R.id.f_indian_not_network_first_load_wrapper);
        mViewAllContentWrapper = view.findViewById(R.id.f_indiana_all_content_wrapper);

        mViewTagNetInfoDataInfoWrapper = view.findViewById(R.id.f_indiana_tag_net_hint_wrapper);


        mViewProductLoading = view.findViewById(R.id.f_indiana_product_loading_more_wrapper);

        mBannerView = (BannerView) view.findViewById(R.id.main_banner_view);
        mViewProductContentWrapper = view.findViewById(R.id.f_indiana_product_content_wrapper);
//        mViewFitFloatMenu = view.findViewById(R.id.f_indiana_product_fit_float_menu);
        mScrollViewMain = (ExScrollView) view.findViewById(R.id.f_indiana_main_scrollview);

        mGridviewProducts = (GridView) view.findViewById(R.id.f_indiana_product_content_gridview);
        mAdapter = new IndianaProductGridViewAdapter(getActivity() );
        mGridviewProducts.setAdapter(mAdapter);



        mBannerView.setOnClickListener(new BannerView.OnClickListener() {
            @Override
            public void onClick(BannerInfo item) {
                LogUtils.i(TAG, "onBannerInfo click. item=%s", item.toString());
                mBannerInfoBiz.onItemClick(item);
            }
        });

        mAdapter.setOnShoppingCartClickListener(new IndianaProductGridViewAdapter.OnShoppingCartClickListener() {
            @Override
            public void onShoppoingCartClick(ActivityProductItemEntity item) {
                LogUtils.i(TAG, "shopping cart click. item=%s", item.toString());
                //TODO 添加物品
                ShoppingCartBiz.getInstance(getActivity()).addProductToShoppingCart(item);
            }
        });

        mGridviewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(TAG, "item click. position=%d", position);
                //TODO
                ActivityProductItemEntity item = (ActivityProductItemEntity) parent.getAdapter().getItem(position);
                mDetailInfoBiz.setActivityId(item.getActivityId());
                startActivity(new Intent(getActivity(), ProductDetailInfoActivity.class));
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

            }
        });

        mScrollViewMain.setOnFloatMenuHiddenListener(new ExScrollView.OnFloatMenuHiddenListener() {
            @Override
            public void shouldHiddle(boolean bShouldHiddle) {
                if (bShouldHiddle) {
//                    mViewFitFloatMenu.setVisibility(View.GONE);

                } else {
//                    mViewFitFloatMenu.setVisibility(View.VISIBLE);

                }
            }
        }, mViewProductContentWrapper);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
    }

    private void initManager() {
        mIndianaBiz = IndianaBiz.getInstance(getActivity());
        mDetailInfoBiz = DetailInfoBiz.getInstance(getActivity());
        mBannerInfoBiz = BannerInfoBiz.getInstance(getActivity());

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

                        } else if (strUIAction.equals(UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK)) {
                            handleCommResponse();

                        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_START)
                                        || strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_FAIL)
                                        || strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_SUCCESS) ) {
                            handleUITagProduct(strUIAction);

                        }


                    }

                }
            }
        }
    }

    /**
     * 处理加载子tag时的信息
     * @param strUIAction
     */
    private void handleUITagProduct(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_START)) {
            mGridviewProducts.setVisibility(View.GONE);
            mViewTagNetInfoDataInfoWrapper.setVisibility(View.VISIBLE);
            mViewTagNetInfoDataInfoWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.VISIBLE);
            mViewTagNetInfoDataInfoWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.GONE);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_FAIL)) {
            mGridviewProducts.setVisibility(View.GONE);
            mViewProductContentWrapper.setVisibility(View.VISIBLE);
            mViewProductContentWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.VISIBLE);
            mViewTagNetInfoDataInfoWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.GONE);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_SUCCESS)) {
//            int iScrollY = mScrollViewMain.getExScrollY();
//            LogUtils.e(TAG, "iScrollY=%d", iScrollY);

            mViewTagNetInfoDataInfoWrapper.setVisibility(View.GONE);
            mGridviewProducts.setVisibility(View.VISIBLE);
            mAdapter.setDataAndNotify(mIndianaBiz.getListProducts());

            mScrollViewMain.smoothScrollTo(0, 0);
        }
    }



    /**
     * 处理公共问题，比如没有联网
     */
    private void handleCommResponse() {
        //TODO
    }


    /**
     * 处理图片轮播ui驱动响应
     * @param strUIAction
     */
    private void handleUIBanner(String strUIAction) {
        LogUtils.i(TAG, "handleUIBanner()");

        if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_FAIL)) {
            mViewAllContentWrapper.setVisibility(View.GONE);
            mViewNotNetworkOrFirstLoadWrapper.setVisibility(View.VISIBLE);
            mViewNotNetworkOrFirstLoadWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.GONE);
            mViewNotNetworkOrFirstLoadWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.VISIBLE);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER)) {
            mViewAllContentWrapper.setVisibility(View.GONE);
            mViewNotNetworkOrFirstLoadWrapper.setVisibility(View.VISIBLE);
            mViewNotNetworkOrFirstLoadWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.GONE);
            mViewNotNetworkOrFirstLoadWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.VISIBLE);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_SUCCESS)) {
            mViewAllContentWrapper.setVisibility(View.VISIBLE);
            mViewNotNetworkOrFirstLoadWrapper.setVisibility(View.GONE);
            mViewProductLoading.setVisibility(View.GONE);
            mBannerView.setDatasAndNotify(mIndianaBiz.getListBanners());
            //TODO
            onBtnHots();
        }

    }


    /**
     * 人气
     */
    public void onBtnHots() {
        LogUtils.w(TAG, "onBtnHots()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_HOTS, false);
    }

    /**
     * 最新
     */
    public void onBtnLatest() {
        LogUtils.w(TAG, "onBtnLatest()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_NEWS, false);
    }

    /**
     * 进度
     */
    public void onBtnSchedule() {
        LogUtils.w(TAG, "onBtnSchedule()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_PROGRESS, false);

    }

    /**
     * 总需
     */
    public void onBtnNeed() {
        LogUtils.w(TAG, "onBtnNeed()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_SHARE, false);
    }

    /**
     * 分类
     */
    public void onBtnCategory() {
        LogUtils.w(TAG, "onBtnCategory()");

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
        super.onFirstEnter();
        mIndianaBiz.onFirstEnter();

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
