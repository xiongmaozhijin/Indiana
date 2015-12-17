package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.example.liangge.indiana.comm.LogUtils;

/**
 * 对ScollView进行扩展，使其支持监听下拉到顶部和上拉到底部
 * Created by baoxing on 2015/12/15.
 */
public class ExScrollView extends ScrollView{

    private static final String TAG = ExScrollView.class.getSimpleName();

    /** 监听是否在顶部或底部 */
    private OnScrollDoneListener mOnScrollDoneListener;

    /** 监听是否应该显示或隐藏浮动菜单 */
    private OnFloatMenuHiddenListener mOnFloatMenuHiddenListener;

    /** 滚动的的浮动菜单的内容，这里具体是GridView */
    private View mFloatMenuContentViewWrapper;


    private float iPrevY;

    private static final int I_DO_LOAD_MORE_CAPTURE = 10;

    /**
     * 监听是否应该显示或隐藏浮动菜单
     */
    public interface OnFloatMenuHiddenListener {
        void shouldHiddle(boolean bShouldHiddle);

    }


    /**
     * 监听是否滚动到顶部或底部
     */
    public interface OnScrollDoneListener {
        void onScrollTop();
        void onScrollBottom();
    }

    public void setOnScrollDoneListener(OnScrollDoneListener listener) {
        this.mOnScrollDoneListener = listener;

    }

    public void setOnFloatMenuHiddenListener(OnFloatMenuHiddenListener listener, View floatMenuContentView) {
        this.mOnFloatMenuHiddenListener = listener;
        this.mFloatMenuContentViewWrapper = floatMenuContentView;

    }


    public ExScrollView(Context context) {
        super(context);
    }

    public ExScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        handleFloatMenu();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        handleOnScrollDone(ev);
        handleFloatMenu(ev);

        return super.onTouchEvent(ev);

    }


    private void handleFloatMenu(MotionEvent ev) {
        final int iAction = ev.getAction();
        final View floatContentViewWrapper = this.mFloatMenuContentViewWrapper;

        if ( (iAction==MotionEvent.ACTION_MOVE) || (iAction==MotionEvent.ACTION_UP) ) {
            handleFloatMenu();

        }

    }

    private void handleFloatMenu() {
        if (this.mFloatMenuContentViewWrapper != null) {
            int iWrapperTopDis = this.mFloatMenuContentViewWrapper.getTop();
            int iScrollY = getScrollY();
            boolean bShouldShow = (iScrollY >= iWrapperTopDis);
            if (mOnFloatMenuHiddenListener != null) {
                mOnFloatMenuHiddenListener.shouldHiddle(!bShouldShow);
            }

        }
    }

    private void handleOnScrollDone(MotionEvent ev) {
        final int iAction = ev.getAction();
        final int LIMIT_CANPTURE = 0;
        final int TOP_VALUE = 0 + LIMIT_CANPTURE;

        if (iAction == MotionEvent.ACTION_MOVE) {
            int iScrollY = getScrollY();
            int iHeight = getHeight();
            int iScrollViewMeasuredHeight = getChildAt(0).getMeasuredHeight();

                if (iScrollY <= TOP_VALUE) {
                    if (mOnScrollDoneListener != null) {
                        mOnScrollDoneListener.onScrollTop();
                    }
                }

                if ( (iScrollY+iHeight) >= (iScrollViewMeasuredHeight-LIMIT_CANPTURE)) {
                    if (mOnScrollDoneListener != null) {
                        mOnScrollDoneListener.onScrollBottom();
                    }
                }



        }
    }

}
