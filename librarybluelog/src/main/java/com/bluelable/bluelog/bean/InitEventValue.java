package com.bluelable.bluelog.bean;

/**
 * Created by xinspace on 11/12/15.
 * 初始化Log模块时需要收集的基本手机信息
 */
public class InitEventValue {

    private static final String TAG = InitEventValue.class.getSimpleName();//debug标签

    /**
     * @author xinspace
     * created at 11/18/15 11:46
     * 数据字段
     */
    private String userId;//用户ID
    private int channelId;//渠道号
    private int productId;//产品ID

    /**
     * @author xinspace
     * created at 11/18/15 11:46
     * 构造函数，填充默认值
     */
    public InitEventValue() {
    }

    /**
     * @author xinspace
     * created at 11/18/15 11:46
     * getters and setters
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
