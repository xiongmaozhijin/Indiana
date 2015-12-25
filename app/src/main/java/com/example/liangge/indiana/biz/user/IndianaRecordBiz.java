package com.example.liangge.indiana.biz.user;

import android.content.Context;

import com.example.liangge.indiana.biz.BaseActivityBiz;
import com.example.liangge.indiana.biz.IndianaBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.comm.net.VolleyBiz;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 夺宝记录Biz
 * Created by baoxing on 2015/12/25.
 */
public class IndianaRecordBiz extends BaseActivityBiz {


    private static Context mContext;

    private static IndianaRecordBiz mInstance;

    private MessageManager mMessageManager;

    private VolleyBiz mVolleyBiz;


    private static class DataInfo {
        public static List<IndianaRecordEntity> mListData = new ArrayList<>();

    }

    private static class RequestInfo {

        public static boolean bIsLoadMore = false;

        public static int iStartPage = 1;

    }



    private IndianaRecordBiz(Context context) {
        this.mContext = context;
        initManager(context);
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
        mVolleyBiz = VolleyBiz.getInstance();

    }


    public static IndianaRecordBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new IndianaRecordBiz(context);

        }

        return mInstance;
    }



    public List<IndianaRecordEntity> getData() {
        return DataInfo.mListData;
    }



    private class SlaveLoadIndianaRecordInfThread extends Thread {

        private static final String REQUEST_TAG = "SlaveLoadIndianaRecordInfThread";


        @Override
        public void run() {
            super.run();

        }


        private String get


    }








    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

}
