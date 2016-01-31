package com.example.liangge.indiana.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.LastestBingoEntity;
import com.example.liangge.indiana.ui.test.Constant;
import com.example.liangge.indiana.ui.widget.RunLottoryView4;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/17.
 */
public class LatestProductGridViewAdapter extends BaseAdapter{

    private static final String TAG = LatestProductGridViewAdapter.class.getSimpleName();

    private Context mContext;

    private List<LastestBingoEntity> mListLatestDatas = new ArrayList<>();

    private static DisplayImageOptions mDisplayImageOptions;

    private static LatestProductGridViewAdapter.OnTimeUpListener mOnTimeUpListener;

    /** 倒计时资源 */
    private static SlaveCntDwnThread mSlaveCntDwnThread;

    public LatestProductGridViewAdapter(Context context) {
        this.mContext = context;
        initRes(context);
    }


    /**
     * 揭晓时间到达监听
     */
    public interface OnTimeUpListener {
        /**
         * 揭晓时间达到回调
         * @param lastestBingoEntity
         */
        void onTimeUp(LastestBingoEntity lastestBingoEntity);


    }

    /**
     * 设置监听揭晓时间回调
     * @param listener
     */
    public void setOnTimeUpListener(LatestProductGridViewAdapter.OnTimeUpListener listener) {
        this.mOnTimeUpListener = listener;

    }





    private void initRes(Context context) {
        initImageLoaderConf(context);
        initCntDownRes();
    }

    /**
     * 初始化倒计时资源
     */
    private void initCntDownRes() {
        LogUtils.i(TAG, "initCntDownRes()");;
        mSlaveCntDwnThread = new SlaveCntDwnThread();
        mSlaveCntDwnThread.start();
    }


    /**
     * 重置数据并更新
     * @param listProducts
     */
    public void setDatasAndNotify(List<LastestBingoEntity> listProducts) {
        this.mListLatestDatas = listProducts;
        mSlaveCntDwnThread.resetRunningLatestData(mListLatestDatas);;
        this.notifyDataSetChanged();
    }

    public void loadMoreDataAndNotify(List<LastestBingoEntity> newList) {
        if (newList != null) {
            LastestBingoEntity item ;
            for (int i=0, len=newList.size(); i<len; i++) {
                item = newList.get(i);
                this.mListLatestDatas.add(item);
            }

            mSlaveCntDwnThread.resetRunningLatestData(mListLatestDatas);
            this.notifyDataSetChanged();
        }
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

        LogUtils.v(TAG, "getView(). position=%d, itemInfo=%s", position, lastestItem.toString());

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.f_lastest_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.adapterDatas(lastestItem, parent, position);

        return convertView;
    }




    private static class ViewHolder {

        /** 是否已经开奖 */
        private boolean isAreadyRunLottory;

        private ImageView latestProductImg;
        private TextView latestProductDescribe;

        /** 正在揭晓Wrapper View */
        private View runLottoryWrapper;
        /** 正在揭晓提示 */
        private RunLottoryView4 runLottoryHint;

        /** 揭晓信息Wrapper View */
        private View bingoInfoWrapper;
        private TextView bingoUser;
        private TextView buyCounts;
        private TextView luckyNumber;
        private TextView alreadyRunLottoryTime;

        private View mViewTenYuanHint;

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

            mViewTenYuanHint = view.findViewById(R.id.ten_yuan_hint);

            runLottoryHint = (RunLottoryView4) view.findViewById(R.id.latest_runlottorytime_hint_txv);

        }

        /**
         * 适配数据
         * @param itemInfo
         */
        public void adapterDatas(LastestBingoEntity itemInfo, ViewGroup parent, int position) {
//            LogUtils.w(TAG, "itemInfo=%s", itemInfo.toString() );
            boolean isAreadyRunLottory = itemInfo.isAlreadyRunLottory();

            adapterCommView(itemInfo);

            if (isAreadyRunLottory) {
                adapterDataBingoData(itemInfo);

            } else {
                adapterDataRunLottory(itemInfo,parent, position);

            }


        }

