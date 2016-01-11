package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.LastestBingoEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by baoxing on 2015/12/17.
 */
public class RunLottoryView3 extends TextView{

    private static final String TAG = RunLottoryView3.class.getSimpleName();

    /** 计算速度 */
    private static final long L_CLAC_SPEED = 47;

    /** 计时监听 */
    private OnTimesUpListener mOnTimesUpListener;

    /** 对应的最新揭晓的产品实体 */
    private LastestBingoEntity mLastestBingoEntity;

    private static DateFormat mDateFormatCountdown = new SimpleDateFormat("hh:mm:ss:SSS", Locale.SIMPLIFIED_CHINESE);

    /** 画笔 */
    private Paint mTextPaint;

    /** 开奖提示 */
    private String mStrRunLottoryHumanReadable = "";

    /** 干活的线程 */
    private volatile SlaveDrawThread mSlaveDrawThread;

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





    public RunLottoryView3(Context context) {
        this(context, null);
    }



    public RunLottoryView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }



    public RunLottoryView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes();
    }

    private void initRes() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDateFormatCountdown.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    }



    public void init2(LastestBingoEntity item) {
        this.mLastestBingoEntity = item;    //这里考虑是传clone还是引用
        LogUtils.w(TAG, "item=%s", item.toString() );
        startSlaveThread();
    }

    private synchronized void startSlaveThread() {
        LogUtils.w(TAG, "startSlaveThread()");
        if (mSlaveDrawThread == null) {
            mSlaveDrawThread = new SlaveDrawThread();
            mSlaveDrawThread.start();

        } else {
            boolean isAlive = mSlaveDrawThread.getIsAlive();
            LogUtils.w(TAG, "isAlive=%b", isAlive);
            if (!isAlive) {
                mSlaveDrawThread = new SlaveDrawThread();
                mSlaveDrawThread.start();
            }

        }


    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRunLottotyHint(canvas);
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

        int temp = (int) mLastestBingoEntity.getTimeLeft();
        mLastestBingoEntity.setTimeLeft(temp - L_CLAC_SPEED);

        return hintReadable;
    }




    /**
     * @deprecated
     * 检查是否到时了
     */
    private boolean isTimeUp() {
        boolean bIsTimeUp = false;
        long lRunLottory = mLastestBingoEntity.getRunLotteryTime();
        long lLeftDuration = lRunLottory - System.currentTimeMillis();

        if (lLeftDuration <= 0) {
            bIsTimeUp = true;
        }

        return bIsTimeUp;
    }

    /**
     * 检测正在揭晓是否到时了
     * @return
     */
    private boolean isTimeUp2() {
        boolean bIsTimeUp = false;
        if ( (mLastestBingoEntity.getTimeLeft() <= 0) && (mLastestBingoEntity.getStatus()==Constant.LatestFragment.CODE_RUNNING) ) {
            bIsTimeUp = true;
        }

        return bIsTimeUp;
    }

    /**
     * 检测是否退出线程
     * @return
     */
    private boolean isExit() {
        return (mLastestBingoEntity.getTimeLeft()<=0);
    }


    /**
     * 绘画奴隶线程
     */
    private class SlaveDrawThread extends Thread {

        private volatile boolean bIsAlive = true;

        public SlaveDrawThread() {
            super();
            LogUtils.w(TAG, "SlaveDrawThread#构造函数");
        }

        @Override
        public void run() {
            super.run();
            while (bIsAlive) {
                mStrRunLottoryHumanReadable = getHumanCountdownHint2();
                postInvalidate();

                updateSpeed();

                if (isTimeUp2()) {
                    LogUtils.e(TAG, "timeUp2...");
                    if (mOnTimesUpListener != null) {
                        mOnTimesUpListener.onTimesUp(mLastestBingoEntity);
                    }
                    this.bIsAlive = false;
                    break;
                }   //end ifTimeUP

                if (isExit()) {
                    LogUtils.e(TAG, "isExit...");
                    this.bIsAlive = false;
                    break;
                }

            }   //end while


            LogUtils.e(TAG, "end run...");
        } //end run

        private void updateSpeed() {
            try {
                Thread.sleep(L_CLAC_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void setIsAlive(boolean isAlive) {
            this.bIsAlive = isAlive;

        }

        public boolean getIsAlive() {
            return this.bIsAlive;
        }

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.e(TAG, "onDetachedFromWindow()");
        if (mSlaveDrawThread != null) {
            mSlaveDrawThread.setIsAlive(false);
        }

    }
}
