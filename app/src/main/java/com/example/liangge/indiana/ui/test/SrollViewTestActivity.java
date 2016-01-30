package com.example.liangge.indiana.ui.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.ui.widget.ExGridViewScrollDone;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SrollViewTestActivity extends AppCompatActivity {

    private static final String TAG = "SrollViewTestActivity";

    private ExGridViewScrollDone mGridView;
    private MyAdapter mAdapter;
    private List<Entity> mData = new ArrayList<>();
    private DisplayImageOptions mDisplayImageOptions;


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
        for (int i=0; i<7; i++) {
            Entity item = new Entity("http://www.pp3.cn/uploads/allimg/111128/1414302040-9.jpg", "text"+i);
            list.add(item);
        }
        mData.addAll(list);
    }

    private void initView() {
        mGridView = (ExGridViewScrollDone) findViewById(R.id.gridview);


        View header = View.inflate(this, R.layout.layout_header_footer_test, null);
        final View foot = View.inflate(this, R.layout.layout_header_footer_test, null);
        foot.setVisibility(View.GONE);

        mGridView.setNumColumns(2);
        mGridView.addHeaderView(header, null, true);
        mGridView.addFooterView(foot, null, false);
        mGridView.setOnTouchScrollDoneListener(new ExGridViewScrollDone.OnTouchScrollDoneListener() {
            @Override
            public void onTouchScrollBottom() {
                foot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTouchScrollTop() {
                foot.setVisibility(View.GONE);
            }
        });





        mAdapter = new MyAdapter();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.toastShortMsg(SrollViewTestActivity.this, "onItemClick.postion" + position);
                LogUtils.e(TAG, "onItemClick=%d", position);
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e(TAG, "onItemLongClick=%d", position);
                return true;
            }
        });






    /*
     * scrollState值：
     * 当屏幕停止滚动时为SCROLL_STATE_IDLE = 0；
     * 当屏幕滚动且用户使用的触碰或手指还在屏幕上时为SCROLL_STATE_TOUCH_SCROLL = 1；
     * 由于用户的操作，屏幕产生惯性滑动时为SCROLL_STATE_FLING = 2
     */

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtils.e(TAG + 1, "scrollState=%d", scrollState);
            }

    /*
     * firstVisibleItem:表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置(下标从0开始)
     * visibleItemCount:表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
     * totalItemCount:表示ListView的ListItem总数
     * listView.getFirstVisiblePosition()表示在现时屏幕第一个ListItem(第一个ListItem部分显示也算)在整个ListView的位置(下标从0开始)
     * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem(最后ListItem要完全显示出来才算)在整个ListView的位置(下标从0开始)
     */

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtils.e(TAG + 2, "firstVisibleItem=%d, visibleItemCount=%d, totalItemCount=%d", firstVisibleItem, visibleItemCount, totalItemCount);
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
        for (int i=0; i<7; i++) {
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
        mGridView.setSelection(mGridView.getBottom());

    }


    public void onBtnInfo(View view) {
        int getBottom = mGridView.getBottom();
        int scrollY = mGridView.getScrollY();
        int getFirstVisItem = mGridView.getFirstVisiblePosition();
        int getLastVisItem = mGridView.getLastVisiblePosition();
//        View firstVisibleView = mGridView.getChildAt(mGridView.getFirstVisiblePosition());
        int childCount = mGridView.getChildCount();
        int lastViewtop = mGridView.getChildAt(mGridView.getLastVisiblePosition()- mGridView.getFirstVisiblePosition()).getTop();

        LogUtils.e(TAG, "getBottom=%d, scrollY=%d, firstItem=%d, lastItem=%d", getBottom, scrollY, getFirstVisItem, getLastVisItem);
        LogUtils.e(TAG, "childCnt=%d, lastViewtop=%d", childCount, lastViewtop);
//        LogUtils.e(TAG, "firstView.top=%d", firstVisibleView.getTop());


        findViewById(R.id.txv1).setVisibility(View.VISIBLE);
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
                convertView = View.inflate(SrollViewTestActivity.this, R.layout.listview_item_test, null);
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
