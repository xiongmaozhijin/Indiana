package com.example.liangge.indiana.ui.Inner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.inner.HistoryRecordAdapter;
import com.example.liangge.indiana.biz.inner.HistoryRecordBiz;
import com.example.liangge.indiana.biz.user.UserCenterBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.ResponseActivityPlayRecordEntity;
import com.example.liangge.indiana.model.inner.HistoryRecordEntity;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;
import com.example.liangge.indiana.ui.widget.ExScrollView;

import java.util.List;

/**
 * 往期揭晓
 */
public class HistoryRunRecordActivity extends BaseActivity2 {

    private static final String TAG = HistoryRunRecordActivity.class.getSimpleName();

    private View mViewNotNetWrapper;

    private View mViewAllContent;

    private View mViewLayout;

    private View mViewLoadMoreWrapper;

    private ExScrollView mExScrollView;

    private ListView mListView;

    private HistoryRecordAdapter mAdapter;


    private HistoryRecordBiz mHistoryRecordBiz;

    private UserCenterBiz mUserCenterBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_run_record);

        initView();
        initManager();
        initRes();
        initState();
    }

    private void initState() {
        mHistoryRecordBiz.onCreate();
    }

    private void initRes() {

    }

    private void initManager() {
        mHistoryRecordBiz = HistoryRecordBiz.getInstance(this);
        mUserCenterBiz = UserCenterBiz.getInstance(this);
    }

    private void initView() {
        mViewLayout = View.inflate(this, R.layout.activity_history_run_record, null);
        mViewNotNetWrapper = findViewById(R.id.net_hint_wrapper);
        mViewAllContent = findViewById(R.id.all_content_wrapper);
        mViewLoadMoreWrapper = findViewById(R.id.load_more_wrapper);
        mExScrollView = (ExScrollView) findViewById(R.id.scrollview);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new HistoryRecordAdapter(this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryRecordEntity item = (HistoryRecordEntity) parent.getAdapter().getItem(position);
                mUserCenterBiz.setUserItem(new ResponseActivityPlayRecordEntity(item.getWinner(), item.getPhoto(), item.getAccount_id()));
                mUserCenterBiz.startActivity(HistoryRunRecordActivity.this);
            }
        });

        mExScrollView.setOnScrollDoneListener(new ExScrollView.OnScrollDoneListener() {
            @Override
            public void onScrollTop() {

            }

            @Override
            public void onScrollBottom() {
                onScrollBottomLoadMoreData();
            }
        });
    }

    /**
     * 加载更多
     */
    private void onScrollBottomLoadMoreData() {
        LogUtils.i(TAG, "onScrollBottomLoadMoreData()");
        mHistoryRecordBiz.onScrollBottomLoadMore();
    }

    public void onBtnBack(View view) {
        finish();
    }

    @Override
    protected void onBtnReload() {
        mHistoryRecordBiz.onCreate();
    }

    @Override
    protected View getScrollViewWrapper() {
        return null;
    }

    @Override
    protected void onRefreshLoadData() {

    }

    @Override
    protected View getLayoutViewWrapper() {
        return null;
    }

    @Override
    protected void handleUIMessage(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.Comm_Activity.COMM_NET_START)) {
            handleStart();

        } else if (strUIAction.equals(UIMessageConts.Comm_Activity.COMM_NET_FAILED)) {
            handleFail();

        } else if (strUIAction.equals(UIMessageConts.Comm_Activity.COMM_NET_SUCCESS)) {
            handleSuccess();

        }
    }

    private void handleStart() {
        int iLoadMode = mHistoryRecordBiz.getLoadMode();
        if (iLoadMode== Constant.Comm.MODE_ENTER) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNotNetWrapper, mViewAllContent);

        } else if (iLoadMode== Constant.Comm.MODE_REFRESH) {

        } else if (iLoadMode== Constant.Comm.MODE_LOAD_MORE) {
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_START, false);

        }
    }

    private void handleFail() {
        int iLoadMode = mHistoryRecordBiz.getLoadMode();
        if (iLoadMode== Constant.Comm.MODE_ENTER) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNotNetWrapper, mViewAllContent);

        } else if (iLoadMode== Constant.Comm.MODE_REFRESH) {

        } else if (iLoadMode== Constant.Comm.MODE_LOAD_MORE) {
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_FAILED, false);

        }
    }

    private void handleSuccess() {
        int iLoadMode = mHistoryRecordBiz.getLoadMode();
        LogUtils.i(TAG, "handleSuccess(). iLoadMode=%d", iLoadMode);
        if (iLoadMode== Constant.Comm.MODE_ENTER) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNotNetWrapper, mViewAllContent);
            mAdapter.resetDataAndNotify(mHistoryRecordBiz.getData());

        } else if (iLoadMode== Constant.Comm.MODE_REFRESH) {

        } else if (iLoadMode== Constant.Comm.MODE_LOAD_MORE) {
            List<HistoryRecordEntity> data = mHistoryRecordBiz.getData();
            if (data.size() > 0) {
                handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
                mAdapter.loadMoreData(data);

            } else {
                handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, true);
            }
        }
    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }


}
