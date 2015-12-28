package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.LogSignInBiz;

/**
 * 仅可以用于HomeActivity
 */
public class UIBaseActivity extends FragmentActivity {

    private PersonalCenterBiz mPersonalCenterBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        initManager();
    }

    //解决 getActivity() 返回 NULL 的问题
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);

    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearRes();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearRes();

        System.exit(0);
    }

    private void clearRes() {
        mPersonalCenterBiz.logOut();
    }

}
