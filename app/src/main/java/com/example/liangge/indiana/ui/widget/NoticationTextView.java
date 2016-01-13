package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.inner.NotificationEntitiy;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息通知显示栏
 * Created by baoxing on 2016/1/13.
 */
public class NoticationTextView extends TextView{

    private static final String TAG = NoticationTextView.class.getSimpleName();

    private List<NotificationEntitiy> mNotificationList = new ArrayList<>();

    private int mIndex = 1;

    private String mTxvNotificationFormat;

    private OnItemClickListener mOnItemClickListener;

    private NotificationEntitiy mCurItem;

    public NoticationTextView(Context context) {
        super(context);
    }


    public interface OnItemClickListener {
        void onItemClickListener(NotificationEntitiy item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public NoticationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRes(context, attrs);
    }

    private void initRes(Context context, AttributeSet attrs) {
        mTxvNotificationFormat = getResources().getString(R.string.notification_format);
        initClick();
    }

    private void initClick() {
        post(new Runnable() {
            @Override
            public void run() {
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.i(TAG, "onClick()");
                        if (mOnItemClickListener != null) {
                            if (mCurItem != null) {
                                mOnItemClickListener.onItemClickListener(mCurItem);
                            }
                        }
                    }
                });
            }
        });
    }

    public NoticationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes(context, attrs);

    }

    /**
     * 设置数据并启动轮播
     * @param list
     */
    public void setNotificationList(List<NotificationEntitiy> list) {
        if (list != null) {
            mNotificationList = list;
        }
        startPlayNotification();
    }

    private void startPlayNotification() {
        if (mNotificationList!=null && mNotificationList.size()>0) {

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    int size = mNotificationList.size();
                    NotificationEntitiy item = mNotificationList.get(mIndex % size);
                    mCurItem = item;
                    String msg = String.format(mTxvNotificationFormat, item.getAccount_name(), item.getCommodity_name() );
                    setText(Html.fromHtml(msg));
                    mIndex++;
                    if (mIndex<=0) {mIndex = 1; }

                    startPlayNotification();
                }
            }, 5000);

        }
    }


}
