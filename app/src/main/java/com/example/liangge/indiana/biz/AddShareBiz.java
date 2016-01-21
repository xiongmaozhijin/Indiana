package com.example.liangge.indiana.biz;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.view.TextureView;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.FileOperateUtils;
import com.example.liangge.indiana.comm.ImageUtils;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetFormPostRequestThread;
import com.example.liangge.indiana.comm.net.NetFormPostRequestThread2;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.CommResponseStatueEntity;
import com.example.liangge.indiana.model.PostShareOrderEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.example.liangge.indiana.ui.AddShareActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 晒单Biz
 * Created by baoxing on 2016/1/19.
 */
public class AddShareBiz extends BaseActivityBiz{

    private static final String TAG = AddShareBiz.class.getSimpleName();

    private static Context mContext;

    private static Context mApplicationContext;

    private static AddShareBiz mInstance;

    private static PersonalCenterBiz mPersonalCenterBiz;

    private static SlaveUpdateShareOrderThread mSlaveUpdateShareOrderThread;

    private MessageManager mMessageManager;

    private static String mCacheImgPathDir;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String toastHint = (String) msg.obj;
            LogUtils.toastShortMsg(mApplicationContext, toastHint);
        }
    };

    private AddShareBiz(Context context) {
        mContext = context;
        mApplicationContext = context.getApplicationContext();
        initManager(context);
        initRes();
    }

    private void initRes() {
        mCacheImgPathDir = mContext.getCacheDir().getAbsolutePath();
        mSlaveUpdateShareOrderThread = new SlaveUpdateShareOrderThread(new HashMap<String, String>(), new ArrayList<String>(), mCacheImgPathDir);
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context.getApplicationContext());
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(context.getApplicationContext());
    }


    public static AddShareBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AddShareBiz(context);
        }

        return mInstance;
    }


    private static class DataInfo {
        public static BingoRecordEntity mBingoRecordEntity = new BingoRecordEntity();
    }

    private static class RequestInfo {
        public static List<String> mImgPath = new ArrayList<>();
        public static PostShareOrderEntity mPostShareOrderEntity = new PostShareOrderEntity();
    }


    public BingoRecordEntity getBingoItem() {
        return DataInfo.mBingoRecordEntity;
    }

    public void setRequestInfo(String title, String content, List<String> imgPaths) {
        RequestInfo.mImgPath = imgPaths;
        RequestInfo.mPostShareOrderEntity.setTitle(title);
        RequestInfo.mPostShareOrderEntity.setContent(content);
        RequestInfo.mPostShareOrderEntity.setActivityID(DataInfo.mBingoRecordEntity.getActivityId());
        RequestInfo.mPostShareOrderEntity.setUserID(mPersonalCenterBiz.getUserInfo().getId());
        RequestInfo.mPostShareOrderEntity.setToken(mPersonalCenterBiz.getUserInfo().getToken());
    }

    /**
     * 提交晒单
     */
    public void onBtnSubmit() {
        LogUtils.i(TAG, "onBtnSubmit()");
        mSlaveUpdateShareOrderThread.cancelAll();
        mSlaveUpdateShareOrderThread = new SlaveUpdateShareOrderThread(RequestInfo.mPostShareOrderEntity.getPostMapEntity(), RequestInfo.mImgPath, mCacheImgPathDir);
        mSlaveUpdateShareOrderThread.start();
    }


    public void startActivity(Context context, BingoRecordEntity item) {
        LogUtils.i(TAG, "startActivity()");
        DataInfo.mBingoRecordEntity = item;
        Intent intent = new Intent(context, AddShareActivity.class);
        context.startActivity(intent);
    }


    public boolean isWorking() {
        boolean r = false;
        if (mSlaveUpdateShareOrderThread != null) {
            if (mSlaveUpdateShareOrderThread.isWorking()) {
                r = true;
            }
        }

        return r;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        mContext = null;
    }


    /**
     * 上传晒单信息
     */
    private class SlaveUpdateShareOrderThread extends NetFormPostRequestThread2 {

        private static final String R_TAG = "SlaveUpdateShareOrderThread";

        private String mCachePathDir;

        public SlaveUpdateShareOrderThread(Map<String, String> attrs, List<String> filePath, String cachePathDir) {
            super(attrs, filePath);
            mCachePathDir = cachePathDir;
        }

        @Override
        protected void preDoDump() {
            super.preDoDump();
            List<String> filePath = getFilePath();
            List<String> compressImgPath = new ArrayList<>();
            for (int i=0, len=filePath.size(); i<len; i++) {
                String itemPath1 = filePath.get(i);
                File file = new File(mCachePathDir, "shareImg"+i+".jpg");
                compressImgPath.add(ImageUtils.getCompressImageFilePath(itemPath1, file.getAbsolutePath()));
            }
            setImgPaths(compressImgPath);
        }

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.Comm_Activity.COMM_NET_START));
            String hint = mApplicationContext.getResources().getString(R.string.share_post);
            Message msg = Message.obtain();
            msg.obj = hint;
            mHandler.sendMessage(msg);
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.Comm_Activity.COMM_NET_SUCCESS));
//            String hint = mApplicationContext.getResources().getString(R.string.share_success);
//            Message msg = Message.obtain();
//            msg.obj = hint;
//            mHandler.sendMessage(msg);
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.Comm_Activity.COMM_NET_FAILED));
            String hint = mApplicationContext.getResources().getString(R.string.share_fail);
            Message msg = Message.obtain();
            msg.obj = hint;
            mHandler.sendMessage(msg);
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(R_TAG, "onResponse=%s", s);
            Gson gson = new Gson();
            CommResponseStatueEntity responseItem = gson.fromJson(s, CommResponseStatueEntity.class);
            Message msg = Message.obtain();
            msg.obj = responseItem.getMsg();
            mHandler.sendMessage(msg);
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(R_TAG, "volleyError=%s", volleyError.getLocalizedMessage() + "\n" + volleyError.getMessage());
        }

        @Override
        protected String getRequestTag() {
            return R_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_ADD_SHARE_ORDERS;
        }

        protected void setCachePathDir(String cachePathDir) {
            mCachePathDir = cachePathDir;
        }
    }














}
