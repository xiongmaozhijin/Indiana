package com.example.liangge.indiana.biz;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.model.PayRequestEntity;
import com.example.liangge.indiana.model.PayRequestItemEntitiy;
import com.example.liangge.indiana.model.ResponseUserInfoEntitiy;
import com.example.liangge.indiana.model.user.ResponseLogEntity;
import com.example.liangge.indiana.model.user.UserInfoEntity;
import com.liangge.databasedao.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/20.
 */
public class Bizdto {

    private static final String TAG = Bizdto.class.getSimpleName();

    public static InventoryEntity changeToInventory(ActivityProductItemEntity activityProductItemEntity, int buyCnt) {
        InventoryEntity item = new InventoryEntity(activityProductItemEntity.getActivityId(), activityProductItemEntity.getProductImgUrl(),
                                                        activityProductItemEntity.getName(),
                                                        activityProductItemEntity.getTitleDesc(),
                                                        activityProductItemEntity.getNeedPeople(), activityProductItemEntity.getLackPeople(), buyCnt, activityProductItemEntity.getMinimum_share());

        return item;
    }

    public static List<InventoryEntity> changeToInventory(List<ActivityProductItemEntity> srcList, List<Order> orderList) {
        LogUtils.e(TAG, "changeToInventory().srcList.size=%d, orderList.size=%d", srcList.size(), orderList.size());
        LogUtils.w(TAG, "srclist=%s, orderlist=%s", srcList.toString(), orderList.toString());

        List<InventoryEntity> dstList = new ArrayList<>();
        for (int i=0, lenI=srcList.size(); i<lenI; i++) {
            dstList.add(changeToInventory(srcList.get(i), 1));
        }

        InventoryEntity inventoryEntityItem;
        Order orderItem;
        for (int i=0, lenI=dstList.size(); i<lenI; i++) {
            inventoryEntityItem = dstList.get(i);

            for (int j=0, lenJ=orderList.size(); j<lenJ; j++) {
                orderItem = orderList.get(j);

                if (inventoryEntityItem.getActivityID() == orderItem.getProductId()) {
                    inventoryEntityItem.setBuyCounts(orderItem.getBuyCnt());
                    break;
                }

            }   //end for

        }   //end for

        return dstList;
    }


    public static List<BannerInfo> changeToBannerInfo(String[] imgUrls) {
        List<BannerInfo> list = new ArrayList<>();
        if (imgUrls != null) {
            for (int i=0, len=imgUrls.length; i<len; i++) {
                BannerInfo item = new BannerInfo(imgUrls[i]);
                list.add(item);
            }
        }

        return list;
    }


    public static UserInfoEntity changeToUserInfoEntity(UserInfoEntity userInfo, ResponseLogEntity item) {

        userInfo.setId(item.getId());
        userInfo.setBalance(item.getBalance());
        userInfo.setPhoto(item.getPhoto());
        userInfo.setToken(item.getToken());
        userInfo.setNickname(item.getNickname());

        return userInfo;
    }


    public static PayRequestEntity changeToPayRequestEntity(long userId, String token, List<InventoryEntity> listInventory) {
        LogUtils.w(TAG, "userId=%d, token=%s, listInventory.size=%d", userId, token, listInventory.size());
        PayRequestEntity pay = new PayRequestEntity();
        List listPayList = new ArrayList();

        InventoryEntity inventoryEntity;
        for (int i=0, len=listInventory.size(); i<len; i++) {
            inventoryEntity = listInventory.get(i);
            PayRequestItemEntitiy payRequestItemEntitiy = new PayRequestItemEntitiy(inventoryEntity.getActivityID(), inventoryEntity.getBuyCounts());
            listPayList.add(payRequestItemEntitiy);
        }

        pay.setId(userId);
        pay.setToken(token);
        pay.setPayList(listPayList);

        return pay;
    }


    public static UserInfoEntity changeToUserInfoEntity(UserInfoEntity userItem, ResponseUserInfoEntitiy resItem) {
        LogUtils.i(TAG, "uesrItem=%s, resItem=%s", userItem.toString(), resItem.getData().toString());
        userItem.setPhoto(resItem.getData().getPhoto());
        userItem.setBalance(resItem.getData().getBalance());
        userItem.setNickname(resItem.getData().getNickname());
        userItem.setAddress(resItem.getData().getAddress());
        userItem.setPhone_number(resItem.getData().getPhone_number());

        LogUtils.i(TAG, "uesrItem=%s, resItem=%s", userItem.toString(), resItem.getData().toString());

        return userItem;
    }



}
