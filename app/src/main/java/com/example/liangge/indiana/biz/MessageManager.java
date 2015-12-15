package com.example.liangge.indiana.biz;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.UIMessageEntity;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 消息管理，发送
 * Created by baoxing on 2015/12/15.
 */
public class MessageManager {

    private static final String TAG = MessageManager.class.getSimpleName();

    private static MessageManager mInstance;

    private Context mContext;

    private BlockingDeque<UIMessageEntity> mMessageQueue = new LinkedBlockingDeque<>();

    private CustomerWorker mCustomerWorker;

    private Handler mUIHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            UIMessageEntity msgEntity = (UIMessageEntity) msg.obj;
            LogUtils.e(TAG, "broadcast a msg. action=%s", msgEntity.getMessageAction());
            Intent i = new Intent();
            i.setAction(UIMessageConts.UI_MESSAGE_ACTION);
            i.putExtra(UIMessageConts.UI_MESSAGE_KEY, msgEntity.getMessageAction());
            mContext.sendBroadcast(i);
        }
    };

    private MessageManager(Context context) {
        this.mContext = context;
        startCustomer();
    }

    private void startCustomer() {
        mCustomerWorker = new CustomerWorker();
        mCustomerWorker.start();

    }


    public static synchronized MessageManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MessageManager(context);
        }

        return mInstance;
    }

    /**
     * 发送消息，可以工作在奴隶线程
     * @param messageEntity
     */
    public void sendMessage(UIMessageEntity messageEntity) {
        try {
            mMessageQueue.put(messageEntity);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class CustomerWorker extends Thread {

        private boolean bIsCustomerActive = true;

        public void stopWork() {
            this.bIsCustomerActive = false;
        }

        @Override
        public void run() {
            UIMessageEntity msgEntity;

            while (bIsCustomerActive) {
                try {
                    msgEntity = mMessageQueue.take();
                    Message msg = Message.obtain();
                    msg.obj = msgEntity;
                    mUIHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void onDestroy() {
        if (this.mCustomerWorker != null) {
            this.mCustomerWorker.stopWork();
        }
    }


}