package com.example.liangge.indiana.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.ui.LogSignInActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends BaseFragment {

    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    private PersonalCenterBiz mPersonalCenterBiz;

    /** 用户头像 */
    private ImageView mImgViewUserPortain;

    /** 登录/注册/已登录 */
    private Button mBtnLogHint;

    /** 软件相关信息按钮 */
    private ImageButton mImgBtnInfo;

    //夺宝记录
    private Button mBtnIndianaRecord;

    //中奖记录
    private Button mBtnBingoRecord;

    //收货地址
    private Button mBtnTakeOverGoods;

    //账户明细
    private Button mBtnAccountDetail;

    //个人信息
    private Button mBtnPersonalInfo;

    //联系客服
    private Button mBtnContactCustomer;



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
        mBtnTakeOverGoods = (Button) view.findViewById(R.id.f_personal_takeovergoods_address);
        mBtnAccountDetail = (Button) view.findViewById(R.id.f_personal_account_detail);
        mBtnPersonalInfo = (Button) view.findViewById(R.id.f_personal_personinfo);
        mBtnContactCustomer = (Button) view.findViewById(R.id.f_personal_contact_customer_service);

        mBtnLogHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgeLoginOrStartActivity();

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
        mBtnTakeOverGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnTakeOverGoods();
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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initManager();
    }

    @Override
    public void onResume() {
        super.onResume();
        onThisFragment();
    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(getActivity());
    }

    @Override
    protected void registerUIBroadCast() {

    }

    @Override
    protected void unRegisterUIBroadCast() {

    }


    private class UIMsgRecevie extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String intentAction = intent.getAction();
                if (intentAction !=  null) {
                    String uiAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_ACTION);
                    handleUIMsg(uiAction);
                }
            }
        }

    }

    private void handleUIMsg(String uiAction) {
        if (uiAction.equals(UIMessageConts.PersonalCenterMessage.M_INIT_LOGIN_INFO)) {
            handleUIInitInfo();
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

        }
    }

    //中奖记录
    public void onBtnBingoRecord() {
        if (judgeLoginOrStartActivity()) {

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
