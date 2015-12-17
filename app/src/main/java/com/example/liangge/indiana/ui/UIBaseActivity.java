package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class UIBaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
