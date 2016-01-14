package com.example.liangge.indiana.ui.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.user.BingoRecordListViewAdapter;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.user.BingoRecordBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;
import com.example.liangge.indiana.ui.widget.ExScrollView;

import java.util.List;

/**
 * 中奖记录页面
 */
public class BingoRecordActivity extends BaseActivity2 {

    private static final String TAG = BingoRecordActivity.class.getSimpleName();

    private BingoRecordBiz mBingoRecordBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private ListView mListView;

    private BingoRecordListViewAdapter mAdapter;

    private View mViewNetWrapper;

    private View mViewContentWrapper;

    private View mViewLayout;

    private ExScrollView mExScrollView;

    /** 加载更多 */
    private View mViewLoadWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingo_record);
        initView();
        initManager();
        initOnCreate();
    }

    private void initView() {
        mViewNetWrapper = findViewById(R.id.net_hint_wrapper);
        mViewContentWrapper = findViewById(R.id.content_wrapper);
        mExScrollView = (ExScrollView) findViewById(R.id.scrollview);

        mViewLayout = mViewContentWrapper;

        mViewLoadWrapper = findViewById(R.id.load_more_wrapper);

        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new BingoRecordListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(TAG, "onItemClick=%d", position);
                final BingoRecordEntity item = (BingoRecordEntity) parent.getAdapter().getItem(position);
                mDetailInfoBiz.setActivityId(item.getActivityId());
                Intent intent = new Intent(BingoRecordActivity.this, ProductDetailInfoActivity.class);
                startActivity(intent);

            }
        });


        mExScrollView.setOnScrollDoneListener(new ExScrollView.OnScrollDoneListener() {
            @Override
            public void onScrollTop() {

            }

            @Override
            public void onScrollBottom() {
                onScrollBottomLoadMore();
            }
        });

    }

    /**
     * 加载更多
     */
    private void onScrollBottomLoadMore() {
        LogUtils.i(TAG, "onScrollBottomLoadMore()");
        mBingoRecordBiz.onScrollBottomLoadMore();
    }


    private void initOnCreate() {
        mBingoRecordBiz.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBingoRecordBiz.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBingoRecordBiz.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBingoRecordBiz.onDestroy();
    }

    private void initManager() {
        mBingoRecordBiz = BingoRecordBiz.getInstance(this);
        mDetailInfoBiz = DetailInfoBiz.getInstance(this);
    }



    @Override
    protected void handleUIMessage(String strUIAction) {
       if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_FAIL)
               || strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_START)
               || strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_SUCCESS)) {
           handleReLoadUIMsg(strUIAction);

       } else if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_START_MORE)
               || strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_SUCCESS_MORE)
               || strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_FAIL_MORE)) {
           handleUILoadMoreData(strUIAction);

       }


    }

    private void handleUILoadMoreData(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_START_MORE)) {
            handleUILoadMore(mViewLoadWrapper, Constant.Comm.LOAD_MORE_START, false);

        } else if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_SUCCESS_MORE)) {
            List<BingoRecordEntity> lists = mBingoRecordBiz.getBingoRecordList();
            boolean isEmpty = (lists==null || lists.size()==0);
            handleUILoadMore(mViewLoadWrapper, Constant.Comm.LOAD_MORE_SUCCESS, isEmpty);
            mAdapter.loadMoreDataAndNotify(lists);

        } else if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_FAIL_MORE)) {
            handleUILoadMore(mViewLoadWrapper, Constant.Comm.LOAD_MORE_FAILED, false);

        }

    }

    private void handleReLoadUIMsg(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_FAIL)) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNetWrapper, mViewContentWrapper);

        } else if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_START)) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNetWrapper, mViewContentWrapper);

        } else if (strUIAction.equals(UIMessageConts.BingoRecord.M_RELOAD_SUCCESS)) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNetWrapper, mViewContentWrapper);
            mAdapter.resetDataAndNotify(mBingoRecordBiz.getBingoRecordList());

        }


    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }



    public void onBtnBack(View view) {
        finish();
    }


    @Override
    protected void onBtnReload() {
        mBingoRecordBiz.onCreate();
    }

    @Override
    protected View getScrollViewWrapper() {
        return mExScrollView;
    }

    @Override
    protected void onRefreshLoadData() {

    }

    @Override
    protected View getLayoutViewWrapper() {
        return mViewLayout;
    }
}
