package com.example.liangge.indiana.ui.Inner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.ShoppingCartListViewAdapter;
import com.example.liangge.indiana.biz.inner.CategroyDetailBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页
 */
public class SearchActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = SearchActivity.class.getSimpleName();

    /** 搜索 */
    private EditText mEdtSearch;

    private ListView mListView;

    private SearchAdapter mSearchAdapter;

    private CategroyDetailBiz mCategroyDetailBiz;

    private static List<String> mHotList = new ArrayList<>();

    private SlaveRequestHotSearchThread mSlaveRequestHotSearchThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initManager();
        initRes();

        initState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSlaveRequestHotSearchThread.cancelAll();

    }

    private void initState() {
        if (!mSlaveRequestHotSearchThread.isWorking()) {
            mSlaveRequestHotSearchThread = new SlaveRequestHotSearchThread();
            mSlaveRequestHotSearchThread.start();
        }
    }

    private void initManager() {
        mCategroyDetailBiz = CategroyDetailBiz.getInstance(this);

    }

    private void initRes() {
        mSlaveRequestHotSearchThread = new SlaveRequestHotSearchThread();
    }

    private void initView() {
        mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mListView = (ListView) findViewById(R.id.listview);
        mSearchAdapter = new SearchAdapter();
        mListView.setAdapter(mSearchAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = mHotList.get(position);
                mEdtSearch.setText(item);
                searchProduct();
                LogUtils.i(TAG, "item click. item=%s", item);
            }
        });

        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //hide key input
                    InputMethodManager imm = (InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //do search
                    searchProduct();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 搜索商品
     */
    private void searchProduct() {
        String keyword = mEdtSearch.getText().toString();
        if (TextUtils.isEmpty(keyword)) {
            String hint = getResources().getString(R.string.activity_search_keyword_hint_1);
            LogUtils.toastShortMsg(this, hint);

        } else {
            String titleFormat = getResources().getString(R.string.activity_category_search);
            String title = String.format(titleFormat, keyword);
            mCategroyDetailBiz.setRequestData(CategroyDetailBiz.IRequestCategory.SEARCH_PRODUCT, keyword);

            mCategroyDetailBiz.setTitle(title);
            Intent i = new Intent(SearchActivity.this, CategoryListActivity.class);
            startActivity(i);
        }

        LogUtils.i(TAG, "searchProduct().keyword=%s", keyword == null ? "null" : keyword);
    }

    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }


    public void onBtnBack(View view) {
        finish();
    }


    private class SlaveRequestHotSearchThread extends NetRequestThread {

        private static final String R_TAG = "SlaveRequestHotSearchThread";

        @Override
        protected void notifyStart() {
            super.notifyStart();
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            if (mSearchAdapter!=null) {
                mSearchAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            String hint = getResources().getString(R.string.activity_search_net_error);
            LogUtils.toastShortMsg(SearchActivity.this, hint);
        }

        @Override
        protected String getJsonBody() {
            LogUtils.i(TAG, "getJsonBody()=%s", "");
            return "";
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(R_TAG, "onResponse=%s", s);
            Gson gson = new Gson();
            mHotList = gson.fromJson(s, new TypeToken<List<String>>(){}.getType());

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(R_TAG, "volleryError=%s", volleyError.getLocalizedMessage() );

        }

        @Override
        protected String getRequestTag() {
            return R_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            LogUtils.i(R_TAG, "api=%s", Constant.WebServiceAPI.REQUEST_HOT_SEARCH);
            return Constant.WebServiceAPI.REQUEST_HOT_SEARCH;
        }
    }





    private class SearchAdapter extends BaseAdapter {


        public SearchAdapter() {
        }

        @Override
        public int getCount() {
            return mHotList.size();
        }

        @Override
        public Object getItem(int position) {
            return mHotList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder viewHolder;
            if (view==null) {
                view = View.inflate(SearchActivity.this, R.layout.search_listview_item, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final String item = mHotList.get(position);
            viewHolder.adapterData(item);

            return view;
        }

    }



    private static class ViewHolder {
        private TextView mTxvDesc;

        public ViewHolder(View view) {
            mTxvDesc = (TextView) view.findViewById(R.id.txv_desc);
        }

        public void adapterData(String item) {
            mTxvDesc.setText(item==null?"null":item);

        }

    } //end viewholder


}
