package com.example.liangge.indiana.ui.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.liangge.indiana.R;

public class SurfaceViewTestActivity extends Activity {

    TextView mTxv1;

    TextView mTxv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view_test);
        initView();
    }

    private void initView() {
        mTxv1 = (TextView) findViewById(R.id.txv1);
        mTxv2 = (TextView) findViewById(R.id.txv2);

        String txv1 = "<p style=\"color:red\"> dfasfwe </p>";
        String txv2 = "<html><p style=\"color:red\"> dfasfwe </p></html>";
        String txv3 = "<font color=\"red\">abc</font>";

        mTxv1.setText(Html.fromHtml(txv1));
        mTxv2.setText(Html.fromHtml(txv3));
    }


}
