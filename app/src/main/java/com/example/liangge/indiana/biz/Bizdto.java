package com.example.liangge.indiana.biz;

import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.ActivityProductItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/20.
 */
public class Bizdto {

    public static InventoryEntity changeToInventory(ActivityProductItemEntity activityProductItemEntity, int buyCnt) {
        InventoryEntity item = new InventoryEntity(activityProductItemEntity.getActivityId(), activityProductItemEntity.getProductImgUrl(),
                                                        activityProductItemEntity.getName(),
                                                        activityProductItemEntity.getTilteDesc(),
                                                        activityProductItemEntity.getNeedPeople(), activityProductItemEntity.getLackPeople(), buyCnt);

        return item;
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

}
