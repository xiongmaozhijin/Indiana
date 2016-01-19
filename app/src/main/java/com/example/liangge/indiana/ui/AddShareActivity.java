package com.example.liangge.indiana.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.AddShareBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.user.BingoRecordEntity;

import org.w3c.dom.Text;

/**
 * 添加晒单
 * Created by baoxing on 2016/1/19.
 */
public class AddShareActivity extends SimpleAdapterBaseActivity2{


    private static final String TAG = AddShareActivity.class.getSimpleName();

    /** 提示 */
    private View mViewTips;

    /** 编辑Wrapper */
    private View mViewEdit;

    /** 晒单主体*/
    private EditText mEdtTitle;

    /** 获奖感言 */
    private EditText mEdtContent;

    /** 获奖商品 */
    private TextView mTxvGoodName;

    /** 参与次数 */
    private TextView mTxvSpentAmount;

    /** 幸运号码 */
    private TextView mTxvLuckyNumber;

    /** 揭晓时间 */
    private TextView mTxvRevealTime;


    private AddShareBiz mAddShareBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addshare);
        initView();
        initManager();
        initRes();
        initState();
    }

    private void initState() {
        BingoRecordEntity item = mAddShareBiz.getBingoItem();
        mTxvGoodName.setText(item.getTitle());
        mTxvSpentAmount.setText(item.getBuyCnt() + "");
        mTxvLuckyNumber.setText(item.getLuckyNumber());
        mTxvRevealTime.setText(item.getHumanAlreadyRunLotteryTime());

    }

    private void initRes() {

    }

    private void initManager() {
        mAddShareBiz = AddShareBiz.getInstance(this);
    }

    private void initView() {
        mViewTips = findViewById(R.id.tips);
        mViewTips.setVisibility(View.VISIBLE);
        mViewEdit = findViewById(R.id.editor);
        mViewEdit = findViewById(R.id.editor);
        mEdtTitle = (EditText) findViewById(R.id.myTitle);
        mEdtContent = (EditText) findViewById(R.id.content);
        mTxvGoodName = (TextView) findViewById(R.id.txtGoodName);
        mTxvSpentAmount = (TextView) findViewById(R.id.txtSpentAmount);
        mTxvLuckyNumber = (TextView) findViewById(R.id.txtLuckyNumber);
        mTxvRevealTime = (TextView) findViewById(R.id.txtRevealTime);

    }


    /**
     * 开始晒单
     * @param view
     */
    public void onBtnGoEditor(View view) {
        mViewTips.setVisibility(View.GONE);
    }

    /**
     * 发布晒单
     * @param view
     */
    public void onBtnSubmit(View view) {
        String title = mEdtTitle.getText().toString();
        String content = mEdtContent.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            String hint = getResources().getString(R.string.share_not_empty);
            LogUtils.toastShortMsg(this, hint);
            return;
        }

        if (title!=null && content.length()<6) {
            String hint = getResources().getString(R.string.share_more_title);
            LogUtils.toastShortMsg(this, hint);
            return;
        }

        if (content!=null && content.length() < 30) {
            String hint = getResources().getString(R.string.share_more_content);
            LogUtils.toastShortMsg(this, hint);
            return;
        }
        //TODO 判断照片


        mAddShareBiz.onBtnSubmit();
    }


    public void onBtnBack(View view) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddShareBiz.onDestroy();
    }

    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }


}
