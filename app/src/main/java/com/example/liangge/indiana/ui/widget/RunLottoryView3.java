package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.model.LastestBingoEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by baoxing on 2015/12/17.
 */
public class RunLottoryView3 extends TextView{

    private static final String TAG = RunLottoryView3.class.getSimpleName();

    /** 计时监听 */
    private OnTimesUpListener mOnTimesUpListener;

    /** 对应的最新揭晓的产品实体 */
    private LastestBingoEntity mLastestBingoEntity;

    private DateFormat mDateFormatCountdown = new SimpleDateFormat("hh:mm:ss:SSS");

    /** 画笔 */
    private Paint mTextPaint;

    /** 开奖提示 */
    private String mStrRunLottoryHumanReadable = "";

    /** 干活的辅助线程 */
    private DrawSlaveThread mDrawSlaveThread;

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

    }


    public void init(LastestBingoEntity lastestBingoEntity) {
        this.mLastestBingoEntity = lastestBingoEntity;
        startDrawWork();
    }

    private void startDrawWork() {
        post(new Runnable() {
            @Override
            public void run() {
                stopDrawSlaveThread();
                startDrawSlaveThread();

            }
        });
    }

    private void startDrawSlaveThread() {
        mDrawSlaveThread = new DrawSlaveThread();
        mDrawSlaveThread._startDrawSlaveThread();
        mDrawSlaveThread.start();
    }

    private void stopDrawSlaveThread() {
        if (mDrawSlaveThread != null) {
            mDrawSlaveThread._stopDrawSlaveThread();
            mDrawSlaveThread = null;
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
     * 得到人类可读的倒计时
     */
    private String getHumanCountdownHint() {
        long lRunLottoryTime = mLastestBingoEntity.getRunLotteryTime();
        long lLeftDuration = lRunLottoryTime - System.currentTimeMillis();
        String readableHint;

        if (lLeftDuration > 0) {
            readableHint = mDateFormatCountdown.format(new Date(lLeftDuration));

        } else {
            readableHint = getResources().getString(R.string.latest_item_runninglottory_wait_hint);

        }

        return readableHint;
    }


    /**
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
     * 绘画干活线程
     */
    private class DrawSlaveThread extends Thread {

        private volatile boolean bIsActive;

        @Override
        public void run() {
            boolean bIsTimeUp;

            while (bIsActive) {

                try {
                    Thread.sleep(100);

                    bIsTimeUp = isTimeUp();
                    mStrRunLottoryHumanReadable = getHumanCountdownHint();
                    postInvalidate();
                    if (bIsTimeUp) {
                        if (mOnTimesUpListener != null) {
                            mOnTimesUpListener.onTimesUp(mLastestBingoEntity);
                        }
                        stopDrawSlaveThread();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }   //end while

        }   //end run

        public void _startDrawSlaveThread() {
            this.bIsActive = true;
        }

        public void _stopDrawSlaveThread() {
            this.bIsActive = false;
        }

    }




}
