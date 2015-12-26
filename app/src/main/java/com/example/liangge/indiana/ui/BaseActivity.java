package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.liangge.indiana.R;

/**
 * @deprecated
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerUIReceive();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterUIReceive();

    }



    protected abstract void registerUIReceive();

    protected abstract void unRegisterUIReceive();



}
