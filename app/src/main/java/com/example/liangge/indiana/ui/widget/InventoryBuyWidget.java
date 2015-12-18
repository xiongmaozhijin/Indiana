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

    /**
     * 购买次数改变监听
     */
    public interface OnBuyCntChangeListener {
        /**
         * 购买次数回调
         * @param curBuyCnt
         */
        void onBuyCntChange(int curBuyCnt);
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
     * @param inventoryEntity
     */
    public void initInventoryBuyWidget(InventoryEntity inventoryEntity) {
        this.mInventoryEntity = inventoryEntity;
        this.mIMaxBuyCnt = mInventoryEntity.getNeedPeopleCounts();
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
                mICurBuyCnt--;
                mEdtNumber.setText(mICurBuyCnt + "");
            }
        });

        mBtnPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mICurBuyCnt++;
                mEdtNumber.setText(mICurBuyCnt + "");
            }
        });

        mEdtNumber.addTextChangedListener(new TextWatcher() {

            private int beforeChanged;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s!=null && s!="") {
                    try {
                        beforeChanged = Integer.parseInt(s.toString());
                    } catch (Exception e) {
                        beforeChanged = 1;
                    }
                } else {
                    beforeChanged = 1;
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.w(TAG, "onTextChanged(). s=%s", s);
                boolean bReset = false;
//                int iCurBuyCntCopy = mI
                if ((s != null) && (s != "")) {

                    try {
                        mICurBuyCnt = Integer.parseInt(s.toString());

                        if (mICurBuyCnt <= 0) {
                            mICurBuyCnt = beforeChanged;
                            bReset = true;

                        }  else if (mICurBuyCnt > mIMaxBuyCnt) {
                            mICurBuyCnt = mIMaxBuyCnt;
                            bReset = true;

                        }


                    } catch (Exception e) {
                        bReset = true;

                    }


                } else {
                    bReset = true;
                }

                if (bReset) {
                    mEdtNumber.setText(mICurBuyCnt + "");
                }

                notifyListener(mICurBuyCnt);

                LogUtils.w(TAG, "iCurBuyCnt=%d", mICurBuyCnt);
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.w(TAG, "afterTextChanged() s=%s", s.toString());
            }
        });


    }


    private void notifyListener(int iCurBuyCnt) {
        LogUtils.w(TAG, "notifyListener(). iCurBuyCnt=%d", iCurBuyCnt);
        if (this.mInventoryEntity != null) {
            this.mInventoryEntity.setBuyCounts(iCurBuyCnt);
        }

        if (this.mOnBuyCntChangeListener != null) {
            this.mOnBuyCntChangeListener.onBuyCntChange(iCurBuyCnt);
        }


    }


}
