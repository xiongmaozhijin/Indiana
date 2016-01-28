package com.example.liangge.indiana.ui.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by baoxing on 2016/1/28.
 */
public class ExListViewTest extends ListView{
    public ExListViewTest(Context context) {
        super(context);
    }

    public ExListViewTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExListViewTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ScrollView mScrollView;

    public void setScrollView(ScrollView scrollView) {
        mScrollView = scrollView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow ScrollView to intercept touch events.
                mScrollView.requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                mScrollView.requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Allow ScrollView to intercept touch events.
                mScrollView.requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.onTouchEvent(ev);
    }
}
