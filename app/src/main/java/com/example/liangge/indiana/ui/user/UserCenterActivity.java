package com.example.liangge.indiana.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.user.BingoRecordListViewAdapter;
import com.example.liangge.indiana.adapter.user.IndianaRecordListViewAdapter;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.user.IndianaRecordBiz;
import com.example.liangge.indiana.biz.user.UserCenterBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;
import com.example.liangge.indiana.ui.widget.ExScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 用户中心
 */
public class UserCenterActivity extends BaseActivity2 {

    private static final String TAG = UserCenterActivity.class.getSimpleName();

    private UserCenterBiz mUserCenterBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private IndianaRecordListViewAdapter mIndianaAdapter;
    private BingoRecordListViewAdapter mBingoAdapter;

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
        int iCurType = mUserCenterBiz.getCurRequestType();
        if (iCurType == UserCenterBiz.RequestType.INDIAN_RECOREDS) {
            mRadioGroup.check(mBtnTagAll.getId());
            onBtnTagAll(null);

        } else if (iCurType== UserCenterBiz.RequestType.BINGO_RECORDS) {
            mRadioGroup.check(mBtnTagBingo.getId());
            onBtnTagBingo(null);

        } else if (iCurType== UserCenterBiz.RequestType.WISH_LISTS) {
            mRadioGroup.check(mBtnTagWishList.getId());
            onBtnTagWishList(null);

        }

        //init view
        ImageLoader.getInstance().displayImage(mUserCenterBiz.getUserItem().getPhoto(), mImgUserPortrait, getUserPortraitImageConfig());
        mTxvUsername.setText(mUserCenterBiz.getUserItem().getNickname());
        mTxvUserId.setText(mUserCenterBiz.getUserItem().getUserID() + "");
    }

    private void initView() {
        mViewLayout = View.inflate(this, R.layout.activity_user_center, null);
        mViewNetHintWrapper = findViewById(R.id.activity_indianarecord_net_wrapper);
        mViewTagContentWrapper = findViewById(R.id.activity_indianarecord_tagcontent_wrapper);
        mExScrollView = (ExScrollView) findViewById(R.id.activity_indianarecord_scrollview);
        mViewLoadMoreWrapper = findViewById(R.id.load_more_wrapper);

        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_1);
        mBtnTagAll = (RadioButton) findViewById(R.id.rb_tag_all);
        mBtnTagBingo = (RadioButton) findViewById(R.id.rb_tag_bingo);
        mBtnTagWishList = (RadioButton) findViewById(R.id.rb_tag_wish_list);

        mListView = (ListView) findViewById(R.id.activity_indianarecord_listview);
        mIndianaAdapter = new IndianaRecordListViewAdapter(this);
        mBingoAdapter = new BingoRecordListViewAdapter(this);
        mListView.setAdapter(mIndianaAdapter);

        mImgUserPortrait = (ImageView) findViewById(R.id.user_portrait);
        mTxvUsername = (TextView) findViewById(R.id.username);
        mTxvUserId = (TextView) findViewById(R.id.user_id);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(TAG, "onItemClick(). position=%d", position);
                if (mUserCenterBiz.getCurRequestType()== UserCenterBiz.RequestType.INDIAN_RECOREDS) {
                    IndianaRecordEntity item = (IndianaRecordEntity) mIndianaAdapter.getItem(position);
                    mDetailInfoBiz.startActivity(UserCenterActivity.this, item.getActivityId());

                } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.BINGO_RECORDS) {
                    BingoRecordEntity item = (BingoRecordEntity) mBingoAdapter.getItem(position);
                    mDetailInfoBiz.startActivity(UserCenterActivity.this, item.getActivityId());

                } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.WISH_LISTS) {

                }

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
        mUserCenterBiz.onScrollBottomLoadMore();
    }

    private void initManager() {
        mUserCenterBiz = UserCenterBiz.getInstance(this);
        mDetailInfoBiz = DetailInfoBiz.getInstance(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mUserCenterBiz.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserCenterBiz.onDestroy();
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
            boolean isEmpty = true;
            if (mUserCenterBiz.getCurRequestType()== UserCenterBiz.RequestType.INDIAN_RECOREDS) {
                isEmpty = mUserCenterBiz.getIndianListData().size() == 0;

            } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.BINGO_RECORDS) {
                isEmpty = mUserCenterBiz.getBingoListData().size() == 0;

            } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.WISH_LISTS) {

            }

            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, isEmpty);
            adapterData(true);
        }

    }

    private void handleReloadUI(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_FAIL)) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNetHintWrapper, mViewTagContentWrapper);

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS)) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNetHintWrapper, mViewTagContentWrapper);
//            mIndianaAdapter.resetDataAndNotify(mUserCenterBiz.getIndianListData());
            adapterData(false);

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_START)) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNetHintWrapper, mViewTagContentWrapper);

        }

    }

    private void adapterData(boolean isAdapterLoadMore) {
        if (isAdapterLoadMore) {
            if (mUserCenterBiz.getCurRequestType()== UserCenterBiz.RequestType.INDIAN_RECOREDS) {
                mIndianaAdapter.loadMoreDataAndNotify(mUserCenterBiz.getIndianListData());

            } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.BINGO_RECORDS) {
                mBingoAdapter.loadMoreDataAndNotify(mUserCenterBiz.getBingoListData());

            } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.WISH_LISTS) {

            }

        } else {
            handleUILoadMore(mViewLoadMoreWrapper, Constant.Comm.LOAD_MORE_SUCCESS, false);
            if (mUserCenterBiz.getCurRequestType()== UserCenterBiz.RequestType.INDIAN_RECOREDS) {
                mListView.setAdapter(mIndianaAdapter);
                mIndianaAdapter.clear();
                mIndianaAdapter.resetDataAndNotify(mUserCenterBiz.getIndianListData());

            } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.BINGO_RECORDS) {
                mListView.setAdapter(mBingoAdapter);
                mBingoAdapter.clear();
                mBingoAdapter.loadMoreDataAndNotify(mUserCenterBiz.getBingoListData());

            } else if (mUserCenterBiz.getCurRequestType()==UserCenterBiz.RequestType.WISH_LISTS) {


            }

        }
    }


    /**
     * 夺宝记录
     * @param view
     */
    public void onBtnTagAll(View view) {
        LogUtils.i(TAG, "onBtnTagAll()");
        mListView.setAdapter(mIndianaAdapter);
        mUserCenterBiz.loadUserData(UserCenterBiz.RequestType.INDIAN_RECOREDS, false);
    }

    /**
     * 中奖记录
     * @param view
     */
    public void onBtnTagBingo(View view) {
        LogUtils.i(TAG, "onBtnTagBingo");
        mListView.setAdapter(mBingoAdapter);
        mUserCenterBiz.loadUserData(UserCenterBiz.RequestType.BINGO_RECORDS, false);
    }

    /**
     * 心愿清单
     * @param view
     */
    public void onBtnTagWishList(View view) {
        LogUtils.i(TAG, "onBtnTagWishList()");

//        mUserCenterBiz.loadUserData(UserCenterBiz.RequestType.WISH_LISTS, false);
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
        mUserCenterBiz.onCreate();
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
