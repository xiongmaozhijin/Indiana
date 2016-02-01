package com.example.liangge.indiana.ui.Inner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.inner.CategoryDetailAdapter;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.ShoppingCartBiz;
import com.example.liangge.indiana.biz.inner.CategroyDetailBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.inner.CategoryDetailEntitiy;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;
import com.example.liangge.indiana.ui.widget.ExListViewScrollDone;
import com.example.liangge.indiana.ui.widget.ExScrollView;

/**
 * 类别展示页/搜索展示页
 */
public class CategoryListActivity extends BaseActivity2 {

    private static final String TAG = CategoryListActivity.class.getSimpleName();

    private View mViewTitleBarWrapper;

    private View mViewNetHintWrapper;

    private View mViewAllContent;


    private ExListViewScrollDone mExListView;
    private CategoryDetailAdapter mAdapter;

    private CategroyDetailBiz mCategroyDetailBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private ShoppingCartBiz mShoppingCartBiz;


    /** 加载更多提示 */
    private View mViewLoadMoreHintWrapper;

    private View mViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        initManager();
        initTitle();
        initView();
        initState();

    }

    private void initState() {
        mCategroyDetailBiz.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCategroyDetailBiz.onDestroy();
    }

    private void initManager() {
        mCategroyDetailBiz = CategroyDetailBiz.getInstance(this);
        mDetailInfoBiz = DetailInfoBiz.getInstance(this);
        mShoppingCartBiz = ShoppingCartBiz.getInstance(this);

    }


    private void initView() {
        mViewLoadMoreHintWrapper = View.inflate(this, R.layout.layout_load_more_wrapper, null);
        mViewNetHintWrapper = findViewById(R.id.network_hint);
        mViewAllContent = findViewById(R.id.all_content_wrapper);
        mExListView = (ExListViewScrollDone) findViewById(R.id.listview);
        mExListView.addFooterView(mViewLoadMoreHintWrapper, null, false);
        mAdapter = new CategoryDetailAdapter(this);

        mViewLayout = mViewAllContent;

        mAdapter.setOnAddToCartListenerListener(new CategoryDetailAdapter.OnAddToCartListener() {
            @Override
            public void onAddToCart(CategoryDetailEntitiy item, int buyCnt) {
                mShoppingCartBiz.addProductToShoppingCart(item.getIssue_id(),buyCnt);
                LogUtils.toastShortMsg(CategoryListActivity.this, getResources().getString(R.string.activity_category_detail_add_success));

            }
        });
        mExListView.setAdapter(mAdapter);
        mExListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(TAG, "position=%d", position);
                Object object = mAdapter.getItem(position);
                if ( (object!=null) && (object instanceof CategoryDetailEntitiy) ) {
                    final CategoryDetailEntitiy item = (CategoryDetailEntitiy) object;
                    mDetailInfoBiz.setActivityId(item.getIssue_id());
                    Intent intent = new Intent(CategoryListActivity.this, ProductDetailInfoActivity.class);
                    startActivity(intent);

                }
            }
        });

        mExListView.setOnTouchScrollDoneListener(new ExListViewScrollDone.OnTouchScrollDoneListener() {
            @Override
            public void onTouchScrollBottom() {
                mCategroyDetailBiz.onScrollBottomLoadData();
            }

            @Override
            public void onTouchScrollTop() {

            }
        });

    }


    private void initTitle() {
        mViewTitleBarWrapper = findViewById(R.id.titlebar);
        TextView txtTitle = (TextView) mViewTitleBarWrapper.findViewById(R.id.titlebar_title);
        txtTitle.setText(mCategroyDetailBiz.getTitle());

        mViewTitleBarWrapper.findViewById(R.id.titlebar_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void handleUIMessage(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.CategoryDetailMessage.REFRESH_SUCCESS)
                || strUIAction.equals(UIMessageConts.CategoryDetailMessage.REFRESH_FAILED)) {

        } else if (strUIAction.equals(UIMessageConts.CategoryDetailMessage.ENTERY_REFRESH_START)
                || strUIAction.equals(UIMessageConts.CategoryDetailMessage.ENTERY_REFRESH_SUCCESS)
                || strUIAction.equals(UIMessageConts.CategoryDetailMessage.ENTERY_REFRESH_FAILED)) {

        } else if (strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_MORE_START)
                || strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_MORE_SUCCESS)
                || strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_MORE_FAILED)) {

        } else if (strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_START)
                || strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_FAILED)
                || strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_SUCCESS)) {
            handleLoadUI(strUIAction);

        }

    }

    private void handleLoadUI(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_START)) {
            handleLoadStart();

        } else if (strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_FAILED)) {
            handleLoadFailed();

        } else if (strUIAction.equals(UIMessageConts.CategoryDetailMessage.LOAD_SUCCESS)) {
            handleLoadSuccess();

        }


    }

    private void handleLoadSuccess() {
        int loadMode = mCategroyDetailBiz.getCurLoadMode();
        if (loadMode == Constant.Comm.MODE_ENTER) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNetHintWrapper, mViewAllContent);
            mAdapter.resetDataAndNotify(mCategroyDetailBiz.getListData());

        } else if (loadMode == Constant.Comm.MODE_LOAD_MORE) {
            int size = mCategroyDetailBiz.getListData().size();
            if (size > 0) {
                handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
                mAdapter.loadMoreData(mCategroyDetailBiz.getListData());

            } else {
                handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_SUCCESS, true);

            }

        } else if (loadMode == Constant.Comm.MODE_REFRESH) {

        }
    }

    private void handleLoadFailed() {
        int loadMode = mCategroyDetailBiz.getCurLoadMode();
        if (loadMode == Constant.Comm.MODE_ENTER) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNetHintWrapper, mViewAllContent);

        } else if (loadMode == Constant.Comm.MODE_LOAD_MORE) {
            handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_FAILED, false);

        } else if (loadMode == Constant.Comm.MODE_REFRESH) {
            LogUtils.toastShortMsg(this, getResources().getString(R.string.activity_category_net_error));

        }
    }

    private void handleLoadStart() {
        int loadMode = mCategroyDetailBiz.getCurLoadMode();
        if (loadMode == Constant.Comm.MODE_ENTER) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNetHintWrapper, mViewAllContent);

        } else if (loadMode == Constant.Comm.MODE_LOAD_MORE) {
            handleUILoadMore(mViewLoadMoreHintWrapper, Constant.Comm.LOAD_MORE_START, false);

        } else if (loadMode == Constant.Comm.MODE_REFRESH) {

        }
    }

    private void loadMoreHintLoadMore() {
        mViewLoadMoreHintWrapper.setVisibility(View.VISIBLE);
//        mViewLoadMoreHintWrapper.findViewById(R.id.)
    }


    private void handleLoadMoreUI(String strUIAction) {

    }

    private void handleEnterUI(String strUIAction) {
    }


    private void handleRefreshUI(String strUIAction) {

    }


    @Override
    protected void onBtnReload() {
        LogUtils.w(TAG, "onBtnReload()");
        mCategroyDetailBiz.onCreate();
    }

    @Override
    protected View getScrollViewWrapper() {
        return mExListView;
    }

    @Override
    protected void onRefreshLoadData() {
        LogUtils.w(TAG, "onRefreshLoadData()");
    }

    @Override
    protected View getLayoutViewWrapper() {
        return mViewLayout;
    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }



}
