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
import android.widget.ImageView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.IndianaProductGridViewAdapter;
import com.example.liangge.indiana.biz.BannerInfoBiz;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.IndianaBiz;
import com.example.liangge.indiana.biz.ShoppingCartBiz;
import com.example.liangge.indiana.biz.WebViewBiz;
import com.example.liangge.indiana.biz.inner.CategroyDetailBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.model.inner.NotificationEntitiy;
import com.example.liangge.indiana.ui.Inner.CategoryListActivity;
import com.example.liangge.indiana.ui.Inner.IndianaCategoryActivity;
import com.example.liangge.indiana.ui.Inner.SearchActivity;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;
import com.example.liangge.indiana.ui.WebViewActivity;
import com.example.liangge.indiana.ui.widget.BannerView;
import com.example.liangge.indiana.ui.widget.ExScrollView;
import com.example.liangge.indiana.ui.widget.NoticationTextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndianaFragment extends BaseRefreshFragment {

    private static final String TAG = IndianaFragment.class.getSimpleName();


    private View mViewLayout;


    /** 图片轮播 */
    private BannerView mBannerView;

    private IndianaBiz mIndianaBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private BannerInfoBiz mBannerInfoBiz;

    private WebViewBiz mWebViewBiz;

    private CategroyDetailBiz mCategroyDetailBiz;

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
    private View mViewProductLoadingWrapper;

    private View mViewNotNetworkOrFirstLoadWrapper;

    private View mViewAllContentWrapper;

    /** 加载子标签时的网络加载提示 */
    private View mViewTagNetInfoDataInfoWrapper;


    //top menu
    /** 分类 */
    private View mViewCategoryWrapper;

    /** 10元专区 */
    private View mViewTenYuanAreaWrapper;

    /** 常见问题 */
    private View mViewQAQ;

    /** 搜索 */
    private View mViewSearch;

    /** 消息轮播TextView */
    private NoticationTextView mViewNotification;

    /** 下拉刷新 */
//    protected PtrFrameLayout mPtrFrameLayout;


    public IndianaFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_indiana, container, false);

        initWidget(view);
        initMenuBtn(view);
        initTopMenuView(view);

        return view;
    }

    private void initTopMenuView(View view) {
        mViewCategoryWrapper = view.findViewById(R.id.top_menu_catory);
        mViewTenYuanAreaWrapper = view.findViewById(R.id.top_menu_tenyuan_area);
        mViewQAQ = view.findViewById(R.id.top_menu_qaq);

        mViewCategoryWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(TAG, "分类");
                Intent intent = new Intent(getActivity(), IndianaCategoryActivity.class);
                startActivity(intent);
            }
        });

        mViewTenYuanAreaWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(TAG, "10元专区");
                String title = getResources().getString(R.string.f_indiana_top_menu_tenyuan_area);
                mCategroyDetailBiz.setTitle(title);
                mCategroyDetailBiz.setRequestData(0, CategroyDetailBiz.IRequestCategory.TEN_YUAN_CATEGORY);
                Intent intent = new Intent(getActivity(), CategoryListActivity.class);
                startActivity(intent);
            }
        });

        mViewQAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(TAG, "常见问题");
                String title = getResources().getString(R.string.webview_title_qaq);
                String url = Constant.WebServiceAPI.REQUEST_QAQ;
                mWebViewBiz.setWebViewRes(title, url);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                startActivity(intent);
            }
        });



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
        mViewLayout = view;
        mViewNotNetworkOrFirstLoadWrapper = view.findViewById(R.id.f_indian_not_network_first_load_wrapper);
        mViewAllContentWrapper = view.findViewById(R.id.f_indiana_all_content_wrapper);

        mViewTagNetInfoDataInfoWrapper = view.findViewById(R.id.f_indiana_tag_net_hint_wrapper);

        mViewNotification = (NoticationTextView) view.findViewById(R.id.txv_notification);

        mViewProductLoadingWrapper = view.findViewById(R.id.f_indiana_product_loading_more_wrapper);

        mBannerView = (BannerView) view.findViewById(R.id.main_banner_view);
        mBannerView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        mViewProductContentWrapper = view.findViewById(R.id.f_indiana_product_content_wrapper);
