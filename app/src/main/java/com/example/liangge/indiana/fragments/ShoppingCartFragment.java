package com.example.liangge.indiana.fragments;


import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.ShoppingCartListViewAdapter;
import com.example.liangge.indiana.biz.ShoppingCartBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.ui.HomeActivity;
import com.example.liangge.indiana.ui.InventoryPayActivity;
import com.example.liangge.indiana.ui.widget.ExRadioButton;
import com.example.liangge.indiana.ui.widget.ExScrollView;
import com.example.liangge.indiana.ui.widget.RotateImageView;
import com.jauker.widget.BadgeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends BaseRefreshFragment {

    private View mViewLayoutWrapper;

    private ExScrollView mExScrollView;

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

    /** 底部菜单的购物车图标 */
    private ExRadioButton mBtnShoppingCart;

    private ListView mListView;

    private ShoppingCartListViewAdapter mAdapter;

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
        initViewOnActivityCreate();
        initManager();
        mShoppingCartBiz.onViewCreated();

    }

    private void initViewOnActivityCreate() {

        mBtnShoppingCart = (ExRadioButton) ((HomeActivity)getActivity()).getShoppingCartBtn();
    }

    private void initManager() {
        mShoppingCartBiz = ShoppingCartBiz.getInstance(getActivity());

    }

    /**
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        mViewLayoutWrapper = view;
        mExScrollView = (ExScrollView) view.findViewById(R.id.f_shopping_content_scrollview);
        mViewEmptyWrapper = view.findViewById(R.id.f_shoppingcart_empty_wrapper);
        mViewLoadOrNotNetWrapper = view.findViewById(R.id.f_shopping_not_network_wrapper);
        mViewContentWrapper = view.findViewById(R.id.f_shoppingcart_content_wrapper);

        mBtnGoIndiana = (Button) view.findViewById(R.id.f_shoppingcart_btn_go_indiana);


        mBtnCommitPay = (Button) view.findViewById(R.id.f_shopping_content_item_btn_commit_pay);
        mTxvContentItemDesc1 = (TextView) view.findViewById(R.id.f_shopping_content_item_txvdesc1);
        mTxvContentItemDesc2 = (TextView) view.findViewById(R.id.f_shopping_content_item_txvdesc2);

        mListView = (ListView) view.findViewById(R.id.f_shopping_content_listview);

        mAdapter = new ShoppingCartListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        mAdapter.setOnBuyCntChangeListener(new ShoppingCartListViewAdapter.OnBuyCntChangeListener() {
            @Override
            public void onBuyCntChange(InventoryEntity inventoryEntity) {
                LogUtils.w(TAG, "onBuyCntChange().inventoryEntity=%s", inventoryEntity.toString());
                mShoppingCartBiz.updateProductItemBuyCnt(inventoryEntity);
                mShoppingCartBiz.requestPayInfo();

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.w(TAG, "onItemClick()");

            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.w(TAG, "onItemLongClick()");

                return false;
            }
        });


        //TODO onListerner
        //马上去夺宝
        mBtnGoIndiana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).onFragmentBtnGoIndiana();
            }
        });


        mBtnCommitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(TAG, "mBtnCommitPay#onClick()");
                Intent i = new Intent(getActivity(), InventoryPayActivity.class);
                startActivity(i);
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

    @Override
    protected void onBtnReload() {
        LogUtils.w(TAG, "onBtnReload()");
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

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_UPDATE_SHOPPINGCART_ITEM_COUNTS)
                            || uiAction.equals(UIMessageConts.ShoppingCartMessage.M_DISMISS_SHOPPINGCART_ITEM_COUNTS_ICON)) {
            handleShoppingCartCounts(uiAction);

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_UPDATE_PAY_INFO)) {
            handleUpdatePayInfo();

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_UPDATE_SHOPPINGCART_ITEM_COUNTS_WITHOUT_SHAKE)) {
            handleUpdateBadgeViewCntWithoutShake();
        }

    }

    /**
     * 更新底部BadgeView的数量，或取消显示
     */
    private void handleUpdateBadgeViewCntWithoutShake() {
//        mBtnShoppingCart.setBuyCnt(mShoppingCartBiz.getBuyCnt());
        mBtnShoppingCart.setBuyCnt(mShoppingCartBiz.getTotalDiffProduct());
    }

    /**
     * 更新结算信息
     */
    private void handleUpdatePayInfo() {
        int iTotalNum = mShoppingCartBiz.getTotalDiffProduct();
        int iTotalCost = mShoppingCartBiz.getTotalCost();
        String desc1Format = getActivity().getResources().getString(R.string.f_shoppingcart_buycnt_hint);
        String desc2Fromat = getActivity().getResources().getString(R.string.f_shoppingcart_pay_hint);
        String desc1 = String.format(desc1Format, iTotalNum);
        String desc2 = String.format(desc2Fromat, iTotalCost);

        mTxvContentItemDesc1.setText(desc1);
        mTxvContentItemDesc2.setText(desc2);
    }

    /**
     * 更新购物车商品数量
     */
    private void handleShoppingCartCounts(String uiAction) {
        if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_UPDATE_SHOPPINGCART_ITEM_COUNTS)) {
            //TODO
            //0.badgeview下次再画
//            mBtnShoppingCart.setBuyCnt(mShoppingCartBiz.getBuyCnt());
            mBtnShoppingCart.setBuyCnt(mShoppingCartBiz.getTotalDiffProduct());
            mBtnShoppingCart.setBuyIconVisibility(true);

            //1.摇摆
            ObjectAnimator anim = ObjectAnimator.ofFloat(mBtnShoppingCart, "rotation", 0, -30, 0, 30, 0);
            anim.setRepeatCount(2);
            anim.start();

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_DISMISS_SHOPPINGCART_ITEM_COUNTS_ICON)) {
            mBtnShoppingCart.setBuyIconVisibility(false);

        }

    }

    private void handleUINotNetwork() {

    }

    private void handleUINetRequest(String uiAction) {
        if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_STARTS)) {

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_SUCCESS)) {

        } else if (uiAction.equals(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_FAILED)) {

        }


    }


    /**
     * 处理购物列表
     */
    private void handleUIResetUpdate() {
        showContentView();
        LogUtils.w(TAG, "list info=%s", mShoppingCartBiz.getListInventoryData().toString());
        mAdapter.resetDataAndNotify(mShoppingCartBiz.getListInventoryData());
        //请求更新付款信息
        mShoppingCartBiz.requestPayInfo();
        dismissRefreshUI();
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

    }


    @Override
    protected View getScrollViewWrapper() {
        return mExScrollView;
    }

    @Override
    protected void onRefreshLoadData() {
        LogUtils.w(TAG, "onRefreshLoadData()");
        mShoppingCartBiz.onRefreshLoadData();
    }

    @Override
    protected View getLayoutViewWrapper() {
        return mViewLayoutWrapper;
    }

    //TODO 暂时这样处理
    @Override
    public void onResume() {
        super.onResume();
        LogUtils.w(TAG, "onResume()");
        if (isAlreadyEnter()) {
            mShoppingCartBiz.onResume(true);
        }
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
