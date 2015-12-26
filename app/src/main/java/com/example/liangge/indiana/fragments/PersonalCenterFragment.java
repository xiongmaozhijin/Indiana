package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
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
    private Button mBtnLogHint;


    private TextView mTxvUserInfo;

    /** 软件相关信息按钮 */
    private ImageButton mImgBtnInfo;

    //夺宝记录
    private Button mBtnIndianaRecord;

    //中奖记录
    private Button mBtnBingoRecord;

    //账户明细
    private Button mBtnAccountDetail;

    //个人信息
    private Button mBtnPersonalInfo;

    //联系客服
    private Button mBtnContactCustomer;


    private DisplayImageOptions mDisplayImageOptions;


    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_personal_center, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mImgViewUserPortain = (ImageView) view.findViewById(R.id.f_personal_user_portain);
        mBtnLogHint = (Button) view.findViewById(R.id.f_personal_log_signup_logout);
        mImgBtnInfo = (ImageButton) view.findViewById(R.id.f_personal_software_info);

        mBtnIndianaRecord = (Button) view.findViewById(R.id.f_personal_txvbtn_indiana_record);
        mBtnBingoRecord = (Button) view.findViewById(R.id.f_personal_txvbtn_bingo_record);
        mBtnAccountDetail = (Button) view.findViewById(R.id.f_personal_account_detail);
        mBtnPersonalInfo = (Button) view.findViewById(R.id.f_personal_personinfo);
        mBtnContactCustomer = (Button) view.findViewById(R.id.f_personal_contact_customer_service);
        mTxvUserInfo = (TextView) view.findViewById(R.id.f_personal_userinfo_1);

        mBtnLogHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLogInOrLogOut();

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
        mBtnAccountDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnAccountDetail();
            }
        });

        mBtnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnPersonalInfo();
            }
        });
        mBtnContactCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnContactCustomer();
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
        onThisFragment();
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
            handleUIInitInfo();

        } else if (uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_START)
                        || uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_FAILED)
                        || uiAction.equals(UIMessageConts.PersonalCenterMessage.M_LOGOUT_SUCCESS)){

            handleUILogOut(uiAction);
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

    /**
     * 处理进入到这个界面时的ui；是否登录分为两种
     */
    private void handleUIInitInfo() {
        if (mPersonalCenterBiz.isLogin()) {
            //TODO

        } else {

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



    private void onThisFragment() {
        mPersonalCenterBiz.initLogInInfo();
        if (mPersonalCenterBiz.isLogin()) {
            initLogInUIState();
        } else {
            initLogOutState();
        }

    }

    /**
     * 处理退出时的ui变化
     */
    private void initLogOutState() {
        LogUtils.i(TAG, "initLogOutState()");
        mBtnLogHint.setText(getResources().getString(R.string.f_personal_btn_log_signup));
        mImgViewUserPortain.setImageResource(R.drawable.main_banner_img_load_empty_uri);
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
        String strUserInfo = String.format(strUserInfoFormat, mPersonalCenterBiz.getUserInfo().getUserId(), mPersonalCenterBiz.getUserInfo().getBalance());
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
//        onThisFragment();     //onResume()

    }

    @Override
    public void onEnter() {
        super.onEnter();
        onThisFragment();

    }

    @Override
    public void onLeft() {
        super.onLeft();
    }

}