//        mViewFitFloatMenu = view.findViewById(R.id.f_indiana_product_fit_float_menu);
        mScrollViewMain = (ExScrollView) view.findViewById(R.id.f_indiana_main_scrollview);

        mGridviewProducts = (GridView) view.findViewById(R.id.f_indiana_product_content_gridview);
        mAdapter = new IndianaProductGridViewAdapter(getActivity() );
        mGridviewProducts.setAdapter(mAdapter);

        mViewSearch = view.findViewById(R.id.main_btn_search);


        mViewNotification.setOnItemClickListener(new NoticationTextView.OnItemClickListener() {
            @Override
            public void onItemClickListener(NotificationEntitiy item) {
                LogUtils.i(TAG, "onItemClick().");
                mDetailInfoBiz.setActivityId(item.getIssue_id());
                Intent i = new Intent(getActivity(), ProductDetailInfoActivity.class);
                startActivity(i);
            }
        });


        mViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSearch();
            }
        });

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
                onScrollBottomLoadMore();
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

    /**
     * 搜索
     */
    private void onBtnSearch() {
        LogUtils.i(TAG, "onBtnSearch()");
        Intent i = new Intent(getActivity(), SearchActivity.class);
        startActivity(i);

    }

    /**
     * 滚动到底部加载更多
     */
    private void onScrollBottomLoadMore() {
        mIndianaBiz.loadActivityProductInfo(mIndianaBiz.getCurRequestTag(), true, Constant.Comm.MODE_LOAD_MORE);

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();

        disableWhenHorizontalMove();
    }

    @Override
    protected View getScrollViewWrapper() {
        return mScrollViewMain;
    }

    @Override
    protected void onRefreshLoadData() {
        mIndianaBiz.loadActivityProductInfo(mIndianaBiz.getCurRequestTag(), false, Constant.Comm.MODE_REFRESH);
    }

    @Override
    protected View getLayoutViewWrapper() {
        return mViewLayout;
    }

    private void initManager() {
        mIndianaBiz = IndianaBiz.getInstance(getActivity());
        mDetailInfoBiz = DetailInfoBiz.getInstance(getActivity());
        mBannerInfoBiz = BannerInfoBiz.getInstance(getActivity());
        mWebViewBiz = WebViewBiz.getInstance(getActivity());
        mCategroyDetailBiz = CategroyDetailBiz.getInstance(getActivity());

    }

    @Override
    protected void onBtnReload() {
        LogUtils.w(TAG, "onBtnReload()");
        mIndianaBiz.onFirstEnter();
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

                        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_START)
                                || strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_FAIL)
                                || strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_SUCCESS)) {
                            handleUITagLoadMore(strUIAction);

                        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_NOTICATION_SUCCESS)) {
                            handleUINotication();

                        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_REFRESH_START) ||
                                strUIAction.equals(UIMessageConts.IndianaMessage.MSG_REFRESH_FAILED) ||
                                strUIAction.equals(UIMessageConts.IndianaMessage.MSG_REFRESH_SUCCESS)) {
                            handleUIRefresh(strUIAction);

                        }


                    }

                }
            }
        }
    }

    /**
     * 处理加载消息通知
     */
    private void handleUINotication() {
        LogUtils.i(TAG, "handleUINotication()");
        mViewNotification.setNotificationList(mIndianaBiz.getNotificationList());
    }

    /**
     * 处理加载更多ui
     * @param strUIAction
     */
    private void handleUITagLoadMore(String strUIAction) {
        LogUtils.i(TAG, "handleUITagLoadMore().strUIAction=%s", strUIAction);

        if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_START)) {
            handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_START, false);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_FAIL)) {
            handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_FAILED, false);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_SUCCESS)) {
            //TODO
            List<ActivityProductItemEntity> requestListData = mIndianaBiz.getListProducts();
            if (requestListData != null) {
                if (requestListData.size() > 0) {
                    handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
                    mAdapter.loadMoreProductDataAndNotify(requestListData);

                } else {
                    handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_SUCCESS, true);

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
            handleNetUI(Constant.Comm.NET_LOADING, mViewTagNetInfoDataInfoWrapper, mGridviewProducts);
            handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);


        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_FAIL)) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewTagNetInfoDataInfoWrapper, mGridviewProducts);
            handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);

