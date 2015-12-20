package com.example.liangge.indiana.biz;

import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.ProductItemEntity;

/**
 * Created by baoxing on 2015/12/20.
 */
public class Bizdto {

    public static InventoryEntity changeToInventory(ProductItemEntity productItemEntity, int buyCnt) {
        InventoryEntity item = new InventoryEntity(productItemEntity.getProductId(), productItemEntity.getProductImgUrl(),
                                                        productItemEntity.getTilteDesc(),
                                                        productItemEntity.getNeedPeople(), productItemEntity.getLackPeople(), buyCnt);

        return item;
    }
}
