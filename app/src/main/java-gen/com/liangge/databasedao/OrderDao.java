package com.liangge.databasedao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.liangge.databasedao.Order;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ORDER".
*/
public class OrderDao extends AbstractDao<Order, Integer> {

    public static final String TABLENAME = "ORDER";

    /**
     * Properties of entity Order.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ProductId = new Property(0, int.class, "productId", true, "PRODUCT_ID");
        public final static Property BuyCnt = new Property(1, int.class, "buyCnt", false, "BUY_CNT");
    };


    public OrderDao(DaoConfig config) {
        super(config);
    }
    
    public OrderDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ORDER\" (" + //
                "\"PRODUCT_ID\" INTEGER PRIMARY KEY NOT NULL ," + // 0: productId
                "\"BUY_CNT\" INTEGER NOT NULL );"); // 1: buyCnt
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ORDER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Order entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getProductId());
        stmt.bindLong(2, entity.getBuyCnt());
    }

    /** @inheritdoc */
    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.getInt(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Order readEntity(Cursor cursor, int offset) {
        Order entity = new Order( //
            cursor.getInt(offset + 0), // productId
            cursor.getInt(offset + 1) // buyCnt
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Order entity, int offset) {
        entity.setProductId(cursor.getInt(offset + 0));
        entity.setBuyCnt(cursor.getInt(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Integer updateKeyAfterInsert(Order entity, long rowId) {
        return entity.getProductId();
    }
    
    /** @inheritdoc */
    @Override
    public Integer getKey(Order entity) {
        if(entity != null) {
            return entity.getProductId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
