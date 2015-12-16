package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.LastestBingoEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @deprecated
 * 滚动时会出现黑屏，使用api14以上支持的TextureView
 * 开奖倒计时View
 * Created by baoxing on 2015/12/16.
 */
public class RunLottoryView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = RunLottoryView.class.getSimpleName();

    /** 计时监听 */
    private OnTimesUpListener mOnTimesUpListener;

    /** 对应的最新揭晓的产品实体 */
    private LastestBingoEntity mLastestBingoEntity;

    /** 绘画奴隶线程 */
    private DrawWorkerThread mDrawWorkerThread;


    private DateFormat mDateFormatCountdown = new SimpleDateFormat("hh:mm:ss:SSS");

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

    public RunLottoryView(Context context) {
        this(context, null);
    }

    public RunLottoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RunLottoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes();
    }

    private void initRes() {
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        startDrawThread();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }


    public void initConf(LastestBingoEntity itemInfo) {
        this.mLastestBingoEntity = itemInfo;
        initDrawThread();
    }

    /**
     * 初始化资源
     */
    private void initDrawThread() {
        this.getHolder().addCallback(this);
        post(new Runnable() {
            @Override
            public void run() {
                startDrawThread();

            }
        });
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
     * 绘画奴隶线程
     */
    private class DrawWorkerThread extends Thread {
        private SurfaceHolder holder;
        private boolean isActive;

        public DrawWorkerThread(SurfaceHolder holder) {
            this.holder = holder;
        }


        @Override
        public void run() {

            int iViewHeight = getHeight();
            int iViewWidth = getWidth();
            int iX = iViewWidth / 2;
            int iY = iViewHeight*9/10;

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
            paint.setTextSize(iViewHeight*8/10);
            paint.setTextAlign(Paint.Align.CENTER);

            String strCountdwonHint = "...";

            while (isActive) {

                strCountdwonHint = getHumanCountdownHint();

                drawContent(iX, iY, paint, strCountdwonHint);

                if (isTimeUp() ) {
                    strCountdwonHint = getHumanCountdownHint();
                    drawContent(iX, iY, paint, strCountdwonHint);
                    if (mOnTimesUpListener != null) {
                        mOnTimesUpListener.onTimesUp(mLastestBingoEntity);
                    }

                    stopDrawThread();
                }

            }   //end while



        }

        @NonNull
        private synchronized void drawContent(int iX, int iY, Paint paint, String strCountdwonHint) {
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    canvas.drawText(strCountdwonHint, iX, iY, paint);
                    Thread.sleep(20);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {
                if (canvas != null) {
                    this.holder.unlockCanvasAndPost(canvas);
                }
            }
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


        public void setThreadActive(boolean isActive) {
            this.isActive = isActive;
        }

    }




    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.w(TAG, "onAttachedToWindow()");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.w(TAG, "onDetachedFromWindow()");
        stopDrawThread();
    }

    private void stopDrawThread() {
        if (mDrawWorkerThread != null) {
            mDrawWorkerThread.setThreadActive(false);
            mDrawWorkerThread = null;
        }
    }

    private void startDrawThread() {
        if (mDrawWorkerThread == null) {
            mDrawWorkerThread = new DrawWorkerThread(getHolder());
            mDrawWorkerThread.setThreadActive(true);
            mDrawWorkerThread.start();
        }

    }




}
