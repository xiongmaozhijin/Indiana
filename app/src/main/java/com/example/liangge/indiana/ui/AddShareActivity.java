package com.example.liangge.indiana.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.AddShareBiz;
import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 添加晒单
 * Created by baoxing on 2016/1/19.
 */
public class AddShareActivity extends SimpleAdapterBaseActivity2{


    private static final String TAG = AddShareActivity.class.getSimpleName();

    private static final int REQUEST_IMAGE = 2;

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

    private LinearLayout mViewShareGrallyWrapper;

    private ArrayList<String> mSelectPath = new ArrayList<>();

    private AddShareBiz mAddShareBiz;

    private static DisplayImageOptions mDisplayImageOptions;

    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addshare);
        initView();
        initManager();
        initRes();
        initState();
    }

    /**
     * 配置开源组件ImageLoader的参数
     * @param context
     */
    private void initImageLoaderConf(Context context) {
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.main_banner_img_load_empty_uri)
                .showImageOnFail(R.drawable.main_banner_img_load_fail)
                .showImageOnLoading(R.drawable.main_product_item_img_onloading)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();

    }


    private void initState() {
        BingoRecordEntity item = mAddShareBiz.getBingoItem();
        mTxvGoodName.setText(item.getTitle());
        mTxvSpentAmount.setText(item.getBuyCnt() + "");
        mTxvLuckyNumber.setText(item.getLuckyNumber());
        mTxvRevealTime.setText(item.getHumanAlreadyRunLotteryTime());

    }

    private void initRes() {
        initImageLoaderConf(this);

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
        mViewShareGrallyWrapper = (LinearLayout) findViewById(R.id.share_grally_wrapper);
        mBtnSubmit = (Button) findViewById(R.id.submit);

    }


    /**
     * 开始晒单
     * @param view
     */
    public void onBtnGoEditor(View view) {
        mViewTips.setVisibility(View.GONE);
    }

    /**
     * 添加图片
     * @param view
     */
    public void onBtnAddImage(View view) {
        LogUtils.i(TAG, "onBtnAddImage()");
        Intent intent = new Intent(AddShareActivity.this, me.nereo.multi_image_selector.MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(me.nereo.multi_image_selector.MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
        // 最大可选择图片数量
        intent.putExtra(me.nereo.multi_image_selector.MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 5);
        // 选择模式
        intent.putExtra(me.nereo.multi_image_selector.MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if(mSelectPath != null && mSelectPath.size()>0){
            intent.putStringArrayListExtra(me.nereo.multi_image_selector.MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
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
        if (mSelectPath==null || mSelectPath.size()<3) {
            String hint = getResources().getString(R.string.share_image_cnt_hint);
            LogUtils.toastShortMsg(this, hint);
            return;
        }

        mAddShareBiz.setRequestInfo(title, content, mSelectPath);
        mAddShareBiz.onBtnSubmit();
    }


    public void onBtnBack(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mAddShareBiz.isWorking()) {
            String hint = getResources().getString(R.string.share_post_continue);
            LogUtils.toastShortMsg(this, hint);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for(String p: mSelectPath){
                    sb.append(p);
                    sb.append("\n");
                }
                LogUtils.i(TAG, "paths=%s", sb.toString());
                displayUpdateImgs(mSelectPath);
            }
        }
    }

    //TODO
    private void displayUpdateImgs(ArrayList<String> mSelectPath) {
        if (mSelectPath != null) {
            final LinearLayout viewWrapper = mViewShareGrallyWrapper;

            viewWrapper.removeAllViews();
            for (int i=0, len=mSelectPath.size(); i<len; i++) {
                ImageView imageView = new ImageView(this);
//                ImageView imageView = (ImageView) View.inflate(this, R.layout.image_item, null);
                viewWrapper.addView(imageView);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                lp.width = LocalDisplay.dp2px(84.0f);
                lp.height = LocalDisplay.dp2px(84.0f);
                lp.setMargins(LocalDisplay.dp2px(2f), 0, LocalDisplay.dp2px(2f), 0);
                imageView.requestLayout();
                ImageLoader.getInstance().displayImage("file://" + mSelectPath.get(i), imageView, mDisplayImageOptions);

            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddShareBiz.onDestroy();
    }

    @Override
    protected void handleUIMessage(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.Comm_Activity.COMM_NET_START)) {
            handleUINetStart();

        } else if (strUIAction.equals(UIMessageConts.Comm_Activity.COMM_NET_SUCCESS)) {
            handleUINetSuccess();

        } else if (strUIAction.equals(UIMessageConts.Comm_Activity.COMM_NET_FAILED)) {
            handleUINetFailed();

        } else if (strUIAction.equals(UIMessageConts.AddShareOrdersMessage.DEAL_IMGS)) {
            handleUIDealImgs();
        }

    }

    private void handleUIDealImgs() {
        mBtnSubmit.setEnabled(false);
    }

    private void handleUINetFailed() {
        mBtnSubmit.setEnabled(true);
    }

    private void handleUINetSuccess() {
        mBtnSubmit.setEnabled(true);
    }

    private void handleUINetStart() {
        mBtnSubmit.setEnabled(false);
    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }


}
