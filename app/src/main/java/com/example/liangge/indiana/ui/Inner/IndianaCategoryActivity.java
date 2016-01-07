package com.example.liangge.indiana.ui.Inner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.inner.IndianaCategoryAdapter;
import com.example.liangge.indiana.biz.inner.CategroyDetailBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.inner.CategoryDetailEntitiy;
import com.example.liangge.indiana.model.inner.IndianaCategoryEntity;
import com.example.liangge.indiana.ui.BaseUIActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 分类浏览
 */
public class IndianaCategoryActivity extends Activity {

    private static final String TAG = IndianaCategoryActivity.class.getSimpleName();

    /** Titlebar */
    private View mViewTittleBarWrapper;

    /** 搜索 */
    private View mViewSearchWrapper;

    /*全部商品*/
    private View mViewAllCategoryWrapper;

    /*类别列表*/
    private ListView mListView;

    private IndianaCategoryAdapter mAdapter;

    /** 加载网络类别数据 */
    private SlaveLoadDataThread mSlaveLoadDataThread;

    private CategroyDetailBiz mCategroyDetailBiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiana_category);

        initManager();
        initTitleBar();
        initView();
        initState();
        initNetRequest();
    }

    private void initManager() {
        mCategroyDetailBiz = CategroyDetailBiz.getInstance(this);
    }

    private void initState() {
        mViewAllCategoryWrapper.setVisibility(View.INVISIBLE);
    }

    /**
     * 网络请求
     */
    private void initNetRequest() {
        LogUtils.i(TAG, "initNetRequest()");

        mSlaveLoadDataThread = new SlaveLoadDataThread();
        mSlaveLoadDataThread.start();

    }

    private void cancelNetRequest() {
        if (mSlaveLoadDataThread != null) {
            mSlaveLoadDataThread.cancelAll();
            mSlaveLoadDataThread = null;
        }
    }


    private void initView() {
        mViewSearchWrapper = findViewById(R.id.category_search_wrapper);
        mViewAllCategoryWrapper = findViewById(R.id.category_all_wrapper);
        mListView = (ListView) findViewById(R.id.category_listview);
        mAdapter = new IndianaCategoryAdapter(this);
        mListView.setAdapter(mAdapter);

        mViewSearchWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索
                LogUtils.i(TAG, "search activity");
            }
        });


        mViewAllCategoryWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //全部商品
                LogUtils.i(TAG, "all category");
                String title = getResources().getString(R.string.category_list_all_category);
                mCategroyDetailBiz.setTitle(title);
                mCategroyDetailBiz.setRequestData(0);
                Intent intent = new Intent(IndianaCategoryActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview item click
                LogUtils.i(TAG, "position=%d", position);
                final IndianaCategoryEntity item = (IndianaCategoryEntity) parent.getAdapter().getItem(position);
                mCategroyDetailBiz.setTitle(item.getCategory_name());
                mCategroyDetailBiz.setRequestData(item.getCategory_id());
                Intent intent = new Intent(IndianaCategoryActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initTitleBar() {
        mViewTittleBarWrapper = findViewById(R.id.titlebar);
        TextView txvTitle = (TextView) mViewTittleBarWrapper.findViewById(R.id.titlebar_title);
        String title = getResources().getString(R.string.activity_category_title);
        txvTitle.setText(title);

        mViewTittleBarWrapper.findViewById(R.id.titlebar_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelNetRequest();
    }


    /**
     * 请求类别数据
     */
    private class SlaveLoadDataThread extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveLoadDataThread";

        @Override
        protected String getJsonBody() {
            return "";
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "SlaveLoadDataThread#onResponse=%s", s);
            Gson gson = new Gson();
            List<IndianaCategoryEntity> list = gson.fromJson(s, new TypeToken<List<IndianaCategoryEntity>>(){}.getType());
            onNetGetListData(list);
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "volleyError=%s", volleyError.getLocalizedMessage());
            String hint = getResources().getString(R.string.activity_category_net_error);
            LogUtils.toastShortMsg(IndianaCategoryActivity.this, hint);
        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_CATEGORY;
        }
    }

    /**
     * 获取网络返回的数据
     * @param list
     */
    private void onNetGetListData(final List<IndianaCategoryEntity> list) {
        if (list != null) {
            mViewAllCategoryWrapper.post(new Runnable() {
                @Override
                public void run() {
                    mViewAllCategoryWrapper.setVisibility(View.VISIBLE);
                    mAdapter.reSetDataAndNotify(list);
                }
            });
        }

    }


}
;