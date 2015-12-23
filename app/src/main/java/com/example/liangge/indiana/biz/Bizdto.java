package com.example.liangge.indiana.biz;

import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.ActivityProductItemEntity;

/**
 * Created by baoxing on 2015/12/20.
 */
public class Bizdto {

    public static InventoryEntity changeToInventory(ActivityProductItemEntity activityProductItemEntity, int buyCnt) {
        InventoryEntity item = new InventoryEntity(activityProductItemEntity.getActivityId(), activityProductItemEntity.getProductImgUrl(),
                                                        activityProductItemEntity.getTilteDesc(),
                                                        activityProductItemEntity.getNeedPeople(), activityProductItemEntity.getLackPeople(), buyCnt);

        return item;
    }
}
