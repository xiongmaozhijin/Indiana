package com.example.liangge.indiana.biz;

import android.content.Context;
import android.content.Intent;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.ui.HomeActivity;

/**
 * Created by baoxing on 2015/12/30.
 */
public class HomeBiz {


    private static final String TAG = HomeBiz.class.getSimpleName();

    private static HomeBiz mInstance;

    private static Context mContext;

    private MessageManager mMessageManager;


    private HomeBiz(Context context) {
        this.mContext = context;
        initManager();
        initRes();
    }

    private void initRes() {

    }

    private void initManager() {
        mMessageManager = MessageManager.getInstance(mContext);
    }




    private static class DataInfo {
        public static int iReplaceFlag = HomeActivity.I_TAG_FRAGMENT_INVALID;
    }





    public static HomeBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HomeBiz(context);
        }

        return mInstance;
    }

    public static HomeBiz getInstance() {
        return mInstance;
    }


    public Context getHomeActivity() {
        return mContext;
    }

    public int getReplaceFragmentFlag() {
        return DataInfo.iReplaceFlag;
    }

    /**
     * 切换Fragment
     * @param fragmentFlag
     */
    public void replaceFragment(int fragmentFlag) {
        LogUtils.i(TAG, "replaceFragment(). fragmentFlag=%d", fragmentFlag);

        DataInfo.iReplaceFlag = fragmentFlag;
        mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.HomeActivity.HOME_ACTIVITY_M_REPLACE_FLAGMENT));

    }


    /**
     * 跳转到主页的购物车模块
     * @param context
     */
    public void jumpToShoppingCartFragment(Context context) {
        LogUtils.i(TAG, "jumpToShoppingCartFragment()");
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        replaceFragment(HomeActivity.I_TAG_FRAGMENT_SHOPPING_CART);
    }











}
