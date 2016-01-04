package com.example.liangge.indiana.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.ImageViewBiz;
import com.example.liangge.indiana.fragments.BaseFragment;
import com.example.liangge.indiana.model.IndianaCategoryEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2016/1/4.
 */
public class IndianaCategoryAdapter extends BaseAdapter {

    private Context mContext;

    private List<IndianaCategoryEntity> mListData = new ArrayList<>();

    private static DisplayImageOptions mDisplayImageOptions;

    public IndianaCategoryAdapter(Context context) {
        this.mContext = context;
        initImageLoaderConf();
    }



    public void reSetDataAndNotify(List<IndianaCategoryEntity> list) {
        if (list != null) {
            mListData.clear();

            IndianaCategoryEntity item;
            for (int i=0, len=list.size(); i<len; i++) {
                item = list.get(i);
                mListData.add(item);
            }

            notifyDataSetChanged();
        }   //end list != null
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
            view = View.inflate(mContext, R.layout.layout_category_listview_item, null);
            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();

        }

        final IndianaCategoryEntity item = mListData.get(position);
        viewHolder.adapterData(item);

        return view;
    }




    private static class ViewHolder {

        private ImageView imgViewIcon;

        private TextView txvCategoryName;

        public ViewHolder(View view) {
            imgViewIcon = (ImageView) view.findViewById(R.id.item_category_img);
            txvCategoryName = (TextView) view.findViewById(R.id.item_category_txv);

        }


        public void adapterData(IndianaCategoryEntity item) {
            this.txvCategoryName.setText(item.getCategory_name());
            ImageLoader.getInstance().displayImage(item.getIcon(), this.imgViewIcon, mDisplayImageOptions);
        }


    }





}
