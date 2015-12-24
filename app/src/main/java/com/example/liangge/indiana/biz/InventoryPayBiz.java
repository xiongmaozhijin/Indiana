package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.UIMessageEntity;

import java.util.List;

/**
 * 订单支付类
 * Created by baoxing on 2015/12/24.
 */
public class InventoryPayBiz {


    public static Context mContext;

    public static InventoryPayBiz mInstance;

    private static ShoppingCartBiz mShoppingCartBiz;

    private static PersonalCenterBiz mPersonalCenterBiz;

    private static MessageManager mMessageManager;


    private InventoryPayBiz(Context context) {
        this.mContext = context;
        mShoppingCartBiz = ShoppingCartBiz.getInstance(context);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(context);
        mMessageManager = MessageManager.getInstance(context);
    }


    public static synchronized InventoryPayBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InventoryPayBiz(context);
        }

        return mInstance;
    }


    /**
     * 当进入到这个activity时
     */
    public void onResume() {
        notifyInitInventoryInfo();

    }

    /**
     * 初始化订单信息
     */
    private void notifyInitInventoryInfo() {
        UIMessageEntity item = new UIMessageEntity(UIMessageConts.InventoryPay.M_INIT_INVENTORY_INFO);
        mMessageManager.sendMessage(item);

    }


    /** 是否登录 */
    public boolean isLogin() {
        return mPersonalCenterBiz.isLogin();
    }

    /**
     * 一共需要支付多少金币
     * @return
     */
    public int getTotalPayGolds() {
        return mShoppingCartBiz.getTotalCost();
    }

    /**
     * 得到可读的订单信息
     * @return
     */
    public String getHummanReadableOrdersInfo() {
        StringBuilder sb = new StringBuilder();
        List<InventoryEntity> listInventoryData = mShoppingCartBiz.getListInventoryData();
        InventoryEntity item;
        for (int i=0, len=listInventoryData.size(); i<len; i++) {
            item = listInventoryData.get(i);
            sb.append("\n");
            sb.append(item.getTitleName());
            sb.append("\t\t");
            sb.append(item.getBuyCounts());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 获取账户金额
     * @return
     */
    public int getAccoutGold() {
        return mPersonalCenterBiz.getAccountGold();
    }

}
