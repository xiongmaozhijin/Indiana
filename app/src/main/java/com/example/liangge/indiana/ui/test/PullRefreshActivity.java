package com.example.liangge.indiana.ui.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.LastestBingoEntity;
import com.example.liangge.indiana.ui.widget.ExScrollView;
import com.example.liangge.indiana.ui.widget.RunLottoryView3;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class PullRefreshActivity extends AppCompatActivity {

    private static final String TAG = PullRefreshActivity.class.getSimpleName();

    private PtrFrameLayout mPtrFrameLayout;

    private static GridView mGridView;

    private static LatestGridViewAdapter mAdapter;

    private List<LastestBingoEntity> mListLatestDatas;

    private static DisplayImageOptions mDisplayImageOptions;

    private ExScrollView mExScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh2);

        initData();
        intiView();
        initRes();
    }

    private void initRes() {
        initImageLoaderConf(this);
    }

    private void initData() {
        mListLatestDatas = new ArrayList<>();
        String url1 = "http://f.hiphotos.baidu.com/image/pic/item/3bf33a87e950352a230666de5743fbf2b3118b85.jpg";
        String url2 = "http://b.hiphotos.baidu.com/image/pic/item/0823dd54564e925838c205c89982d158ccbf4e26.jpg";
        String url3 = "http://pic13.nipic.com/20110419/2290512_182044467100_2.jpg";
        String url4 = "http://img3.3lian.com/2013/s1/17/d/15aa.jpg";
        String url5 = "http://pic33.nipic.com/20131008/13661616_190558208000_2.jpg";

        String[] imgs = {url1, url2, url3, url4, url5};

        LastestBingoEntity item1 = new LastestBingoEntity(2313, url1, "titledesc惠普电脑1irb1", "tom", "123212", 10, System.currentTimeMillis() + 50*1000);
        LastestBingoEntity item2 = new LastestBingoEntity(41321,url2, "title乐视电视descirb2", "小李", "941212", 1, System.currentTimeMillis() + 10*1000);
        LastestBingoEntity item3 = new LastestBingoEntity(3243241, url3, "titledes小米手机irb2", "张李", "321212", 1, System.currentTimeMillis() + 100*1000);

        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);

        LastestBingoEntity entity;
        for (int i=0; i<10; i++) {
            int random = 30 * 1000; //TODO
            long time1 = System.currentTimeMillis() - random;
            long time2 = System.currentTimeMillis() + random;

            entity = new LastestBingoEntity(3413,imgs[i%5], "titleDescribe"+i, "user_for"+i, random+"", i, time1);

            mListLatestDatas.add(entity);
        }

        item1 = new LastestBingoEntity(331231, url4, "titledesc惠普电脑1irb1", "tom", "123212", 10, System.currentTimeMillis() - 50*1000);
        item2 = new LastestBingoEntity(2313,url5, "title乐视电视descirb2", "小李", "941212", 1, System.currentTimeMillis() - 10*1000);
        item3 = new LastestBingoEntity(23213, url4, "titledes小米手机irb2", "张李", "321212", 1, System.currentTimeMillis() - 100*1000);

        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);
        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);

    }




    private void intiView() {
//        initRefreshView();
        initGridView();
        initExScrollView();

    }

    private void initExScrollView() {
        mExScrollView = (ExScrollView) findViewById(R.id.scrollview_test);
        mExScrollView.setOnScrollDoneListener(new ExScrollView.OnScrollDoneListener() {
            @Override
            public void onScrollTop() {
                LogUtils.w(TAG, "onScrollTop()");
            }

            @Override
            public void onScrollBottom() {
                LogUtils.w(TAG, "onScrollBottom()");

            }
        });
    }

    private void initGridView() {
        mGridView = (GridView) findViewById(R.id.pull_refresh_gridview);
        mAdapter = new LatestGridViewAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true));
    }


    private void initRefreshView() {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.pull_refresh_wrapper);
        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameLayout);
        header.setBackgroundResource(android.R.color.holo_blue_bright);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrameLayout.setPullToRefresh(false);
        // default is true
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);


        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 3000);
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

    }





    private class LatestGridViewAdapter extends BaseAdapter {

        private Context mContext;

        public LatestGridViewAdapter(Context context) {
            this.mContext = context;

        }

        @Override
        public int getCount() {
            return mListLatestDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mListLatestDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            LastestBingoEntity lastestItem = mListLatestDatas.get(position);

            LogUtils.e(TAG, "getView(). position=%d, itemInfo=%s", position, lastestItem.toString() );

            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.f_lastest_gridview_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }

            viewHolder.adapterDatas(lastestItem);

            return convertView;
        }


    }

    private static class ViewHolder {

        /** 是否已经开奖 */
        private boolean isAreadyRunLottory;

        private ImageView latestProductImg;
        private TextView latestProductDescribe;

        /** 正在揭晓Wrapper View */
        private View runLottoryWrapper;
        /** 正在揭晓提示 */
        private RunLottoryView3 runLottoryHint;

        /** 揭晓信息Wrapper View */
        private View bingoInfoWrapper;
        private TextView bingoUser;
        private TextView buyCounts;
        private TextView luckyNumber;
        private TextView alreadyRunLottoryTime;

        public ViewHolder(View view) {
            initItemview(view);

        }

        private void initItemview(View view) {
            latestProductImg = (ImageView) view.findViewById(R.id.latest_product_item_img);
            latestProductDescribe = (TextView) view.findViewById(R.id.latest_product_item_describe);
            runLottoryWrapper = view.findViewById(R.id.laset_item_runlottory_wrapper);
            bingoInfoWrapper = view.findViewById(R.id.latest_bingo_info_wrapper);

            bingoUser = (TextView) view.findViewById(R.id.latest_bingo_info_bingouser_txv);
            buyCounts = (TextView) view.findViewById(R.id.latest_bingo_info_buycounts_txv);
            luckyNumber = (TextView) view.findViewById(R.id.latest_bingo_info_luncynumber_txv);
            alreadyRunLottoryTime = (TextView) view.findViewById(R.id.latest_bingo_info_runlottorytime_txv);

            runLottoryHint = (RunLottoryView3) view.findViewById(R.id.latest_runlottorytime_hint_txv);

        }

        /**
         * 适配数据
         * @param itemInfo
         */
        public void adapterDatas(LastestBingoEntity itemInfo) {
//            LogUtils.w(TAG, "itemInfo=%s", itemInfo.toString() );
            boolean isAreadyRunLottory = itemInfo.isAlreadyRunLottory();

            adapterCommView(itemInfo);

            if (isAreadyRunLottory) {
                adapterDataBingoData(itemInfo);

            } else {
                adapterDataRunLottory(itemInfo);

            }


        }

        /**
         * 适配公共部分
         * @param itemInfo
         */
        private void adapterCommView(LastestBingoEntity itemInfo) {
            ImageLoader.getInstance().displayImage(itemInfo.getProductUrl(), latestProductImg, mDisplayImageOptions);
            latestProductDescribe.setText(itemInfo.getHumanProductDescribe() );
        }

        /**
         * 适配开奖信息
         * @param itemInfo
         */
        private void adapterDataBingoData(LastestBingoEntity itemInfo) {
            runLottoryWrapper.setVisibility(View.GONE);
            bingoInfoWrapper.setVisibility(View.VISIBLE);

            bingoUser.setText(itemInfo.getBingoUser());
            buyCounts.setText(itemInfo.getHumanBuyTimes() );
            luckyNumber.setText(itemInfo.getLuckyNumeber());
            alreadyRunLottoryTime.setText(itemInfo.getHumanAlreadyRunLotteryTime() );
        }

        /**
         * 适配正在揭晓情况数据
         * @param itemInfo
         */
        private void adapterDataRunLottory(LastestBingoEntity itemInfo) {
            runLottoryWrapper.setVisibility(View.VISIBLE);
            bingoInfoWrapper.setVisibility(View.GONE);

            runLottoryHint.init(itemInfo);
            runLottoryHint.setOnTimesUpListener(new SimpleOnTimeUpListenerAdapter());
        }


    }


    private static class SimpleOnTimeUpListenerAdapter implements RunLottoryView3.OnTimesUpListener {

        @Override
        public void onTimesUp(LastestBingoEntity lastestBingoEntity) {
            LogUtils.w(TAG, "onTimeUp(). info=%s", lastestBingoEntity.toString() );
            mGridView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }


}
