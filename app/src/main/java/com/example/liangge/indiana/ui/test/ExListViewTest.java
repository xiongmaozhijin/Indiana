package com.example.liangge.indiana.ui.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.liangge.indiana.comm.LogUtils;

/**
 * Created by baoxing on 2016/1/28.
 */
public class ExListViewTest extends ListView{
    private static final String TAG = ExListViewTest.class.getSimpleName();

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

    private boolean isCerterin = false;

    private int iPrevScrollBottom1;
    private int iScrollBottomJudgeCaptureCnt = 0;
    private static final int I_SCROLL_BOOTTOM_JUDGE_CAPTURE_CNT = 3;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow ScrollView to intercept touch events.
//                mScrollView.requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:

                mScrollView.requestDisallowInterceptTouchEvent(true);

                if (isScrollDown(ev)) {
                    int firPostion = getFirstVisiblePosition();
                    if (firPostion==0) {
                        View firstVisibleView = getChildAt(getFirstVisiblePosition());

                        if (firstVisibleView != null) {
                            if (firstVisibleView.getTop()>=0) {
                                mScrollView.requestDisallowInterceptTouchEvent(false);
                                LogUtils.e(TAG, "interupt scroll down");
                            }
                        }

                    }

                }


                if (isScrollUp(ev)) {
                    int size = getAdapter().getCount() - 1;
                    int lastVisble = getLastVisiblePosition();
                    int lastViewIndex = getLastVisiblePosition() - getFirstVisiblePosition();
                    View view = getChildAt(lastViewIndex);
                    if (view != null) {
                        if (lastVisble==size) {
                            int iCurTop = view.getTop();
                            if (iPrevScrollBottom1 == iCurTop) {
                                iScrollBottomJudgeCaptureCnt++;
                                if (iScrollBottomJudgeCaptureCnt == I_SCROLL_BOOTTOM_JUDGE_CAPTURE_CNT) {
                                    iScrollBottomJudgeCaptureCnt = 0;
                                    mScrollView.requestDisallowInterceptTouchEvent(false);
                                }
                            } else {
                                iPrevScrollBottom1 = iCurTop;
                                iScrollBottomJudgeCaptureCnt = 0;
                            }
                        }
                    }


                }



                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Allow ScrollView to intercept touch events.
                mScrollView.requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.onTouchEvent(ev);
    }





    private void handleScrollLocation(MotionEvent event) {


    }

    private boolean isCertainScrollDown = false;
    private boolean isCertainScrollUp = false;
    private static final int SCROLL_DOWN_LIMIT_DISTANCE = 5;
    private static final int OUT_CAPUTURE_CNT = 5;

    private int iScrolldwonCaputrue = 0;
    private int iScrollupCaputrue = 0;
    private int iPrevScrollY = -1;
    private int iCurScrollY;

    /**
     * 是否手指向下
     * @param ev
     * @return
     */
    private boolean isScrollDown(MotionEvent ev) {
        boolean r = false;
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            iPrevScrollY = (int) ev.getY();
            iScrolldwonCaputrue = 0;

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (!isCertainScrollDown) {
                isCertainScrollDown = true;
                iPrevScrollY = (int) ev.getY();
                iScrolldwonCaputrue = 0;
            }
            iCurScrollY = (int) ev.getY();

            if (iCurScrollY>iPrevScrollY) {
                iScrolldwonCaputrue++;
            } else {
                iScrolldwonCaputrue = 0;
            }

            LogUtils.i(TAG, "iCurScrollY=%d, iPrevScrollY=%d, iScrolldownCapture=%d", iCurScrollY, iPrevScrollY, iScrolldwonCaputrue);

            if (Math.abs(iCurScrollY-iPrevScrollY) >= SCROLL_DOWN_LIMIT_DISTANCE) {
                if (iScrolldwonCaputrue >= OUT_CAPUTURE_CNT) {
                    r = iCurScrollY-iPrevScrollY>= SCROLL_DOWN_LIMIT_DISTANCE;
                    iScrolldwonCaputrue = 0;
                }

                iPrevScrollY = iCurScrollY;
                if (r) {
                    LogUtils.e(TAG, "isScrollDown()");
                }
            }

        } else if (ev.getAction()==MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            isCertainScrollDown = false;
            iScrolldwonCaputrue = 0;
        }

        return r;
    }


    private int iPrevScrollUpY = 100000;
    private int iCurScrollUpY;
    /**
     * 是否手指向上
     * @param ev
     * @return
     */
    private boolean isScrollUp(MotionEvent ev) {
        boolean r = false;
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            iPrevScrollUpY = (int) ev.getY();
            iScrollupCaputrue = 0;

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (!isCertainScrollUp) {
                isCertainScrollUp = true;
                iPrevScrollUpY = (int) ev.getY();
                iScrollupCaputrue = 0;
            }
            iCurScrollUpY = (int) ev.getY();

            if (iCurScrollUpY < iPrevScrollUpY) {
                iScrollupCaputrue++;
            } else {
                iScrollupCaputrue = 0;
            }

            if (Math.abs(iCurScrollUpY-iPrevScrollUpY) >= SCROLL_DOWN_LIMIT_DISTANCE) {

                if (iScrollupCaputrue >= OUT_CAPUTURE_CNT) {
                    r = ( (iPrevScrollUpY-iCurScrollUpY) >= SCROLL_DOWN_LIMIT_DISTANCE);

                }


                iPrevScrollUpY = iCurScrollUpY;
                if (r) {
                    LogUtils.e(TAG, "isScrollUp()");
                }
            }

        } else if (ev.getAction()==MotionEvent.ACTION_CANCEL || ev.getAction()==MotionEvent.ACTION_UP) {
            iScrollupCaputrue = 0;
            isCertainScrollUp = false;

        }

        return r;
    }




}
