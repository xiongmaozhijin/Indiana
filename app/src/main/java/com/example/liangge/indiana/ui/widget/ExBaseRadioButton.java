package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.RadioButton;

import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.LogUtils;

/**
 * 改变Drawable的大小
 * Created by baoxing on 2016/1/6.
 */
public class ExBaseRadioButton extends RadioButton {

    private static final String TAG = ExBaseRadioButton.class.getSimpleName();

    private static int mDrawableSize;

    private static final int I_DRAWABLE_SIZE = 28;  //dp

    static {
//        mDrawableSize = LocalDisplay.dp2px(I_DRAWABLE_SIZE);
    }

    public ExBaseRadioButton(Context context) {
        super(context);
        init(context);
    }


    public ExBaseRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExBaseRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context) {
        mDrawableSize = LocalDisplay.dp2px(I_DRAWABLE_SIZE);
        LogUtils.w(TAG, "init()#mDrawableSize=%d", mDrawableSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        final Drawable drawable = getCompoundDrawables()[1];
        final Rect rect = new Rect(0, 0, mDrawableSize, mDrawableSize);
        drawable.setBounds(rect);
        setCompoundDrawables(null, drawable, null, null);

        super.onDraw(canvas);

    }
}
