package com.example.liangge.indiana.biz;

/**
 * Created by baoxing on 2015/12/17.
 */
public abstract class BaseFragmentBiz {

    /**
     * 当这个界面实例化完成时
     */
    public abstract void onViewCreated();

    /**
     * 当用户第一次进入到这个界面时
     */
    public abstract void onFirstEnter();

    /**
     * 当用户再次进入到这个界面时
     */
    public abstract void onEnter();

    /**
     * 当用户离开这个界面时
     */
    public abstract void onLeave();




}
