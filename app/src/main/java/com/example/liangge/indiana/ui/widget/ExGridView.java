package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/**
 * 扩展GridView，使其支持ScrollView
 * Created by baoxing on 2015/12/15.
 */
public class ExGridView extends GridView{

        public ExGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ExGridView(Context context) {
            super(context);
        }

        public ExGridView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }



}
