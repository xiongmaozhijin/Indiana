package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.BannerInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片轮播
 * Created by baoxing on 2015/12/14.
 */
public class BannerView extends FrameLayout{

    private static final String TAG = BannerView.class.getSimpleName();

    private View mWrapperView;

    private ViewPager mBannerViewPager;

    private BannerViewAdapter mAdapter;

    private LinearLayout mLLViewIndicator;

    private Context mContext;

    private List<View> mViewIndicators = new ArrayList<>();

    /** ImageLoader的选项配置 */
    private DisplayImageOptions mDisplayImageOptions;

    /** 数据列表 */
    private List<BannerInfo> mBannerListInfo = new ArrayList<>();

    /** 当前的BannerInfo */
    private BannerInfo mCurBannerInfo;

    /** 当前的viewpager的item */
    private int mCurItem;

    /** 点击事件 */
    private OnClickListener mOnClickListener;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes(context);
        initViewPager(context);
        initImageLoaderConf(context);
    }

    private void initImageLoaderConf(Context context) {
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.main_banner_img_load_empty_uri)
                .showImageOnFail(R.drawable.main_banner_img_load_fail)
                .resetViewBeforeLoading(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(50))
                .build();
    }

    private void initRes(Context context) {
        this.mContext = context;
        mWrapperView = LayoutInflater.from(context).inflate(R.layout.widget_banner_view, this);
        mBannerViewPager = (ViewPager) mWrapperView.findViewById(R.id.main_banner_viewpager);
        mLLViewIndicator = (LinearLayout) mWrapperView.findViewById(R.id.main_banner_indicator);
    }


    private void initViewPager(Context context) {
        mAdapter = new BannerViewAdapter();
        mBannerViewPager.setAdapter(mAdapter);
        mBannerViewPager.addOnPageChangeListener(new BannerPageChangeListener());

    }



    public void setDatasAndNotify(List<BannerInfo> bannerInfos) {
        if (bannerInfos != null) {
            this.mBannerListInfo.clear();
            BannerInfo item;
            for (int i=0; i<bannerInfos.size(); i++) {
                item = bannerInfos.get(i);
                this.mBannerListInfo.add(item);
            }
        }

        generateIndicatos();
        confing();
    }

    private void confing() {
        LogUtils.e(TAG, "config()");
        mBannerViewPager.setAdapter(mAdapter);
        updateIndicator(0);
    }

    private void generateIndicatos() {
        LogUtils.e(TAG, "generateIndicators()");
        int iCounts = this.mBannerListInfo.size();
        this.mLLViewIndicator.removeAllViews();
        this.mViewIndicators.clear();
        for (int i=0; i<iCounts; i++) {
            View item = new View(mContext);
            item.setBackgroundResource(R.drawable.main_banner_indicator_normal);
            this.mLLViewIndicator.addView(item, (int)(getResources().getDimension(R.dimen.indiana_indicator_size)), (int)(getResources().getDimension(R.dimen.indiana_indicator_size)));
            this.mViewIndicators.add(item);
        }
    }


    private class BannerViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
           if (mBannerListInfo.size() > 0) {
               BannerInfo bannerInfo = mBannerListInfo.get(position % mBannerListInfo.size());
               mCurBannerInfo = bannerInfo;
               mCurItem = position;

               ImageView imgView = new ImageView(mContext);
               ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
               imgView.setLayoutParams(layoutParams);
               imgView.setScaleType(ImageView.ScaleType.FIT_XY);
               imgView.setClickable(true);
               imgView.setOnClickListener(new ImgViewOnClick(bannerInfo));
               ImageLoader.getInstance().displayImage(bannerInfo.getImgUrl(), imgView, mDisplayImageOptions);

               ((ViewPager) container).addView(imgView);

               return imgView;
           }

            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            ((ViewPager)container).removeView((View)object);
        }



    }


    public interface OnClickListener {
        void onClick(BannerInfo item);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;

    }




    private class ImgViewOnClick implements View.OnClickListener {

        private BannerInfo bannerInfo;

        public ImgViewOnClick(BannerInfo bannerInfo) {
            this.bannerInfo = bannerInfo;
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(bannerInfo);
            }
        }
    }

    private class BannerPageChangeListener extends ViewPager.SimpleOnPageChangeListener {


        @Override
        public void onPageSelected(int position) {
            updateIndicator(position);
        }

    }

    /**
     * 更新指示跟随
     * @param position
     */
    private void updateIndicator(int position) {
        int iCounts = mViewIndicators.size();
        View dotView;
        boolean bIsSelect;

        for (int i=0; i<iCounts; i++) {
            dotView = mViewIndicators.get(i);
            bIsSelect = i != (position%iCounts);
            if (!bIsSelect) {
                dotView.setBackgroundResource(R.drawable.main_banner_indicator_normal);
            } else {
                dotView.setBackgroundResource(R.drawable.main_banner_indicator_select);
            }
        }
    }



    public void onStop() {

    }


    public void onDestroy() {

    }


}
