package com.example.liangge.indiana.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.user.IndianaRecordListViewAdapter;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.user.IndianaRecordBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;
import com.example.liangge.indiana.ui.widget.ExScrollView;

import java.util.List;

/**
 * 用户中心
 */
public class UserCenterActivity extends BaseActivity2 {

    private static final String TAG = UserCenterActivity.class.getSimpleName();

    private IndianaRecordBiz mIndianaRecordBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private IndianaRecordListViewAdapter mAdapter;

    /** 网络提示包裹View */
    private View mViewNetHintWrapper;

    private View mViewTagContentWrapper;

    private ListView mListView;

    private View mViewLayout;

    private ExScrollView mExScrollView;

    private View mViewLoadMoreWrapper;

    private RadioGroup mRadioGroup;
    private RadioButton mBtnTagAll;
    private RadioButton mBtnTagBingo;
    private RadioButton mBtnTagWishList;

    private ImageView mImgUserPortrait;
    private TextView mTxvUsername;
    private TextView mTxvUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        initView();
        initManager();

        initOnCreate();
    }

    private void initOnCreate() {
//        mIndianaRecordBiz.onCreate();
        String curTag = mIndianaRecordBiz.getCurRequestTag();
        if (curTag == Constant.IndianaRecord.TAG_ALL) {
            mRadioGroup.check(mBtnTagAll.getId());
            onBtnTagAll(null);

        } else if (curTag== Constant.IndianaRecord.TAG_ING) {
            mRadioGroup.check(mBtnTagBingo.getId());
            onBtnTagBingo(null);

        } else if (curTag==Constant.IndianaRecord.TAG_DONE) {
            mRadioGroup.check(mBtnTagWishList.getId());
            onBtnTagWishList(null);

        }

    }

    private void initView() {
        mViewLayout = View.inflate(this, R.layout.activity_indiana_record, null);
        mViewNetHintWrapper = findViewById(R.id.activity_indianarecord_net_wrapper);
        mViewTagContentWrapper = findViewById(R.id.activity_indianarecord_tagcontent_wrapper);
        mExScrollView = (ExScrollView) findViewById(R.id.activity_indianarecord_scrollview);
        mViewLoadMoreWrapper = findViewById(R.id.load_more_wrapper);

        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mBtnTagAll = (RadioButton) findViewById(R.id.rb_tag_all);
        mBtnTagBingo = (RadioButton) findViewById(R.id.rb_tag_ing);
        mBtnTagWishList = (RadioButton) findViewById(R.id.rb_tag_done);

        mListView = (ListView) findViewById(R.id.activity_indianarecord_listview);
        mAdapter = new IndianaRecordListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        mImgUserPortrait = (ImageView) findViewById(R.id.user_portrait);
        mTxvUsername = (TextView) findViewById(R.id.username);
        mTxvUserId = (TextView) findViewById(R.id.user_id);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(TAG, "onItemClick(). position=%d", position);
                final IndianaRecordEntity item = (IndianaRecordEntity) parent.getAdapter().getItem(position);
                mDetailInfoBiz.setActivityId(item.getActivityId());
                Intent i = new Intent(UserCenterActivity.this, ProductDetailInfoActivity.class);
                startActivity(i);
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
     * 滚动到底部加载更多
     */
    private void onScrollBottomLoadMore() {
        LogUtils.i(TAG, "onScrollBottomLoadMore()");
        mIndianaRecordBiz.onScrollBottomLoadMore();
    }

    private void initManager() {
        mIndianaRecordBiz = IndianaRecordBiz.getInstance(this);
        mDetailInfoBiz = DetailInfoBiz.getInstance(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mIndianaRecordBiz.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIndianaRecordBiz.onDestroy();
    }

    @Override
    protected void handleUIMessage(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_FAIL)
                || strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS)
                || strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_START)) {
            handleReloadUI(strUIAction);

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_FAIL_MORE)
                || strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_START_MORE)
                || strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS_MORE)) {
            handleLoadMoreUI(strUIAction);

        }

    }

    private void handleLoadMoreUI(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_FAIL_MORE)) {
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_FAILED, false);

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_START_MORE)) {
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_START, false);

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS_MORE)) {
            List<IndianaRecordEntity> data = mIndianaRecordBiz.getData();
            boolean isEmpty = data.size()==0;
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, isEmpty);
            mAdapter.loadMoreDataAndNotify(data);
        }



    }

    private void handleReloadUI(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_FAIL)) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNetHintWrapper, mViewTagContentWrapper);

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS)) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNetHintWrapper, mViewTagContentWrapper);
            mAdapter.resetDataAndNotify(mIndianaRecordBiz.getData());

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_START)) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNetHintWrapper, mViewTagContentWrapper);

        }

    }

    /**
     * 夺宝记录
     * @param view
     */
    public void onBtnTagAll(View view) {
        LogUtils.i(TAG, "onBtnTagAll()");
        mIndianaRecordBiz.loadIndianaRecord(Constant.IndianaRecord.TAG_ALL, false);
    }

    /**
     * 中奖记录
     * @param view
     */
    public void onBtnTagBingo(View view) {
        LogUtils.i(TAG, "onBtnTagBingo");
        mIndianaRecordBiz.loadIndianaRecord(Constant.IndianaRecord.TAG_ING, false);
    }

    /**
     * 心愿清单
     * @param view
     */
    public void onBtnTagWishList(View view) {
        LogUtils.i(TAG, "onBtnTagWishList()");
        mIndianaRecordBiz.loadIndianaRecord(Constant.IndianaRecord.TAG_DONE, false);
    }


    public void onBtnBack(View view) {
        LogUtils.i(TAG, "onBtnBack()");
        finish();
    }


    @Override
    protected String getDebugTag() {
        return TAG;
    }


    @Override
    protected void onBtnReload() {
        LogUtils.w(TAG, "onBtnReload()");
        mIndianaRecordBiz.onCreate();
    }

    @Override
    protected View getScrollViewWrapper() {
        return mExScrollView;
    }

    @Override
    protected void onRefreshLoadData() {
        LogUtils.w(TAG, "onRefreshLoadData()");
    }

    @Override
    protected View getLayoutViewWrapper() {
        return null;
    }
}
