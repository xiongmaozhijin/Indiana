package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.LastestBingoEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by baoxing on 2015/12/17.
 */
public class RunLottoryView4 extends TextView{

    private static final String TAG = RunLottoryView4.class.getSimpleName();

    /** 计算速度 */
    private static long L_CLAC_SPEED = 47;

    /** 计时监听 */
    private OnTimesUpListener mOnTimesUpListener;

    /** 对应的最新揭晓的产品实体 */
    private LastestBingoEntity mLastestBingoEntity;

    private static DateFormat mDateFormatCountdown = new SimpleDateFormat("hh:mm:ss:SSS", Locale.SIMPLIFIED_CHINESE);

    /** 画笔 */
    private Paint mTextPaint;

    /** 开奖提示 */
    private String mStrRunLottoryHumanReadable = "";

    /**
     * 设置计时监听
     * @param listener
     */
    public void setOnTimesUpListener(OnTimesUpListener listener) {
        this.mOnTimesUpListener = listener;

    }



    /**
     * 计时监听
     */
    public interface OnTimesUpListener {
        /**
         * 开奖时间到了回调
         * @param lastestBingoEntity
         */
        void onTimesUp(LastestBingoEntity lastestBingoEntity);
    }





    public RunLottoryView4(Context context) {
        this(context, null);
    }



    public RunLottoryView4(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }



    public RunLottoryView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes();
    }

    private void initRes() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDateFormatCountdown.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

    }


    /**
     * 初始化必要的资源
     * @param item
     * @param updateSpeed   计算速率
     */
    public void init2(LastestBingoEntity item, long updateSpeed) {
        mLastestBingoEntity = item;    //这里考虑是传clone还是引用，暂时只能传引用，直接修改改变值
        L_CLAC_SPEED = updateSpeed;

        LogUtils.w(TAG, "item=%s", item.toString());

        initStartDraw();

        post(new Runnable() {
            @Override
            public void run() {
                updateNotifyInfo();

            }
        });

    }


    public void clearRes() {
        initEndDraw();
    }


    /**
     * 重置开始绘画资源
     */
    private void initStartDraw() {
        bIsNeedDrawAgain = true;
    }

    /**
     * 取消绘制
     */
    private void initEndDraw() {
        bIsNeedDrawAgain = false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRunLottotyHint(canvas);
        updateNotifyInfo();
    }

    /**
     * 绘画开奖提示
     * @param canvas
     */
    private void drawRunLottotyHint(Canvas canvas) {
        int iViewHeight = getHeight();
        int iViewWidth = getWidth();
        int iX = iViewWidth / 2;
        int iY = iViewHeight*9/10;

        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(iViewHeight * 8 / 10);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.WHITE);
        canvas.drawText(mStrRunLottoryHumanReadable, iX, iY, mTextPaint);
    }



    /**
     * 得到人类可读的倒计时(揭晓中，非计算中)
     * @return
     */
    private String getHumanCountdownHint2() {
        LogUtils.i(TAG, "getHumanCountdownHint2()");
        long lLeftTime = mLastestBingoEntity.getTimeLeft();
        String hintReadable;


        if (lLeftTime>0 && mLastestBingoEntity.getStatus()==Constant.LatestFragment.CODE_RUNNING) {
//            hintReadable = mDateFormatCountdown.format(lLeftTime);
//            int hour = (int) (lLeftTime/1000/60/60);
//            int min = (int) (lLeftTime/1000/60);
//            int sec = (int) (lLeftTime/1000);
//            int mes = (int) (lLeftTime - hour*60*60*1000 - min*60*1000 - sec*1000);

            int hour = (int) (lLeftTime/1000/60/60);
            lLeftTime = lLeftTime - hour*1000*60*60;
            int min = (int) (lLeftTime/1000/60);
            lLeftTime = lLeftTime - min*1000*60;
            int sec = (int) (lLeftTime/1000);
            lLeftTime = lLeftTime - sec*1000;
            int mes = (int) lLeftTime;

            hintReadable = String.format("%02d:%02d:%02d:%03d", hour, min, sec, mes);

            LogUtils.w(TAG, "hour=%d, min=%d, sec=%d, mes=%d", hour, min, sec, mes);
            LogUtils.w(TAG, "lLeftTime=%d, readable=%s", lLeftTime, hintReadable);

        } else {
            if (mLastestBingoEntity.getStatus()== Constant.LatestFragment.CODE_CLAC_ING) {
                hintReadable = getResources().getString(R.string.latest_item_runninglottory_wait_hint);

            } else {
                hintReadable = getResources().getString(R.string.latest_item_runninglottory_request_hint1);

            }

        }

        return hintReadable;
    }


    /**
     * 检测正在揭晓是否到时了
     * @return
     */
    private synchronized boolean isTimeUp2() {
        boolean bIsTimeUp = false;
        if ( (mLastestBingoEntity.getTimeLeft() <= 0) && (mLastestBingoEntity.getStatus()!=Constant.LatestFragment.CODE_ALREADY_RUN)  ) {
            bIsTimeUp = true;
        }

        return bIsTimeUp;
    }


    /** 是否需要继续绘制 */
    private boolean bIsNeedDrawAgain = true;

    /**
     * 更新和通知信息
     */
    private void updateNotifyInfo() {

        if (bIsNeedDrawAgain) {

            if (isTimeUp2()) {
                initEndDraw();
                notifyTimeUp();

            }   //end ifTimeUP

            mStrRunLottoryHumanReadable = getHumanCountdownHint2();
            postInvalidate();
        }

    }



    private void notifyTimeUp() {
        LogUtils.e(TAG, "timeUp2...");

        if (mOnTimesUpListener != null) {
            mOnTimesUpListener.onTimesUp(mLastestBingoEntity);
        }

    }






    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.e(TAG, "onDetachedFromWindow()");

    }



}
