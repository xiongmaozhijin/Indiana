package com.example.liangge.indiana.ui.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.liangge.indiana.comm.LogUtils;

/**
 * Created by baoxing on 2015/12/16.
 */

public class RunLottotyView extends SurfaceView implements SurfaceHolder.Callback {


    private static final String TAG = RunLottotyView.class.getSimpleName();

    private boolean bDrawThreadAclive;

    private DrawThread mDrawThread;

    public RunLottotyView(Context context) {
        this(context, null);

    }

    public RunLottotyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public RunLottotyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes();
    }

    private void initRes() {
        mDrawThread = new DrawThread(this.getHolder());
        this.getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.bDrawThreadAclive = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.bDrawThreadAclive = true;
        mDrawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.bDrawThreadAclive = false;
    }

    public class DrawThread extends Thread {

        private SurfaceHolder mSurfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.mSurfaceHolder = surfaceHolder;

        }

        @Override
        public void run() {
            int h = getHeight();
            Canvas canvas = null;
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(22);
            paint.setTextAlign(Paint.Align.CENTER);
            int i = 0;
            while (bDrawThreadAclive) {
                try {
                    i++;
                    canvas = mSurfaceHolder.lockCanvas();
                    canvas.drawColor(Color.YELLOW);
                    canvas.drawText("yaerwrwrewrewrqr" + i, 0, h, paint);
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    if (canvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);

                    }
                }


            }

        }

    }

}

