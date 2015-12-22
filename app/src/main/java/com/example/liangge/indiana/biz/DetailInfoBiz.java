package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.model.BannerInfo;

import java.util.List;

/**
 * 商品详细Biz
 * Created by baoxing on 2015/12/22.
 */
public class DetailInfoBiz {


    private static DetailInfoBiz mInstance;

    private static Context mContext;

    /**
     * 请求字段
     */
    private static class RequestField {
        /** 活动期次 */
        public static long lActivityId;


    }



    /**
     * 数据信息
     */
    private static class DataInfo {

        /** 奖品图片轮播数据 */
        public List<BannerInfo> listBannerInfo;

        /** 进度提示代码 */
        public int processHintCode;

        /** 进度提示（进行中/揭晓中/已揭晓）*/
        public String processHint;

        /** 活动产品标题描述 */
        public String activityProductTitle;

        /** 总需人次 */
        public int needPeopleTimes;

        /** 参与购买人次 */
        public int buyJoinTimes;

        /** 剩余人次 */
        public int lackTimes;

        /** 获奖用户 */
        public String luckyDog;

        /** 用户地址 */
        public String userAddress;

        /** 获奖用户参与次数*/
        public int luckDogBuyCnt;

        /** 开奖时间 */
        public String sRunLottoryTime;

        /** 幸运号码 */
        public String luckyNumber;


    }




    private DetailInfoBiz(Context context) {
        this.mContext = context;
    }


    public static DetailInfoBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DetailInfoBiz(context);
        }

        return mInstance;
    }











    public void onCreate() {

    }

    public void onResume() {

    }


    public void onStop() {

    }


    public void onDestroy() {

    }


}