//            handleCompleteRefreshUI();

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_SUCCESS)) {
//            int iScrollY = mScrollViewMain.getExScrollY();
//            LogUtils.e(TAG, "iScrollY=%d", iScrollY);

            handleNetUI(Constant.Comm.NET_SUCCESS, mViewTagNetInfoDataInfoWrapper, mGridviewProducts);
            handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);

            mAdapter.setDataAndNotify(mIndianaBiz.getListProducts());

//            handleCompleteRefreshUI();

        }
    }


    private void handleUIRefresh(String strAction) {
        if (strAction.equals(UIMessageConts.IndianaMessage.MSG_REFRESH_START)) {


        } else if (strAction.equals(UIMessageConts.IndianaMessage.MSG_REFRESH_FAILED)) {
            handleCompleteRefreshUI();
            String hint = getResources().getString(R.string.refresh_failed);
            LogUtils.toastShortMsg(getActivity(), hint);

        } else if (strAction.equals(UIMessageConts.IndianaMessage.MSG_REFRESH_SUCCESS)) {
            handleCompleteRefreshUI();
            handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
            mAdapter.setDataAndNotify(mIndianaBiz.getListProducts());

        }

    }



    /**
     * 处理公共问题，比如没有联网
     */
    private void handleCommResponse() {
        handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNotNetworkOrFirstLoadWrapper, mViewAllContentWrapper);
    }


    /**
     * 处理图片轮播ui驱动响应
     * @param strUIAction
     */
    private void handleUIBanner(String strUIAction) {
        LogUtils.i(TAG, "handleUIBanner().uiAction=%s", strUIAction);

        if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_FAIL)) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNotNetworkOrFirstLoadWrapper, mViewAllContentWrapper);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER)) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNotNetworkOrFirstLoadWrapper, mViewAllContentWrapper);

        } else if (strUIAction.equals(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_SUCCESS)) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNotNetworkOrFirstLoadWrapper, mViewAllContentWrapper);
            handleUILoadMore(mViewProductLoadingWrapper, Constant.Comm.LOAD_MORE_SUCCESS, true);

            mBannerView.setDatasAndNotify(mIndianaBiz.getListBanners());
            //TODO
            onBtnHots();
        }

    }

    /**
     * 完成下拉刷新
     */
    private void handleCompleteRefreshUI() {
        dismissRefreshUI();
    }





    /**
     * 人气
     */
    public void onBtnHots() {
        LogUtils.w(TAG, "onBtnHots()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_HOTS, false, Constant.Comm.MODE_ENTER);
    }

    /**
     * 最新
     */
    public void onBtnLatest() {
        LogUtils.w(TAG, "onBtnLatest()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_NEWS, false, Constant.Comm.MODE_ENTER);
    }

    /**
     * 进度
     */
    public void onBtnSchedule() {
        LogUtils.w(TAG, "onBtnSchedule()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_PROGRESS, false, Constant.Comm.MODE_ENTER);

    }

    /**
     * 总需
     */
    public void onBtnNeed() {
        LogUtils.w(TAG, "onBtnNeed()");
        mIndianaBiz.loadActivityProductInfo(Constant.IndianaFragment.TAG_SHARE, false, Constant.Comm.MODE_ENTER);
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


    @Override
    public void onDoubleClick() {
        super.onDoubleClick();
        if (canDoubleClick(mScrollViewMain, mViewNotNetworkOrFirstLoadWrapper, mViewAllContentWrapper) ) {
            mScrollViewMain.smoothScrollTo(0, 0);
            if (!isRefreshing()) {
                onAutoRefreshUIShow();
            }
        }
    }
}
