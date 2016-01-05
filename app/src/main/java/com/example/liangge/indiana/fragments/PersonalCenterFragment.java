package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.LogSignInBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.ui.user.LogSignInActivity;
import com.example.liangge.indiana.ui.user.BingoRecordActivity;
import com.example.liangge.indiana.ui.user.IndianaRecordActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends BaseFragment {

    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    private PersonalCenterBiz mPersonalCenterBiz;

    private LogSignInBiz mLogSignInBiz;


    private UIMsgRecevie mUIMsgRecevie;



    /** 用户头像 */
    private ImageView mImgViewUserPortain;

    /** 登录/注册/已登录 */
    private TextView mBtnLogHint;

    /** 简单的用户信息显示 */
    private TextView mTxvUserInfo;

    /** 软件相关信息按钮 */
    private ImageButton mImgBtnInfo;

    /** 夺宝记录 */
    private View mBtnIndianaRecord;

    /** 中奖记录 */
    private View mBtnBingoRecord;

    /** 充值记录 */
    private View mBtnAccountDetail;

    /** 充值 */
    private View mViewRecharge;

    /** 正在进行 */
    private View mViewOnGoing;


    private DisplayImageOptions mDisplayImageOptions;


    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_user, container, false);
        initViewNew(view);

        return view;
    }

    private void initViewNew(View view) {
        mImgViewUserPortain = (ImageView) view.findViewById(R.id.f_personal_user_portain);
        mBtnLogHint = (TextView) view.findViewById(R.id.f_personal_log_signup_logout);
        mImgBtnInfo = (ImageButton) view.findViewById(R.id.f_personal_software_info);
        mTxvUserInfo = (TextView) view.findViewById(R.id.f_personal_userinfo_1);
        mViewRecharge = view.findViewById(R.id.user_recharge);

        mBtnIndianaRecord = view.findViewById(R.id.f_personal_txvbtn_indiana_record);
        mBtnBingoRecord = view.findViewById(R.id.f_personal_txvbtn_bingo_record);


        mViewRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecharge();
            }
        });
        mBtnIndianaRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnIndianaRecord();
            }
        });
        mBtnBingoRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnBingoRecord();
            }
        });

        mBtnLogHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLogInOrLogOut();
            }
        });

    }

    /**
     * 登录注册/退出
     */
    private void onBtnLogInOrLogOut() {
        LogUtils.i(TAG, "onBtnLogInOrLogOut()");
       if (judgeLoginOrStartActivity()) {
           //退出 TODO
            mPersonalCenterBiz.logOut();
       }

    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
        initRes();
    }

    private void initRes() {
        initImageLoaderConf();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAlreadyEnter()) {
            mPersonalCenterBiz.onResume(true);
        }
    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(getActivity());
        mLogSignInBiz = LogSignInBiz.getInstance(getActivity());

    }


    private void initImageLoaderConf() {
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



    @Override
    protected void registerUIBroadCast() {
        if (mUIMsgRecevie == null) {
            mUIMsgRecevie = new UIMsgRecevie();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            getActivity().registerReceiver(mUIMsgRecevie, filter);
        }

    }

    @Override
    protected void unRegisterUIBroadCast() {
        if (mUIMsgRecevie != null) {
            getActivity().unregisterReceiver(mUIMsgRecevie);
            mUIMsgRecevie = null;
        }

    }


    private class UIMsgRecevie extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String intentAction = intent.getAction();
                if (intentAction !=  null) {
                    String uiAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                    if (uiAction != null) {
                        handleUIMsg(uiAction);
                    }
                }
            }
        }

    }

    private void handleUIMsg(String uiAction) {
        if (uiAction.equals(UIMessageConts.PersonalCenterMessage.M_INIT_LOGIN_INFO)) {

        } else if (uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_START)
                        || uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_FAILED)
                        || uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_SUCCESS)){

            handleUILogOut(uiAction);
        } else if (uiAction.equals(UIMessageConts.PersonalCenterMessage.PERSONALCENTER_M_UPDATE_USER_INFO)) {
            handleUIUpdateUserInfo();
        }

    }

    /**
     * 更新个人信息
     */
    private void handleUIUpdateUserInfo() {
        LogUtils.i(TAG, "handleUIUpdateUserInfo()");
        if (mPersonalCenterBiz.isLogin()) {
            initLogInUIState();

        } else {
            initLogOutState();

        }

    }

    /**
     * 处理退出登录
     */
    private void handleUILogOut(String uiAction) {
        if (uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_START)) {
            String hint = getResources().getString(R.string.f_personalcenter_logouting_hint);
            LogUtils.toastShortMsg(getActivity(), hint);

        } else if (uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_FAILED)) {
            String hint = getResources().getString(R.string.f_personalcenter_logouting_hint_fail);
            LogUtils.toastShortMsg(getActivity(), hint);

        } else if (uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_SUCCESS)) {
            initLogOutState();
        }

    }


    //夺宝记录
    public void onBtnIndianaRecord() {
        if (judgeLoginOrStartActivity()) {
            Intent intent = new Intent(getActivity(), IndianaRecordActivity.class);
            startActivity(intent);
        }
    }

    //中奖记录
    public void onBtnBingoRecord() {
        if (judgeLoginOrStartActivity()) {
            Intent intent = new Intent(getActivity(), BingoRecordActivity.class);
            startActivity(intent);
        }
    }

    //收货地址
    public void onBtnTakeOverGoods() {
        if (judgeLoginOrStartActivity()) {

        }
    }

    //账户明细
    public void onBtnAccountDetail() {
        if (judgeLoginOrStartActivity()) {

        }
    }

    //个人信息
    public void onBtnPersonalInfo() {
        if (judgeLoginOrStartActivity()) {

        }
    }

    //联系客服
    public void onBtnContactCustomer() {
        if (judgeLoginOrStartActivity()) {

        }
    }

    /**
     * 充值
     */
    public void onRecharge() {

    }




    /**
     * 处理退出时的ui变化
     */
    private void initLogOutState() {
        LogUtils.i(TAG, "initLogOutState()");
        mBtnLogHint.setText(getResources().getString(R.string.f_personal_btn_log_signup));
        mImgViewUserPortain.setImageResource(R.drawable.ic_avatar_default);
        mTxvUserInfo.setText("");
        mTxvUserInfo.setVisibility(View.INVISIBLE);
    }

    /**
     * 处理登录了得ui变化
     */
    private void initLogInUIState() {
        LogUtils.i(TAG, "initLogInUIState()");
        mTxvUserInfo.setVisibility(View.VISIBLE);
        String strUserInfoFormat = getResources().getString(R.string.f_personal_userinfo_1);
        String strUserInfo = String.format(strUserInfoFormat, mPersonalCenterBiz.getUserInfo().getNickname(), mPersonalCenterBiz.getUserInfo().getBalance());
        ImageLoader.getInstance().displayImage(mPersonalCenterBiz.getUserInfo().getPhoto(), mImgViewUserPortain, mDisplayImageOptions);
        mTxvUserInfo.setText(strUserInfo);


        mBtnLogHint.setText(getResources().getString(R.string.f_personal_btn_log_signup_2));

    }


    private boolean judgeLoginOrStartActivity() {
        if (mPersonalCenterBiz.isLogin() ) {

            return true;
        } else {
            startLogInSigInActivity();
            return false;
        }
    }

    /**
     * 启动登录注册界面
     */
    private void startLogInSigInActivity() {
        Intent i = new Intent(getActivity(), LogSignInActivity.class);
        startActivity(i);
    }






    @Override
    protected String getDeugTag() {
        return TAG;
    }


    @Override
    public void onFirstEnter() {
        super.onFirstEnter();
        mPersonalCenterBiz.onFirstEnter();
    }

    @Override
    public void onEnter() {
        super.onEnter();
        mPersonalCenterBiz.onEnter();
    }

    @Override
    public void onLeft() {
        super.onLeft();
    }

}
