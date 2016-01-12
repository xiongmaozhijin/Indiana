package com.example.liangge.indiana.adapter.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.inner.HistoryRecordEntity;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/25.
 */
public class IndianaRecordListViewAdapter extends BaseAdapter {

    private List<IndianaRecordEntity> mListData = new ArrayList<>();

    private static Context mContext;

    private static DisplayImageOptions mDisplayImageOptions;

    public IndianaRecordListViewAdapter(Context context) {
        this.mContext = context;
        initRes();
    }

    private void initRes() {
        initImageLoaderConf();

    }


    private void initImageLoaderConf() {
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



    public void resetDataAndNotify(List<IndianaRecordEntity> list) {
        if (list != null) {
            this.mListData.clear();
            for (int i=0, len=list.size(); i<len; i++) {
                IndianaRecordEntity item = list.get(i);
                this.mListData.add(item);
            }

            notifyDataSetChanged();
        }

    }

    /**
     * 加载更多
     * @param newList
     */
    public void loadMoreDataAndNotify(List<IndianaRecordEntity> newList) {
        if (newList != null) {
            IndianaRecordEntity item;
            for (int i=0, len = newList.size(); i<len; i++) {
                item = newList.get(i);
                mListData.add(item);
            }
            notifyDataSetChanged();
        }
    }






    @Override
    public int getCount() {
        return this.mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.activity_indianarecord_listview_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();

        }

        final IndianaRecordEntity item = mListData.get(position);

        viewHolder.adapterData(item);

        return view;
    }



    private static class ViewHolder {

        private ImageView imgViewProduct;

        private TextView txvIndianaInfo;

        private View mViewTenYuanArea;

        public ViewHolder(View view) {
            imgViewProduct = (ImageView) view.findViewById(R.id.activity_indianarecord_product_img);
            txvIndianaInfo = (TextView) view.findViewById(R.id.activity_indianarecord_userinfo_txv);
            mViewTenYuanArea = view.findViewById(R.id.ten_yuan_hint);
        }

        public void adapterData(IndianaRecordEntity item) {
            String indianaInfoFormat;
            String indianaInfo;
            int activityState = item.getActivityState();
            if (activityState == 0) {      //已揭晓
                indianaInfoFormat = mContext.getResources().getString(R.string.activity_indianarecord_userinfo_1);
                indianaInfo = String.format(indianaInfoFormat, item.getTitleDescribe(), item.getJoinActivityNum(), item.getBuyCnt(), item.getMyIndianaNum(),
                                                item.getBingoUser(), item.getBingoBuyCnt(), item.getLuckyNumber(), item.getHumanAlreadyRunLotteryTime());
            } else if (activityState == 1) {    //进行中
                indianaInfoFormat = mContext.getResources().getString(R.string.activity_indianarecord_userinfo_2);
                indianaInfo = String.format(indianaInfoFormat, item.getTitleDescribe(), item.getActivityId(), item.getBuyCnt(), item.getMyIndianaNum(),
                                                item.getHumanReadableProcessHint());
            } else if (activityState == 2) {    //揭晓中
                indianaInfoFormat = mContext.getResources().getString(R.string.activity_indianarecord_userinfo_3);
                indianaInfo = String.format(indianaInfoFormat, item.getTitleDescribe(), item.getActivityId(), item.getBuyCnt(), item.getMyIndianaNum());
            } else {        //服务端参数错误
                indianaInfo = mContext.getResources().getString(R.string.activity_indianarecord_userinfo_4);
            }

            ImageLoader.getInstance().displayImage(item.getProductImgUrl(), this.imgViewProduct, mDisplayImageOptions);
            this.txvIndianaInfo.setText(indianaInfo);

            if (item.getMinimum_share() == 10) {
                mViewTenYuanArea.setVisibility(View.VISIBLE);

            } else {
                mViewTenYuanArea.setVisibility(View.INVISIBLE);

            }

        }   //end adapterData

    }

}
