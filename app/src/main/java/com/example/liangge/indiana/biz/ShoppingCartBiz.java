package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.fragments.ShoppingCartFragment;

/**
 * Created by baoxing on 2015/12/17.
 */
public class ShoppingCartBiz extends BaseFragmentBiz{

    private static ShoppingCartBiz mInstance;

    private static Context mContext;


    private ShoppingCartBiz(Context context) {
        this.mContext = context;

    }


    public synchronized static ShoppingCartBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ShoppingCartBiz(context);

        }

        return mInstance;
    }


    @Override
    public void onViewCreated() {


    }

    //1.查询是否有清单
    @Override
    public void onFirstEnter() {

    }

    //1.查询是否有清单
    @Override
    public void onEnter() {

    }

    @Override
    public void onLeave() {

    }
}
