package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.InventoryEntity;

/**
 * 购买控件
 * Created by baoxing on 2015/12/17.
 */
public class InventoryBuyWidget extends FrameLayout{

    private static final String TAG = InventoryBuyWidget.class.getSimpleName();

    private Button mBtnSub;

    private Button mBtnPlus;

    private EditText mEdtNumber;

    private OnBuyCntChangeListener mOnBuyCntChangeListener;

    /**
     * 当前的购物清单
     */
    private InventoryEntity mInventoryEntity;

    /** 当前购买次数 */
    private int mICurBuyCnt = 1;

    /** 最大购买次数，总需的总次数 */
    private int mIMaxBuyCnt = 1000;

    private int mIMinBuyCnt = 1;

    /**
     * 购买次数改变监听
     */
    public interface OnBuyCntChangeListener {
        /**
         * 购买次数回调
         * @param curBuyCnt
         */
        void onBuyCntChange(long orderId, int curBuyCnt);

        /**
         * 购买次数回调
         * @param inventoryEntity
         */
        void onBuyCntChange(InventoryEntity inventoryEntity);
    }

    public void setOnBuyCntChangeListener(OnBuyCntChangeListener listener) {
        this.mOnBuyCntChangeListener = listener;
    }

    /**
     * @deprecated
     * 这个初始化方法是较抽象的，但不是方便的，使用该方法还需要回调
     * 初始化购买控件
     * @param maxBuyCnt
     * @param curBuyCnt
     */
    public void initInventoryBuyWidget(int maxBuyCnt, int curBuyCnt) {
        this.mIMaxBuyCnt = maxBuyCnt;
        this.mICurBuyCnt = curBuyCnt;
    }

    /**
     * 初始化购买控件
     * @param maxBuyCnt
     */
    public void initInventoryBuyWidget(int maxBuyCnt) {
        LogUtils.i(TAG, "initInventoryBuyWidget()maxBuyCnt=%d", maxBuyCnt);
        this.mIMaxBuyCnt = maxBuyCnt;
    }

    /**
     * 初始化购买控件
     * @param inventoryEntity
     */
    public void initInventoryBuyWidget(InventoryEntity inventoryEntity) {
        this.mInventoryEntity = inventoryEntity;
        this.mIMaxBuyCnt = mInventoryEntity.getLackPeopleCounts();
        this.mIMinBuyCnt = mInventoryEntity.getMinBuyCnt();
        if (this.mIMinBuyCnt == 0) {
            this.mIMinBuyCnt = 1;
        }

        post(new Runnable() {
            @Override
            public void run() {
                if (mInventoryEntity.getLackPeopleCounts() > 0) {
                    mEdtNumber.setText(mInventoryEntity.getBuyCounts()+"");
                }

            }
        });

    }






    public InventoryBuyWidget(Context context) {
        this(context, null);
    }

    public InventoryBuyWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public InventoryBuyWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.widget_inventory_buy, this);
        mBtnSub = (Button) view.findViewById(R.id.inventory_buy_sub_btn);
        mBtnPlus = (Button) view.findViewById(R.id.inventory_buy_plus_btn);
        mEdtNumber = (EditText) view.findViewById(R.id.inventory_buy_edit);

        mBtnSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIMaxBuyCnt > 0) {
                    LogUtils.i(TAG, "onBtnSub()");
                    mICurBuyCnt--;
                    mEdtNumber.setText(mICurBuyCnt + "");
                }

            }
        });

        mBtnPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIMaxBuyCnt > 0) {
                    LogUtils.i(TAG, "onBtnPlus()");
                    mICurBuyCnt++;
                    mEdtNumber.setText(mICurBuyCnt + "");
                }

            }
        });

        mEdtNumber.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtils.w(TAG, "beforTextChange=%s", s.toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.w(TAG, "onTextChanged(). s=%s", s.toString());
                String str = s.toString();

                if (str==null || str.equals("")) {
                    mEdtNumber.setText(mIMinBuyCnt+"");
                    notifyListener(mIMinBuyCnt);

                } else {
                    try {
                        int number = Integer.parseInt(str);
                        if (number< mIMinBuyCnt) {
                            mEdtNumber.setText(mIMinBuyCnt+"");
                            notifyListener(mIMinBuyCnt);
                        } else if (number > mIMaxBuyCnt) {
                            mEdtNumber.setText(mIMaxBuyCnt+"");
                            notifyListener(mIMaxBuyCnt);
                        } else {
                            notifyListener(number);

                        }

                    } catch (Exception e) {
                        mEdtNumber.setText(mIMinBuyCnt+"");
                        notifyListener(mIMinBuyCnt);
                    }



                }



            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }


    private void notifyListener(int iCurBuyCnt) {
        LogUtils.w(TAG, "notifyListener(). iCurBuyCnt=%d", iCurBuyCnt);
        this.mICurBuyCnt = iCurBuyCnt;

        if (this.mOnBuyCntChangeListener != null) {
            if (this.mInventoryEntity != null) {
                this.mInventoryEntity.setBuyCounts(iCurBuyCnt);
            }
            this.mOnBuyCntChangeListener.onBuyCntChange(this.mInventoryEntity.getActivityID(), iCurBuyCnt);
            this.mOnBuyCntChangeListener.onBuyCntChange(this.mInventoryEntity);

        }


    }

    /**
     * 获取购买次数
     * @return
     */
    public int getCurBuyCnt() {
        return this.mICurBuyCnt;
    }




}
