package com.bluelable.bluelog.wrapper;

/**
 * Created by xinspace on 01/13/16.
 * 获取手机信息，包括用户ID，产品版本号
 */
public interface DeviceWrapper {

    /**
     * @author xinspace
     * created at 01/13/16 14:35
     * 获取用户ID
     * @return 用户ID，建议用IMEI
     */
    String getUserId();

    /**
     * @author xinspace
     * created at 01/13/16 14:37
     * 获取手机型号
     * @return 手机型号,使用Build.MODEL获取
     */
    String getPhoneModel();

    /**
     * @author xinspace
     * created at 01/13/16 14:37
     * 获取产品版本号
     * @return 产品版本号
     */
    int getProductVersion();
}