        /**
         * 适配公共部分
         * @param itemInfo
         */
        private void adapterCommView(LastestBingoEntity itemInfo) {
            ImageLoader.getInstance().displayImage(itemInfo.getProductUrl(), latestProductImg, mDisplayImageOptions);
            latestProductDescribe.setText(itemInfo.getHumanProductDescribe());

            runLottoryHint.clearRes();

            bingoUser.setText("-");
            buyCounts.setText("-");
            luckyNumber.setText("----");
            alreadyRunLottoryTime.setText("xxxx-xx-xx");

            if (itemInfo.getMinimum_share() == 10) {
                mViewTenYuanHint.setVisibility(View.VISIBLE);

            } else {
                mViewTenYuanHint.setVisibility(View.INVISIBLE);

            }

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
            luckyNumber.setText(itemInfo.getLuckyNumber());
            alreadyRunLottoryTime.setText(itemInfo.getHumanAlreadyRunLotteryTime() );
        }

        /**
         * 适配正在揭晓或计算中的情况数据
         * @param itemInfo
         * @param parent
         */
        private void adapterDataRunLottory(LastestBingoEntity itemInfo, ViewGroup parent, int position) {
//            if (position == parent.getChildCount() ) {
                runLottoryWrapper.setVisibility(View.VISIBLE);
                bingoInfoWrapper.setVisibility(View.GONE);

                runLottoryHint.init2(itemInfo, SlaveCntDwnThread.L_CALC_SPEED);
                runLottoryHint.setOnTimesUpListener(new SimpleOnTimeUpListenerAdapter());
//            }

        }


    }



    private static class SimpleOnTimeUpListenerAdapter implements RunLottoryView4.OnTimesUpListener {

        @Override
        public void onTimesUp(LastestBingoEntity lastestBingoEntity) {
            LogUtils.w(TAG, "onTimeUp(). info=%s", lastestBingoEntity.toString() );
            if (mOnTimeUpListener != null) {
                mOnTimeUpListener.onTimeUp(lastestBingoEntity);

            }

        }

    }



    //计算部分


    /**
     * 倒计时统一线程
     */
    private static class SlaveCntDwnThread extends Thread {

        /** 计算速率 */
        private static final long L_CALC_SPEED = 47;

        private List<LastestBingoEntity> mRunningLatestData = new ArrayList<>();


        //重置资源
        private List<LastestBingoEntity> mResetData = new ArrayList<>();
        private boolean bIsNeedReset = false;

        /**
         * 重置资源，当传入为null时，清空资源
         * @param runningLatestData
         */
        public void resetRunningLatestData(List<LastestBingoEntity> runningLatestData) {
            mResetData.clear();
            LastestBingoEntity item;
            if (runningLatestData != null) {
                for (int i=0, len=runningLatestData.size(); i<len; i++) {
                    item = runningLatestData.get(i);
                    mResetData.add(item);
                }

            }

            bIsNeedReset = true;
        }




        @Override
        public void run() {
            super.run();
            LogUtils.w(TAG, "CntDwn Slave Start..run()...");

            List<LastestBingoEntity> inValidData = new ArrayList<>();
            LastestBingoEntity item;

            while (true) {
                for (int i=0, len=mRunningLatestData.size(); i<len; i++) {
                    item = mRunningLatestData.get(i);
                    item.setTimeLeft(item.getTimeLeft()-L_CALC_SPEED);
                    if (item.getTimeLeft() < 0) {
                        inValidData.add(item);
                    }

                }
                mRunningLatestData.removeAll(inValidData);
                inValidData.clear();
                updateSpeedDump();
                _resetRunningLatestData();

            }   //end while

        }   //end run

        private void _resetRunningLatestData() {
            synchronized (this) {
                if (bIsNeedReset) {
                    bIsNeedReset = false;

                    debug();

                    mRunningLatestData.clear();

                    if (mResetData==null) {
                        mResetData = new ArrayList<>();
                    }
                    LastestBingoEntity item;
                    for (int i=0, len=mResetData.size(); i<len; i++) {
                        item = mResetData.get(i);
                        if (item.getStatus() == com.example.liangge.indiana.comm.Constant.LatestFragment.CODE_RUNNING) {
                            mRunningLatestData.add(item);
                        }
                    }

                    mResetData.clear();
                }
            }

        }

        private void debug() {
            final String D = TAG + "DEBUG";
            LogUtils.e(D, "mRunningList.size=%d, mRunnningList=%s, ", mRunningLatestData.size(), mRunningLatestData.toString());

        }

        private void updateSpeedDump() {
            try {
                Thread.sleep(L_CALC_SPEED);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }


    }




}
