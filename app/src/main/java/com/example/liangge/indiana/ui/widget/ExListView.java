package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by baoxing on 2015/12/21.
 */
public class ExListView extends ListView{
    public ExListView(Context context) {
        super(context);
    }

    public ExListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRes(context, attrs);
    }



    public ExListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes(context, attrs);
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


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
