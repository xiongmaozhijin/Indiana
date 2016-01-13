package com.bluelable.bluelog.wrapper;

/**
 * Created by xinspace on 01/13/16.
 * 后台服务封装类
 */
public interface ServiceWrapper {

    /**
     * @author xinspace
     * created at 01/13/16 15:10
     * 启动重复运行的服务
     */
    void startRepeatTask();

    /**
     * @author xinspace
     * created at 01/13/16 15:11
     * 取消重复运行的服务
     */
    void cancelRepeatTask();

    /**
     * @author xinspace
     * created at 01/13/16 15:11
     * 启动定时运行的服务
     */
    void startAtTimeTask();

    /**
     * @author xinspace
     * created at 01/13/16 15:12
     * 取消定时运行的服务
     */
    void cancelAtTimeTask();
}
