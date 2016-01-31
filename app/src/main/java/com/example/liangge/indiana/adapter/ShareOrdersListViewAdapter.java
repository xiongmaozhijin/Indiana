package com.example.liangge.indiana.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.model.ShareOrdersEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 晒单适配器
 * Created by baoxing on 2016/1/20.
 */
public class ShareOrdersListViewAdapter extends BaseAdapter{

    private Context mContext;

    private List<ShareOrdersEntity> mListData = new ArrayList<>();

    private static DisplayImageOptions mDisplayImageOptions;

    private static DisplayImageOptions mUserImgOptions;

    public ShareOrdersListViewAdapter(Context context) {
        mContext = context;
        initRes();
    }




    /**
     * 重置数据并更新列表
     * @param list
     */
    public void setDataAndNotify(List<ShareOrdersEntity> list) {
        if (list != null) {
            this.mListData.clear();
            ShareOrdersEntity item;
            for (int i=0; i<list.size(); i++) {
                item = list.get(i);
                this.mListData.add(item);
            }
            notifyDataSetChanged();
        }
    }

    public void loadMoreDataAndNotify(List<ShareOrdersEntity> newList) {
        if (newList != null && newList.size()>0) {
            ShareOrdersEntity item;
            for (int i=0, len=newList.size(); i<len; i++) {
                item = newList.get(i);
                this.mListData.add(item);
            }

            notifyDataSetChanged();
        }
    }






    private void initRes() {
        initImageLoaderConf();
        mUserImgOptions = PersonalCenterBiz.getInstance(mContext).getUserPortraitConfig();
    }

    private void initImageLoaderConf() {
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.main_banner_img_load_empty_uri)
                .showImageOnFail(R.drawable.main_banner_img_load_fail)
                .showImageOnLoading(R.drawable.main_product_item_img_onloading)
                .cacheOnDisk(true)
                .cacheInMemory(false)
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
            view = View.inflate(mContext, R.layout.layout_share_item_new, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();

        }

        final ShareOrdersEntity item = mListData.get(position);
        viewHolder.adapterData(item);

        return view;
    }



    private static class ViewHolder {

        private ImageView imgUserPortrait;
        private TextView username;
        private TextView shareDate;
        private TextView shareTitle;
        private TextView shareContent;
        private TextView goodName;
        private ImageView shareImg1;
        private ImageView shareImg2;
        private ImageView shareImg3;


        public ViewHolder(View view) {
            initView(view);
        }

        private void initView(View view) {
            imgUserPortrait = (ImageView) view.findViewById(R.id.iv_avatar);
            username = (TextView) view.findViewById(R.id.share_list_name);
            shareDate = (TextView) view.findViewById(R.id.share_list_time);
            shareTitle = (TextView) view.findViewById(R.id.share_list_title);
            shareContent = (TextView) view.findViewById(R.id.share_list_text);
            goodName = (TextView) view.findViewById(R.id.share_list_goodName);
            shareImg1 = (ImageView) view.findViewById(R.id.img1);
            shareImg2 = (ImageView) view.findViewById(R.id.img2);
            shareImg3 = (ImageView) view.findViewById(R.id.img3);

        }


        public void adapterData(ShareOrdersEntity item) {
            ImageLoader.getInstance().displayImage(item.getUserPortraitUrl(), imgUserPortrait, mUserImgOptions);
            username.setText(item.getUsername());
            shareDate.setText(item.getShareDate());
            shareTitle.setText(item.getShareTitle());
            shareContent.setText(item.getShareContent());
            goodName.setText(item.getBingoRecordEntity().getTitle());
            ImageLoader.getInstance().displayImage(item.getShareImgs().get(0), shareImg1, mDisplayImageOptions);
            ImageLoader.getInstance().displayImage(item.getShareImgs().get(1), shareImg2, mDisplayImageOptions);
            ImageLoader.getInstance().displayImage(item.getShareImgs().get(2), shareImg3, mDisplayImageOptions);

        }


    }








}
