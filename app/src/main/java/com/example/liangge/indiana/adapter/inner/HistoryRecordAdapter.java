package com.example.liangge.indiana.adapter.inner;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.adapter.ShoppingCartListViewAdapter;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.inner.CategoryDetailEntitiy;
import com.example.liangge.indiana.model.inner.HistoryRecordEntity;
import com.example.liangge.indiana.ui.widget.CommTitleBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2016/1/11.
 */
public class HistoryRecordAdapter extends BaseAdapter{

    private static final String TAG = HistoryRecordAdapter.class.getSimpleName();

    private static Context mContext;

    private List<HistoryRecordEntity> mListData = new ArrayList<>();

    private static DisplayImageOptions mDisplayImageUserPortraitOptions;

    public HistoryRecordAdapter(Context context) {
        this.mContext = context;
        initUserPortraitImageLoaderConf();
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
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }


    public void resetDataAndNotify(List<HistoryRecordEntity> list) {
        LogUtils.i(TAG, "resetDataAndNotify(). list.size=%d", list.size());
        if (list != null) {
            mListData.clear();
            HistoryRecordEntity item;
            for (int i=0, len=list.size(); i<len; i++) {
                item = list.get(i);
                mListData.add(item);
            }
            notifyDataSetChanged();
        }
    }

    public void loadMoreData(List<HistoryRecordEntity> newList) {
        if (newList != null) {
            HistoryRecordEntity item;
            for (int i=0, len = newList.size(); i<len; i++) {
                item = newList.get(i);
                mListData.add(item);
            }
            notifyDataSetChanged();
        }
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
        ViewHoldder viewHoldder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.activity_history_listview_item, null);
            viewHoldder = new ViewHoldder(view);
            view.setTag(viewHoldder);

        } else {
            viewHoldder = (ViewHoldder) view.getTag();

        }
        final HistoryRecordEntity item = mListData.get(position);
        viewHoldder.adapterData(item);

        return view;
    }





    private static class ViewHoldder {
        private TextView txvDesc1;
        private ImageView imgUserPortrait;
        private TextView txvDesc2;

        public ViewHoldder(View view) {
            txvDesc1 = (TextView) view.findViewById(R.id.txv_desc1);
            imgUserPortrait = (ImageView) view.findViewById(R.id.img_user_portrait);
            txvDesc2 = (TextView) view.findViewById(R.id.txv_info_desc2);
        }

        public void adapterData(HistoryRecordEntity item) {
            String desc1Format = mContext.getResources().getString(R.string.activity_history_desc1);
            String desc2Format = mContext.getResources().getString(R.string.activity_history_item_desc2);
            String desc1 = String.format(desc1Format, item.getIssue_id(), item.getOut_time());
            String desc2 = String.format(desc2Format, item.getWinner(), item.getAccount_id(), item.getLuckyNumber(), item.getOwn_share());
            txvDesc1.setText(desc1);
            txvDesc2.setText(Html.fromHtml(desc2));
            ImageLoader.getInstance().displayImage(item.getPhoto(), imgUserPortrait, mDisplayImageUserPortraitOptions);
        }

    }

}
