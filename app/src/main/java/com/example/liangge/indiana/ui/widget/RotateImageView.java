package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by baoxing on 2015/12/17.
 */
public class RotateImageView extends ImageView {

    private RotateViewThread mRotateViewThread;

    public RotateImageView(Context context) {
        this(context, null);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        startRotateview();
    }

    /**
     * 启动动画
     */
    public void startRotateview() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mRotateViewThread == null) {
                    mRotateViewThread = new RotateViewThread();
                    mRotateViewThread.setRoteViewThreadActive(true);
                    mRotateViewThread.start();
                }
            }
        });
    }

    /**
     * 停止动画
     */
    public void stopRotateview() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mRotateViewThread != null) {
                    mRotateViewThread.setRoteViewThreadActive(false);
                    mRotateViewThread = null;
                }
            }
        });
    }

    private int mRotateDegress = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        int h = getHeight();
        int w = getWidth();
        canvas.rotate(Math.abs(mRotateDegress) % 360, w/2, h/2);
        super.onDraw(canvas);

    }


    private class RotateViewThread extends Thread {
        private boolean isActive;

        @Override
        public void run() {
            while (this.isActive) {
                try {
                    Thread.sleep(30);
                    mRotateDegress += 3;
                    postInvalidate();

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

        public void setRoteViewThreadActive(boolean isActive) {
            this.isActive = isActive;
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mRotateViewThread != null) {
            mRotateViewThread.setRoteViewThreadActive(false);
            mRotateViewThread = null;
        }
    }
}
