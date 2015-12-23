package com.example.liangge.indiana.ui.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.ui.widget.ExScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GridViewTest extends AppCompatActivity {

    private static final String TAG = GridViewTest.class.getSimpleName();

    private GridView mGridView;

    private GridAdapter mAdapter;

    private ExScrollView mScrollGridView;

    private View mFloatContentView;

    private View mFixMenu;

    private List<ActivityProductItemEntity> mListData = new ArrayList<>();
    private static DisplayImageOptions mDisplayImageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_test);

        initData();
        initView();
        initImageLoaderConf(this);

    }

    private void initData() {
        String url1 = "http://www.bz55.com/uploads/allimg/150309/139-150309101F7.jpg";
        String url2 = "http://pic33.nipic.com/20131008/13661616_190558208000_2.jpg";
        String url3 = "http://pic13.nipic.com/20110419/2290512_182044467100_2.jpg";
        String url4 = "http://img3.3lian.com/2013/s1/17/d/15aa.jpg";

        ActivityProductItemEntity item1 = new ActivityProductItemEntity(url1, "name1", 23, "23%");
        ActivityProductItemEntity item2 = new ActivityProductItemEntity(url2, "name2", 100, "100%");
        ActivityProductItemEntity item3 = new ActivityProductItemEntity(url3, "name3", 45, "45%");
        mListData.add(item1);
        mListData.add(item2);
        mListData.add(item3);
        for (int i=0; i<9; i++) {
            ActivityProductItemEntity item = new ActivityProductItemEntity(url4, "产品名"+i, i, i+"%");
            mListData.add(item);
        }
        mListData.add(item3);
    }

    private void initView() {
        mFloatContentView = findViewById(R.id.float_content_wrapper);
        mFixMenu = findViewById(R.id.fit_float_menu);

        mGridView = (GridView) findViewById(R.id.gridview);
        mScrollGridView = (ExScrollView) findViewById(R.id.scrollview);

        mScrollGridView.setOnFloatMenuHiddenListener(new ExScrollView.OnFloatMenuHiddenListener() {
            @Override
            public void shouldHiddle(boolean bShouldHiddle) {
                if (bShouldHiddle) {
                    mFixMenu.setVisibility(View.GONE);

                } else {
                    mFixMenu.setVisibility(View.VISIBLE);

                }
            }
        }, mFloatContentView);


        mScrollGridView.setOnScrollDoneListener(new ExScrollView.OnScrollDoneListener() {
            @Override
            public void onScrollTop() {
                LogUtils.e(TAG, "onScrollTop()");
            }

            @Override
            public void onScrollBottom() {
                LogUtils.e(TAG, "onScrollBottom()");
            }
        });

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtils.e(TAG, "firstVisibleItem=%d, visisbleItemCount=%d, totalItemCount=%d", firstVisibleItem, visibleItemCount,  totalItemCount);
            }
        });


        mAdapter = new GridAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e(TAG, "onItemClick(). position=%d, item=%s", position, mListData.get(position).toString());
            }
        });

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

/*options        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();*/
    }



    private class GridAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public GridAdapter(Context context) {
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(GridViewTest.this, R.layout.f_indiana_gridview_item, null);

                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            final ActivityProductItemEntity item = mListData.get(position);
            viewHolder.adapterData(item);

            return convertView;
        }


    }


    public static class ViewHolder {

        private ImageView imgProductImg;

        private TextView txvProductName;

        private TextView txvBingoProgress;

        private ImageButton btnShoppingCart;

        public ViewHolder(View view) {
            this.imgProductImg = (ImageView) view.findViewById(R.id.indiana_product_item_img);
            this.txvProductName = (TextView) view.findViewById(R.id.indiana_product_item_name_txv);
            this.txvBingoProgress = (TextView) view.findViewById(R.id.indiana_product_item_bingo_process_txv);
            this.btnShoppingCart = (ImageButton) view.findViewById(R.id.f_indiana_product_item_shopping_cart_btn);
        }

        public void adapterData(ActivityProductItemEntity itemInfo) {
            ImageLoader.getInstance().displayImage(itemInfo.getProductImgUrl(), this.imgProductImg, mDisplayImageOptions);
            this.txvProductName.setText(itemInfo.getName());
            this.txvBingoProgress.setText(itemInfo.getStrBingoProgress());
            this.btnShoppingCart.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    LogUtils.e(TAG, "shopping cart click");
                }
            });
        }
    }

}
