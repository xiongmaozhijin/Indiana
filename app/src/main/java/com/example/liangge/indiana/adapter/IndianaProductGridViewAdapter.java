package com.example.liangge.indiana.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by baoxing on 2015/12/15.
 */
public class IndianaProductGridViewAdapter extends BaseAdapter {

    private static final String TAG = IndianaProductGridViewAdapter.class.getSimpleName();
    /** 产品数据 */
    public List<ActivityProductItemEntity> mListProducts = new ArrayList<>();

    private Context mContext;

    private static DisplayImageOptions mDisplayImageOptions;

    private static OnShoppingCartClickListener mOnShoppingCartClickListener;

    /**
     * 购物车点击事件
     */
    public interface OnShoppingCartClickListener {
        void onShoppoingCartClick(ActivityProductItemEntity item);

    }

    public void setOnShoppingCartClickListener(OnShoppingCartClickListener listener) {
        this.mOnShoppingCartClickListener = listener;

    }



    public IndianaProductGridViewAdapter(Context context) {
        this.mContext = context;
        initRes();
    }

    private void initRes() {
        initImageLoaderConf(mContext);
    }

    /**
     * 配置开源组件ImageLoader的参数
     * @param context
     */
    private void initImageLoaderConf(Context context) {
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


    /**
     * 重置数据并更新列表
     * @param list
     */
    public void setDataAndNotify(List<ActivityProductItemEntity> list) {
        if (list != null) {
            this.mListProducts.clear();
            ActivityProductItemEntity item;
            for (int i=0; i<list.size(); i++) {
                item = list.get(i);
                this.mListProducts.add(item);
//                LogUtils.e(TAG, "id=%d", item.getActivityId());
            }
            notifyDataSetChanged();
        }
    }

    public void loadMoreProductDataAndNotify(List<ActivityProductItemEntity> newList) {
        if (newList != null) {
            ActivityProductItemEntity item;
            for (int i=0, len=newList.size(); i<len; i++) {
                item = newList.get(i);
                this.mListProducts.add(item);
//                LogUtils.e(TAG, "id=%d", item.getActivityId());
            }

            notifyDataSetChanged();
        }
    }

    /**
     * 清除数据
     */
    public void clearDataAndNotify() {
        if (mListProducts != null) {
            mListProducts.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return mListProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mListProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.f_indiana_gridview_item, null);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.f_indiana_gridview_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final ActivityProductItemEntity item = mListProducts.get(position);
        viewHolder.adapterData(item);

        return convertView;
    }


    public static class ViewHolder {

        private ImageView imgProductImg;

        private TextView txvProductName;

        private TextView txvBingoProgress;

        private ImageButton btnShoppingCart;

        private ProgressBar progressBar;

        private View mViewTenYuanHint;

        public ViewHolder(View view) {
            this.imgProductImg = (ImageView) view.findViewById(R.id.indiana_product_item_img);
            this.txvProductName = (TextView) view.findViewById(R.id.indiana_product_item_name_txv);
            this.txvBingoProgress = (TextView) view.findViewById(R.id.indiana_product_item_bingo_process_txv);
            this.btnShoppingCart = (ImageButton) view.findViewById(R.id.f_indiana_product_item_shopping_cart_btn);
            this.progressBar = (ProgressBar) view.findViewById(R.id.f_indiana_product_item_progressbar);
            this.mViewTenYuanHint = view.findViewById(R.id.ten_yuan_hint);
        }

        public void adapterData(final ActivityProductItemEntity itemInfo) {
            ImageLoader.getInstance().displayImage(itemInfo.getProductImgUrl(), this.imgProductImg, mDisplayImageOptions);

            if (itemInfo.getMinimum_share() == 10) {
                mViewTenYuanHint.setVisibility(View.VISIBLE);

            } else {
                mViewTenYuanHint.setVisibility(View.GONE);

            }

            this.txvProductName.setText(itemInfo.getName());
            this.txvBingoProgress.setText(itemInfo.getStrBingoProgress());
//            int progress = ( (itemInfo.getNeedPeople()-itemInfo.getLackPeople()) / itemInfo.getNeedPeople() ) * 100;
            this.progressBar.setProgress(itemInfo.getBingoProgress());
            this.btnShoppingCart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mOnShoppingCartClickListener != null) {
                        mOnShoppingCartClickListener.onShoppoingCartClick(itemInfo);
                    }
                }
            });
        }
    }
}
