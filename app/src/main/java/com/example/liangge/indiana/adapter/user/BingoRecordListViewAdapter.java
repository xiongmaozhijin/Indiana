package com.example.liangge.indiana.adapter.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/25.
 */
public class BingoRecordListViewAdapter extends BaseAdapter {

    private List<BingoRecordEntity> mListData = new ArrayList<>();

    private static Context mContext;

    private static DisplayImageOptions mDisplayImageOptions;

    public BingoRecordListViewAdapter(Context context) {
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



    public void resetDataAndNotify(List<BingoRecordEntity> list) {
        if (list != null) {
            this.mListData.clear();
            for (int i=0, len=list.size(); i<len; i++) {
                BingoRecordEntity item = list.get(i);
                this.mListData.add(item);
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
            view = View.inflate(mContext, R.layout.activity_bingorecord_listview_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();

        }

        final BingoRecordEntity item = mListData.get(position);

        viewHolder.adapterData(item);

        return view;
    }



    private static class ViewHolder {

        private ImageView imgViewProduct;

        private TextView txvIndianaInfo;

        public ViewHolder(View view) {
            imgViewProduct = (ImageView) view.findViewById(R.id.product_imgview);
            txvIndianaInfo = (TextView) view.findViewById(R.id.bingo_info);
        }

        public void adapterData(BingoRecordEntity item) {
            String bingoInfoFormat = mContext.getResources().getString(R.string.activity_bingorecord_bingoinfo);
            String bingoInfo = String.format(bingoInfoFormat, item.getTitle(), item.getActivityId()+"", item.getNeedPeople(), item.getBuyCnt(), item.getLuckyNumber(), item.getHumanAlreadyRunLotteryTime());
            txvIndianaInfo.setText(bingoInfo);
            ImageLoader.getInstance().displayImage(item.getProductImgUrl(), imgViewProduct, mDisplayImageOptions);
        }   //end adapterData

    }

}
