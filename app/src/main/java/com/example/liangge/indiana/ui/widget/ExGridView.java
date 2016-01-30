package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/**
 * @deprecated
 * 不建议使用了，因为这种做法会导致item无法服用，造成OOM<br/>
 * 使用 {@link ExGridViewScrollDone}
 * 扩展GridView，使其支持ScrollView<br/>
 * <p/>
 * Created by baoxing on 2015/12/15.
 */
public class ExGridView extends GridView {

    public ExGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRes(context, attrs);
    }


    public ExGridView(Context context) {
        super(context);
    }

    public ExGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initRes(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void initRes(Context context, AttributeSet attrs) {
        initState();
    }


    private void initState() {
        post(new Runnable() {
            @Override
            public void run() {
                setFocusable(false);
            }
        });
    }


}
