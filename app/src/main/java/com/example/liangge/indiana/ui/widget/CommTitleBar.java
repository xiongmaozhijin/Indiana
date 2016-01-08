package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;

/**
 * Created by baoxing on 2016/1/5.
 */
public class CommTitleBar extends FrameLayout{

    private static final String TAG = CommTitleBar.class.getSimpleName();

    private String title = "";

    private int titleColor;

    private Context mContext;

    public CommTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initRes(context);
    }

    public CommTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
        initRes(context);
    }

    private void initRes(Context context) {
//        View view = View.inflate(context, R.layout.layout_comm_title_bar, this);
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_comm_title_bar, this);
        LayoutInflater.from(context).inflate(R.layout.layout_comm_title_bar, this, true);
        TextView txvTitle = (TextView) findViewById(R.id.titlebar_title);
        View titleWrapper = findViewById(R.id.titlebar_wrapper);

        txvTitle.setText(title);
        titleWrapper.setBackgroundColor(titleColor);
    }


    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommTitleBar);
        title = ta.getString(R.styleable.CommTitleBar_titlebar_title);

        titleColor = ta.getColor(R.styleable.CommTitleBar_titlebar_color, getResources().getColor(R.color.titlebar_color));

        LogUtils.w(TAG, "title=%s", title);
        ta.recycle();
    }

}
