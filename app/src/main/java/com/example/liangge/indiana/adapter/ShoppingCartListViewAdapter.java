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
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.ui.widget.InventoryBuyWidget;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/21.
 */
public class ShoppingCartListViewAdapter extends BaseAdapter{

    /** 清单数据 */
    private List<InventoryEntity> mListInventorys = new ArrayList<>();

    private static Context mContext;

    private static OnBuyCntChangeListener mOnBuyCntChangeListener;

    private static DisplayImageOptions mDisplayImageOptions;

    private static final String TAG = ShoppingCartListViewAdapter.class.getSimpleName();

    /**
     * 购买数量发生变化监听事件
     */
    public interface OnBuyCntChangeListener {
        /**
         * 购买数量发生变化回调
         * @param inventoryEntity
         */
        void onBuyCntChange(InventoryEntity inventoryEntity);
    }

    public void setOnBuyCntChangeListener(OnBuyCntChangeListener listener) {
        this.mOnBuyCntChangeListener = listener;
    }


    public ShoppingCartListViewAdapter(Context context) {
        this.mContext = context;
        initRes();
    }

    private void initRes() {
        initImageLoaderConf(mContext);

    }

    /**
     * 重置列表数据和更新ui显示
     * @param listData
     */
    public void resetDataAndNotify(List<InventoryEntity> listData) {
        if (listData != null) {
            this.mListInventorys = listData;
            notifyDataSetChanged();
        }

    }

    public void setListData(List<InventoryEntity> listData) {
        if (listData != null) {
            this.mListInventorys = listData;

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
        return  this.mListInventorys.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mListInventorys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtils.w(TAG, "getView().position=%d", position);

        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = View.inflate(mContext, R.layout.f_shoppingcart_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        final InventoryEntity item = mListInventorys.get(position);
        viewHolder.adapterData(item);

        return convertView;
    }

    public static class ViewHolder {

        /** 产品图片 */
        private ImageView mImgViewProduct;

        /** 清单标题描述 */
        private TextView mTxvTitleDescribe;

        /** 参与描述 */
        private TextView mTxvJoinDescribe;

        /** 购买控件 */
        private InventoryBuyWidget mInventoryBuyWidget;


        public ViewHolder(View view) {
            initView(view);
            initListener();
        }

        private void initListener() {
            this.mInventoryBuyWidget.setOnBuyCntChangeListener(new InventoryBuyWidget.OnBuyCntChangeListener() {
                @Override
                public void onBuyCntChange(long orderId, int curBuyCnt) {

                }

                @Override
                public void onBuyCntChange(InventoryEntity inventoryEntity) {
                    LogUtils.w(TAG, "onBuyCntChange().inventoryEntity=%s", inventoryEntity.toString());
                    if (mOnBuyCntChangeListener != null) {
                        mOnBuyCntChangeListener.onBuyCntChange(inventoryEntity);
                    }
                }
            });

        }

        private void initView(View view) {
            this.mImgViewProduct = (ImageView) view.findViewById(R.id.f_shoppingcart_product_img);
            this.mTxvTitleDescribe = (TextView) view.findViewById(R.id.f_shoppingcart_title_describe);
            this.mTxvJoinDescribe = (TextView) view.findViewById(R.id.f_shoppingcart_join_describe);
            this.mInventoryBuyWidget = (InventoryBuyWidget) view.findViewById(R.id.f_shoppingcart_buy_widget);

        }


        /**
         * 适配数据
         * @param item
         */
        private void adapterData(InventoryEntity item) {
            ImageLoader.getInstance().displayImage(item.getInvertoryImgUrl(), this.mImgViewProduct, mDisplayImageOptions);
            mTxvTitleDescribe.setText(item.getTitleDescribe());
            mTxvJoinDescribe.setText(item.getJoinDescribe(mContext));
            mInventoryBuyWidget.initInventoryBuyWidget(item);

        }


    }




}
