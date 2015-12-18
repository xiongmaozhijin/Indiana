package com.example.liangge.indiana.biz.daoimpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.liangge.indiana.comm.LogUtils;
import com.liangge.databasedao.DaoMaster;
import com.liangge.databasedao.DaoSession;
import com.liangge.databasedao.Order;
import com.liangge.databasedao.OrderDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by baoxing on 2015/12/18.
 */
public class DBManager {

    private static final String DATABASE_NAME = "indiana_db";

    private static final String TAG = DBManager.class.getSimpleName();

    private static DBManager mInstance;

    private Context context;

    private DaoSession mDaoSession;

    private SQLiteDatabase mSqLiteDatabase;

    private DBManager(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), DATABASE_NAME, null);
        mSqLiteDatabase = devOpenHelper.getWritableDatabase();
        mDaoSession = new DaoMaster(mSqLiteDatabase).newSession();
    }

    public static synchronized DBManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBManager(context);
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }

        return mInstance;
    }


    /**
     * 增加一条订单
     * @param productId 产品id
     * @param buyCnt    购买数量
     */
    public void addOrder(int productId, int buyCnt) {
        LogUtils.w(TAG, "addOrder().productId=%d, buyCnt=%d", productId, buyCnt);
        Order order = new Order(productId, buyCnt);
        OrderDao orderDao = mDaoSession.getOrderDao();
        orderDao.insert(order);
    }

    /**
     * 删除一条订单
     * @param productId
     */
    public void deleteOrder(Integer productId) {
        LogUtils.w(TAG, "deleteOrder(). productId=%d",productId);
        OrderDao orderDao = mDaoSession.getOrderDao();
        orderDao.deleteByKey(productId);
    }

    /**
     * 更新一条订单记录
     * @param productId
     * @param buyCnt
     */
    public void updateOrder(int productId, int buyCnt) {
        LogUtils.w(TAG, "updateOrder().productId=%d, buyCnt=%d", productId, buyCnt);
        OrderDao orderDao = mDaoSession.getOrderDao();
        orderDao.insertOrReplace(new Order(productId, buyCnt));
    }

    public List<Order> selectAllOrder() {
        LogUtils.w(TAG, "selectAllOrder()");
        OrderDao orderDao = mDaoSession.getOrderDao();
        return orderDao.loadAll();
    }











}
