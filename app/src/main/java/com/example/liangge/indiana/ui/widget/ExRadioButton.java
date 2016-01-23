package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.LogUtils;

/**
 * 扩展RadioButton，使其支持Badgeview
 * Created by baoxing on 2015/12/21.
 */
public class ExRadioButton extends ExBaseRadioButton{

    private static final String TAG = ExRadioButton.class.getSimpleName();

    private int mIBuyCnt;

    private Paint mTextPaint;

    private Paint mBgPaint;

    private Rect mTempRect = new Rect();

    private boolean mIsShowBadgeView = false;


    public ExRadioButton(Context context) {
        super(context);
        initRes();
    }

    public ExRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRes();
    }

    public ExRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes();
    }

    private void initRes() {
        LocalDisplay.init(getContext());
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setDither(true);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setDither(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBadgeView(canvas);

    }

    private void drawBadgeView(Canvas canvas) {
        if (mIsShowBadgeView) {
            int h = getHeight();
            int w = getWidth();
            final Drawable drawable = getCompoundDrawables()[1];
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            int iCircleSize = LocalDisplay.dp2px(8);
            //bg
//            int x1 = w - iCircleSize;
            int x1 = w/2 + dw/2 - iCircleSize/2;
            int y1 = iCircleSize;
            mBgPaint.setColor(getResources().getColor(R.color.titlebar_color));
            mBgPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x1, y1, iCircleSize, mBgPaint);
            //cnt
            String strBuyCnt = mIBuyCnt + "";
            mTextPaint.getTextBounds(strBuyCnt, 0, strBuyCnt.length(), mTempRect);
            int txvHeight = mTempRect.height();

            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setColor(Color.WHITE);
            canvas.drawText(mIBuyCnt + "", x1, y1+txvHeight/2, mTextPaint);

            LogUtils.i(TAG, "drawBadgeView(). h=%d, w=%d,dh=%d, dw=%d iCircleSize=%d, x1=%d, y1=%d", h, w,dh, dw, iCircleSize, x1, y1);
        }

    }

    /**
     * 运行在主线程，更新购买数量
     * @param buyCnt
     */
    public void setBuyCnt(int buyCnt) {
        this.mIBuyCnt = buyCnt;
        invalidate();
    }

    public void setBuyIconVisibility(boolean bShowBadgeView) {
        this.mIsShowBadgeView = bShowBadgeView;
        invalidate();
    }





}
