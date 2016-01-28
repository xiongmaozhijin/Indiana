package com.example.liangge.indiana.ui.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.ShoppingCartListViewAdapter;
import com.example.liangge.indiana.comm.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ScrollListViewActivity extends Activity {


    private static final String TAG = "ScrollListViewActivity";

    private ListView mListView;
    private MyAdapter mAdapter;
    private List<Entity> mData = new ArrayList<>();
    private DisplayImageOptions mDisplayImageOptions;

    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_list_view);

        initData();
        initView();
        initImageLoaderConf(this);


    }

    private void initData() {
        List<Entity> list = new ArrayList<>();
        int base = mData.size();
        int size = Constant.IMAGES.length;
        for (int i=0; i<8; i++) {
            Entity item = new Entity("http://www.pp3.cn/uploads/allimg/111128/1414302040-9.jpg", "text"+i);
            list.add(item);
        }
        mData.addAll(list);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        ((ExListViewTest)mListView).setScrollView(mScrollView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.toastShortMsg(ScrollListViewActivity.this, "onItemClick.postion" + position);
                LogUtils.e(TAG, "onItemClick=%d", position);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e(TAG, "onItemLongClick=%d", position);
                return true;
            }
        });


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtils.e(TAG+1, "scrollState=%d", scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtils.e(TAG+2, "firstVisibleItem=%d, visibleItemCount=%d, totalItemCount=%d", firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }

    public void onBtnAddData(View view) {
        loadData();

    }

    private void loadData() {
        List<Entity> list = new ArrayList<>();
        int base = mData.size();
        int size = Constant.IMAGES.length;
        for (int i=0; i<8; i++) {
            String url = i%2==0 ? "http://www.pp3.cn/uploads/allimg/111128/1414302040-9.jpg" : "Constant.IMAGES[(base+i)%size]";
            Entity item = new Entity(url, "text"+(i+base));
            list.add(item);
        }
        mData.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void initImageLoaderConf(Context context) {
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.main_banner_img_load_empty_uri)
                .showImageOnFail(R.drawable.main_banner_img_load_fail)
                .showImageOnLoading(R.drawable.main_product_item_img_onloading)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();

    }


    private void dump() {
        mListView.setSelection(mListView.getBottom());

    }


    public void onBtnInfo(View view) {
        int getBottom = mListView.getBottom();
        int scrollY = mListView.getScrollY();
        int getFirstVisItem = mListView.getFirstVisiblePosition();
        int getLastVisItem = mListView.getLastVisiblePosition();

        LogUtils.e(TAG, "getBottom=%d, scrollY=%d, firstItem=%d, lastItem=%d", getBottom, scrollY, getFirstVisItem, getLastVisItem);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();

        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LogUtils.w(TAG, "getView. postion=%d", position);

            ViewHolder viewHolder;
            if (convertView== null) {
                convertView = View.inflate(ScrollListViewActivity.this, R.layout.listview_item_test, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Entity item = mData.get(position);
            viewHolder.adapter(item);

            return convertView;
        }
    }

    private class ViewHolder {
        private ImageView img;
        private TextView txv;
        public ViewHolder(View view) {
            img = (ImageView) view.findViewById(R.id.imgview);
            txv = (TextView) view.findViewById(R.id.textview);
        }
        public void adapter(Entity item) {
            ImageLoader.getInstance().displayImage(item.getImgUrl(), img, mDisplayImageOptions);
            txv.setText(item.getTitle());
        }
    }

    private static class Entity {
        private String imgUrl;
        private String title;

        public Entity(String imgUrl, String title) {
            this.imgUrl = imgUrl;
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
