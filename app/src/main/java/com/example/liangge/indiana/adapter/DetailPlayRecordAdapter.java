package com.example.liangge.indiana.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.model.ResponseActivityPlayRecordEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页所有参与记录的Adapter
 * Created by baoxing on 2015/12/28.
 */
public class DetailPlayRecordAdapter extends BaseAdapter
{

    private static final String TAG = DetailPlayRecordAdapter.class.getSimpleName();

    private List<ResponseActivityPlayRecordEntity> mListData = new ArrayList<>();

    private static Context mContext;

    private static DisplayImageOptions mDisplayImageUserPortraitOptions;

    public DetailPlayRecordAdapter(Context context) {
        this.mContext = context;
        initUserPortraitImageLoaderConf();
    }

    public void reSetDataAndNotify(List<ResponseActivityPlayRecordEntity> list) {
        if (list != null) {
            mListData.clear();

            for (int i=0, len=list.size(); i<len; i++) {
                ResponseActivityPlayRecordEntity item = list.get(i);
                mListData.add(item);
            }

            notifyDataSetChanged();

            LogUtils.i(TAG, "reSetDataAndNotify().list.size=%d; mListData.size=%d", list.size(), mListData.size());
        }

    }


    public void loadMoreData(List<ResponseActivityPlayRecordEntity> newList) {
        if (newList != null) {
            ResponseActivityPlayRecordEntity item;
            for (int i=0, len=newList.size(); i<len; i++) {
                item = newList.get(i);
                mListData.add(item);
            }

            notifyDataSetChanged();
        }
    }

    /**
     * 初始化用户头像显示配置
     */
    private void initUserPortraitImageLoaderConf() {
        mDisplayImageUserPortraitOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.user_gray)
                .showImageOnFail(R.drawable.user_gray)
                .showImageOnLoading(R.drawable.user_gray)
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(90))
                .build();
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.activity_detail_all_records_listview_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ResponseActivityPlayRecordEntity item = mListData.get(position);
        viewHolder.adapterData(item);

        return view;
    }




    private static class ViewHolder {

        private ImageView imgUserPortrait;
        private TextView txvRecordInfo;

        public ViewHolder(View view){
            this.imgUserPortrait = (ImageView) view.findViewById(R.id.user_portrait);
            this.txvRecordInfo = (TextView) view.findViewById(R.id.playrecords_info);
        }

        public void adapterData(ResponseActivityPlayRecordEntity item) {
            String infoFormat = mContext.getResources().getString(R.string.activity_detailinfo_listview_item_txv_info_format);
            String info = String.format(infoFormat, item.getNickname(), item.getOwn_share(), item.getRecord_time());
            this.txvRecordInfo.setText(Html.fromHtml(info));
            ImageLoader.getInstance().displayImage(item.getPhoto(), imgUserPortrait, mDisplayImageUserPortraitOptions);
        }

    }




}
