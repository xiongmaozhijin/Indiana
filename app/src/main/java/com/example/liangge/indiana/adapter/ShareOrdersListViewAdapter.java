package com.example.liangge.indiana.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.model.ShareOrdersEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 晒单适配器
 * Created by baoxing on 2016/1/20.
 */
public class ShareOrdersListViewAdapter extends BaseAdapter{

    private Context mContext;

    private List<ShareOrdersEntity> mListData = new ArrayList<>();

    public ShareOrdersListViewAdapter(Context context) {
        mContext = context;
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
        return null;


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

        }


    }








}
