package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.biz.daoimpl.DBManager;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.ProductItemEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.liangge.databasedao.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/17.
 */
public class ShoppingCartBiz extends BaseFragmentBiz{

    private static final String TAG = ShoppingCartBiz.class.getSimpleName();

    private static ShoppingCartBiz mInstance;

    private static Context mContext;

    /** 清单列表数据 */
    private volatile static List<InventoryEntity> mListInventorys = new ArrayList<>();

    /** 消息管理 */
    private MessageManager mMessageManager;

    private static DBManager mDBManager;

    /** 当前购物车数量 */
    private int mICurCntShoppingItem = 0;

    private ShoppingCartBiz(Context context) {
        this.mContext = context;
        initRes();

    }

    private void initRes() {
        mMessageManager = MessageManager.getInstance(mContext);
        mDBManager = DBManager.getInstance(mContext);
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

    /**
     * 把商品添加到购物车
     * 1.写数据库
     * 2.写存在的内存列表
     * 3.更新ui
     * @param productItemEntity
     */
    public void addProductToShoppingCart(ProductItemEntity productItemEntity) {
        LogUtils.w(TAG, "addProductToShoppingCart()");
        new AsyncAddProductThread(productItemEntity).start();
    }

    /**
     * 异步增加商品 <br/>
     *
     * 把商品添加到购物车
     * 1.写数据库
     * 2.写存在的内存列表
     * 3.更新ui
     *
     */
    private class AsyncAddProductThread extends Thread {

        private ProductItemEntity mAddProductItemEntity;

        public AsyncAddProductThread(ProductItemEntity productItemEntity) {
            this.mAddProductItemEntity = productItemEntity;
        }

        @Override
        public void run() {
            //1.
            writeOrderToDatabase();
            //2.
            writeOrderToList();

            //3.
             notifyCntShoppingItem();

        }

        private void notifyCntShoppingItem() {
            mICurCntShoppingItem = getCurShoppingItemCnt();

            LogUtils.w(TAG, "notifyCntShoppingItem().mICurCnt=%d", mICurCntShoppingItem);

            if (mICurCntShoppingItem != 0) {
                UIMessageEntity msgInfo = new UIMessageEntity(UIMessageConts.ShoppingCartMessage.M_UPDATE_SHOPPINGCART_ITEM_COUNTS);
                mMessageManager.sendMessage(msgInfo);
            }

        }

        private void writeOrderToList() {
            boolean bIsContain = false;
            InventoryEntity inventoryEntityItem;
            for (int i=0, len=mListInventorys.size(); i<len; i++) {
                inventoryEntityItem = mListInventorys.get(i);
                if (inventoryEntityItem.getProductId() == mAddProductItemEntity.getProductId()) {
                    int iCurBuyCnt = inventoryEntityItem.getBuyCounts();
                    inventoryEntityItem.setBuyCounts(iCurBuyCnt+1);
                    bIsContain = true;
                    break;
                }
            }
            if (!bIsContain) {
                mListInventorys.add(Bizdto.changeToInventory(mAddProductItemEntity, 1));
            }
        }

        private void writeOrderToDatabase() {
            Order orderDb = mDBManager.loadOrderEntity(mAddProductItemEntity.getProductId());
            if (orderDb == null) {
                orderDb = new Order(mAddProductItemEntity.getProductId(), 1);
                mDBManager.addOrder(orderDb);
            } else {
                int iCurBuyCnt = orderDb.getBuyCnt();
                orderDb.setBuyCnt(iCurBuyCnt + 1);
                mDBManager.updateOrder(orderDb);
            }
        }

    }

    /**
     * 当前购物车数量
     * @return
     */
    private int getCurShoppingItemCnt() {
        List<Order> allOrderList = mDBManager.selectAllOrder();
        int iCnt = 0;
        if (allOrderList != null) {
            Order item;
            for (int i=0, len=allOrderList.size(); i<len; i++) {
                item = allOrderList.get(i);
                iCnt += item.getBuyCnt();
            }

        }

        return iCnt;
    }


    /**
     * 进入到这个界面时
     * 0.把内存的list通知ui更新
     * 1.从数据库中取出所有订单
     * 2.和内存中的订单进行差集比较得到A
     * 4.把A请求网络查询
     * 5.把结果添加到内容列表
     * 6.把内存列表list通知ui更新/通知查询失败
     */
    private void onThisFrament() {
        notifyOrderDatas();

        if (!bIsWorking) {
            bIsWorking = true;
            new TestRequest().start();

        }


    }


    /**
     * 消息通知ui加载重置列表清单/或空购物车
     */
    private void notifyOrderDatas() {
        UIMessageEntity info = new UIMessageEntity();

        if (this.mListInventorys.size() > 0) {
            info.setMessageAction(UIMessageConts.ShoppingCartMessage.M_RESET_UPDATE_LISTS);

        } else {
            info.setMessageAction(UIMessageConts.ShoppingCartMessage.M_EMPTY_ORDERS);

        }
        mMessageManager.sendMessage(info);

        //发送消息，通知查询成功，停止icon refresh loadding
        UIMessageEntity msgInfo = new UIMessageEntity(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_SUCCESS);
        mMessageManager.sendMessage(msgInfo);

    }

    /** 正在网络请求操作 */
    private volatile boolean bIsWorking = false;

    private class TestRequest extends Thread {
        @Override
        public void run() {
            LogUtils.w(TAG, "run..");
            UIMessageEntity startInfo = new UIMessageEntity(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_STARTS);
            mMessageManager.sendMessage(startInfo);

            List<Order> allDatas = mDBManager.selectAllOrder();
            List<Order> queryDatas = getDiffDatas(mListInventorys, allDatas);
            //...
            LogUtils.w(TAG, "网络请求差集数据..info=%s. size=%d", queryDatas.toString(), queryDatas.size());

            //消息回调 TODO

            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            UIMessageEntity resultInfo = new UIMessageEntity(UIMessageConts.ShoppingCartMessage.M_QUERY_ORDERS_SUCCESS);
            mMessageManager.sendMessage(resultInfo);
            bIsWorking = false;
        }

    }

    /**
     * 返回在listOrder中存在，在listInventory中不存在的数据；即差集
     * @param listInventory
     * @param listOrder
     * @return
     */
    private List<Order> getDiffDatas(List<InventoryEntity> listInventory, List<Order> listOrder) {
        List<Order> diffLists = new ArrayList<>();

        Order orderItem;
        InventoryEntity inventoryItem;
        for (int i=0, leni=listOrder.size(); i<leni; i++) {
            orderItem = listOrder.get(i);

            boolean bIsContain = false;
            for (int j=0, lenj=listInventory.size(); j<lenj; j++) {
                inventoryItem = listInventory.get(j);
                if (orderItem.getProductId() == inventoryItem.getProductId() ) {
                    bIsContain = true;
                }

            }

            if (!bIsContain) {
                diffLists.add(orderItem);
            }

        }


        return diffLists;
    }



    //1.查询是否有清单
    @Override
    public void onFirstEnter() {
        onThisFrament();
    }

    //1.查询是否有清单
    @Override
    public void onEnter() {
        onThisFrament();
    }

    @Override
    public void onLeave() {

    }


}
