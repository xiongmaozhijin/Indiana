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

    /** 要删除清单集合 */
    private static List<InventoryEntity> mListDelete = new ArrayList<>();

    private static Context mContext;

    private static OnBuyCntChangeListener mOnBuyCntChangeListener;

    private static DisplayImageOptions mDisplayImageOptions;

    private static final String TAG = ShoppingCartListViewAdapter.class.getSimpleName();

    /** 是否编辑状态 */
    private static boolean mIsEditState = false;

    private static OnItemClickListener1 mOnItemClickListener1;
    private static OnItemLongClickListener1 mOnItemLongClickListener1;

    /**
     * 点击事件
     */
    public interface OnItemClickListener1 {
        void onItemClickListener1(InventoryEntity item);
    }

    /**
     * 长按事件
     */
    public interface OnItemLongClickListener1 {
        void onItemLongClickListener1(InventoryEntity item);
    }


    public void setOnItemClickListener1(OnItemClickListener1 listener) {
        this.mOnItemClickListener1 = listener;
    }

    public void setOnItemLongClickListener1(OnItemLongClickListener1 listener) {
        this.mOnItemLongClickListener1 = listener;
    }

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

    /**
     * 是否全选了
     * @return
     */
    public boolean isSeletcAll() {
        boolean isSelectAll = mListDelete.size() == mListInventorys.size();

        LogUtils.i(TAG, "isSeletcAll=%b", isSelectAll);
        return isSelectAll;
    }

    /**
     * 添加或取消一项列表清单，并显示
     * @param item
     */
    public void addOrCancelItemAndNotify(InventoryEntity item) {
        if (mListDelete.contains(item)) {
            mListDelete.remove(item);
            LogUtils.i(TAG, "CancelItemAndNotify(). item=%s", item.toString());
        } else {
            mListDelete.add(item);
            LogUtils.i(TAG, "AddItemAndNotify(). item=%s", item.toString());
        }

        notifyDataSetChanged();
    }

    /**
     * 设置是否编辑状态
     * @param isEditState
     */
    public void setEditStateAndNotify(boolean isEditState) {
        LogUtils.i(TAG, "setEditStateAndNotify(). isEditState=%b", isEditState);
        this.mIsEditState = isEditState;
        if (!isEditState) {
            mListDelete.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 取消全部要删除的items
     */
    public void cancelAllDeleteItems() {
        LogUtils.i(TAG, "cancelAllDeleteItems()");
        mListDelete.clear();
        notifyDataSetChanged();
    }

    /**
     * 全选全部删除
     */
    public void addAllDeleteItems() {
        LogUtils.i(TAG, "addAllDeleteItems()");
        mListDelete.addAll(mListInventorys);
        notifyDataSetChanged();
    }


    /**
     * 返回要删除的数据
     * @return
     */
    public List<InventoryEntity> getDeleteList() {
        LogUtils.i(TAG, "getDeleteList(). list=%s", mListDelete.toString() );
        return mListDelete;
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
        viewHolder.adapterData(item, convertView);

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

        private ImageView mImgEditHint;

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
            this.mImgEditHint = (ImageView) view.findViewById(R.id.img_edit_hint);
        }


        /**
         * 适配数据
         * @param item
         */
        private void adapterData(final InventoryEntity item, View view) {
            ImageLoader.getInstance().displayImage(item.getInvertoryImgUrl(), this.mImgViewProduct, mDisplayImageOptions);
//            mTxvTitleDescribe.setText(item.getTitleDescribe());
            mTxvTitleDescribe.setText(Html.fromHtml(item.getTitleDescribe()));
            mTxvJoinDescribe.setText(item.getJoinDescribe(mContext));
            mInventoryBuyWidget.initInventoryBuyWidget(item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i(TAG, "onItemClick().");
                    if (mOnItemClickListener1 != null) {
                        mOnItemClickListener1.onItemClickListener1(item);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LogUtils.i(TAG, "onItemLongClick()");
                    if (mOnItemLongClickListener1 != null) {
                        mOnItemLongClickListener1.onItemLongClickListener1(item);
                    }

                    return false;
                }
            });

            if (mIsEditState) {
                //编辑状态
                if (mListDelete.contains(item) ) {
                    mImgEditHint.setImageResource(R.drawable.delete_select);

                } else {
                    mImgEditHint.setImageResource(R.drawable.delete_no_select);

                }

                mImgEditHint.setVisibility(View.VISIBLE);

            } else {
                mImgEditHint.setVisibility(View.GONE);

            }


        }


    }




}
