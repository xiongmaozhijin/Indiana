package com.example.liangge.indiana.ui.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.ui.widget.BannerView;

import java.util.ArrayList;
import java.util.List;

public class BannerActivityTest extends AppCompatActivity {

    private static final String TAG = BannerActivityTest.class.getSimpleName();

    private BannerView mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        initWidget();

    }

    private void initWidget() {
        mBannerView = (BannerView) findViewById(R.id.test_banner_viewpager);
        mBannerView.setOnClickListener(new BannerView.OnClickListener() {
            @Override
            public void onClick(BannerInfo item) {
                LogUtils.e(TAG, "onClick item=%s", item.toString());
            }
        });

    }


    public void onBtnLoad(View view) {
        List<BannerInfo> lists = getDatas();
        mBannerView.setDatasAndNotify(lists);
    }

    private List<BannerInfo> getDatas() {
        List<BannerInfo> lists = new ArrayList<>();
        BannerInfo item1 = new BannerInfo();
        item1.setImgUrl("http://f.hiphotos.baidu.com/image/pic/item/3bf33a87e950352a230666de5743fbf2b3118b85.jpg");
        lists.add(item1);

        BannerInfo item2 = new BannerInfo();
        item2.setImgUrl("http://b.hiphotos.baidu.com/image/pic/item/0823dd54564e925838c205c89982d158ccbf4e26.jpg");
        lists.add(item2);


        return lists;
    }

}
