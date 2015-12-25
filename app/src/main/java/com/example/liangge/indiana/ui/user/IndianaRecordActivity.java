package com.example.liangge.indiana.ui.user;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.user.IndianaRecordListViewAdapter;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.user.IndianaRecordBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;
import com.example.liangge.indiana.ui.BaseActivity;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.ProductDetailInfoActivity;

/**
 * 夺宝记录
 */
public class IndianaRecordActivity extends BaseActivity2 {

    private static final String TAG = IndianaRecordActivity.class.getSimpleName();

    private IndianaRecordBiz mIndianaRecordBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private IndianaRecordListViewAdapter mAdapter;

    /** 网络提示包裹View */
    private View mViewNetHintWrapper;

    private View mViewTagContentWrapper;

    private ListView mListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiana_record);

        initView();
        initManager();
    }

    private void initView() {
        mViewNetHintWrapper = findViewById(R.id.activity_indianarecord_net_wrapper);
        mViewTagContentWrapper = findViewById(R.id.activity_indianarecord_tagcontent_wrapper);

        mListView = (ListView) findViewById(R.id.activity_indianarecord_listview);
        mAdapter = new IndianaRecordListViewAdapter(this);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i(TAG, "onItemClick(). position=%d", position);
                final IndianaRecordEntity item = (IndianaRecordEntity) parent.getAdapter().getItem(position);
                mDetailInfoBiz.setActivityId(item.getActivityId());
                Intent i = new Intent(IndianaRecordActivity.this, ProductDetailInfoActivity.class);
                startActivity(i);
            }
        });


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

    }

    private void handleReloadUI(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_FAIL)) {
            mViewNetHintWrapper.setVisibility(View.VISIBLE);
            mViewTagContentWrapper.setVisibility(View.GONE);
            mViewNetHintWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.GONE);
            mViewNetHintWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.VISIBLE);

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS)) {
            mViewNetHintWrapper.setVisibility(View.GONE);
            mViewTagContentWrapper.setVisibility(View.VISIBLE);
            //TODO

        } else if (strUIAction.equals(UIMessageConts.IndianaRecord.M_RELOAD_START)) {
            mViewNetHintWrapper.setVisibility(View.VISIBLE);
            mViewTagContentWrapper.setVisibility(View.GONE);
            mViewNetHintWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.VISIBLE);
            mViewNetHintWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.GONE);

        }

    }

    //全部
    public void onBtnTagAll(View view) {
        LogUtils.i(TAG, "onBtnTagAll()");

    }

    //进行中
    public void onBtnTagIng(View view) {
        LogUtils.i(TAG, "onBtnTagIng");

    }

    //已揭晓
    public void onBtnDone(View view) {
        LogUtils.i(TAG, "onBtnDone()");

    }


    public void onBtnBack(View view) {
        LogUtils.i(TAG, "onBtnBack()");
        finish();
    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }



}
