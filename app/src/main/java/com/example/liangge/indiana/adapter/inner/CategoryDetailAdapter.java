package com.example.liangge.indiana.adapter.inner;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.model.inner.CategoryDetailEntitiy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 类别详细展示
 * Created by baoxing on 2016/1/4.
 */
public class CategoryDetailAdapter extends BaseAdapter {

    private List<CategoryDetailEntitiy> mListData = new ArrayList<>();

    private static Context mContext;

    private static DisplayImageOptions mDisplayImageOptions;

    /*购买监听*/
    private static OnAddToCartListener mListener;

    /**
     * 加入购物车监听
     */
    public interface OnAddToCartListener {
        public void onAddToCart(CategoryDetailEntitiy item , int buyCnt);
    }

    public void resetDataAndNotify(List<CategoryDetailEntitiy> list) {
        if (list != null) {
            mListData.clear();
            CategoryDetailEntitiy item;
            for (int i=0, len=list.size(); i<len; i++) {
                item = list.get(i);
                mListData.add(item);
            }
            notifyDataSetChanged();
        }
    }

    public void loadMoreData(List<CategoryDetailEntitiy> newList) {
        if (newList != null) {
            CategoryDetailEntitiy item;
            for (int i=0, len = newList.size(); i<len; i++) {
                item = newList.get(i);
                mListData.add(item);
            }
            notifyDataSetChanged();
        }
    }


    public void setOnAddToCartListenerListener(OnAddToCartListener listener) {
        mListener = listener;
    }


    public CategoryDetailAdapter(Context context) {
        this.mContext = context;
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
            view = View.inflate(mContext, R.layout.layout_category_list_detail_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final CategoryDetailEntitiy item = mListData.get(position);
        viewHolder.adapterData(item);

        return view;
    }




    private static class ViewHolder {
        private static final int BUY_CNT = 10;

        private ImageView imgProduct;
        private TextView txvDes1;
        private TextView txvDes2;
        private ProgressBar progressBar;
        private Button btnAddToCart;

        public ViewHolder(View view) {
            imgProduct = (ImageView) view.findViewById(R.id.imgview);
            txvDes1 = (TextView) view.findViewById(R.id.txv_desc1_title);
            txvDes2 = (TextView) view.findViewById(R.id.txv_desc2_cnt);
            progressBar = (ProgressBar) view.findViewById(R.id.progress);
            btnAddToCart = (Button) view.findViewById(R.id.btn_addto_char);
        }

        public void adapterData(final CategoryDetailEntitiy item) {
            String hintDesc2Format = mContext.getResources().getString(R.string.activity_category_detail_cnt_des);
            String hintDesc2 = String.format(hintDesc2Format, item.getShare_total(), item.getShare_total() - item.getShare_current());
            int iProgress = (int) ((float)item.getShare_current() / item.getShare_total()) * 1000;
            ImageLoader.getInstance().displayImage(item.getIcon(), this.imgProduct, mDisplayImageOptions);
            txvDes1.setText(item.getName());
            txvDes2.setText(Html.fromHtml(hintDesc2));
            progressBar.setProgress(iProgress);
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onAddToCart(item, BUY_CNT);
                    }
                }
            });
        }


    }







}
