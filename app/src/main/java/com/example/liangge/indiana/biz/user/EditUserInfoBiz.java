package com.example.liangge.indiana.biz.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.BaseActivityBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.FileOperateUtils;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.user.UserInfoEntity;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 编辑用户Biz
 * Created by baoxing on 2016/1/9.
 */
public class EditUserInfoBiz extends BaseActivityBiz{

    private static final String TAG = EditUserInfoBiz.class.getSimpleName();

    private static EditUserInfoBiz mInstance;

    private static Context mContext;

    private MessageManager mMessageManager;


    private SlaveSaveUserInfo mSlaveSaveUserInfo;

    private PersonalCenterBiz mPersonalCenterBiz;

    private EditUserInfoBiz(Context context) {
        this.mContext = context;
        initRes(context);
        initManager(context);
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(context);
    }

    private void initRes(Context context) {
        mSlaveSaveUserInfo = new SlaveSaveUserInfo();
    }


    public static EditUserInfoBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EditUserInfoBiz(context);
        }

        return mInstance;
    }


    private static class RequestInfo {
        public static ByteArrayOutputStream photo;
        public static String nickname;
        public static String phoneNumber;
        public static List<UserInfoEntity.UserAddress> address;
    }

    /**
     * 设置更新请求信息
     * @param photo
     * @param nickname
     * @param phoneNumber
     * @param address
     */
    public void setUpdateRequest(ByteArrayOutputStream photo, String nickname, String phoneNumber, List<UserInfoEntity.UserAddress> address) {
        RequestInfo.photo = photo;
        RequestInfo.nickname = nickname;
        RequestInfo.phoneNumber = phoneNumber;
        RequestInfo.address = address;
    }


    /**
     * 得到临时保存用户头像的文件路径
     * @return
     */
    public File getUserPortraitTempFile(Context context) {
        final String portraitName = "user_portrait_img" + ".jpg";
        File file = new File(context.getExternalCacheDir(), portraitName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.e(TAG, "IOException");

            }
        }

        LogUtils.w(TAG, "getUserPortraitTempFile(). file=%s", file==null?"null":file.getAbsolutePath());
       return file;
    }


    /**
     * 删除用户头像文件
     * @param file
     */
    public void deleteUserPortraitTempFile(File file) {
      if (file != null && (file.exists())) {
          file.delete();
      }
    }

    /**
     * 保存用户头像在本地
     * @param outputStream
     * @param context
     */
    public void saveUserPortrait(ByteArrayOutputStream outputStream, Context context) {
        File file = getUserPortraitTempFile(context);
        deleteUserPortraitTempFile(file);
        File file1 = getUserPortraitTempFile(context);

        FileOperateUtils.byte2File(outputStream.toByteArray(), file1);
        LogUtils.i(TAG, "saveUserPortrait(). file1=%s", file1.getAbsolutePath());
    }


    public void copyFileFromUri(Context context, Uri uri, File dstFile) {
        FileOperateUtils.copyFile(context, uri, dstFile);
    }


    /**
     * 保存用户信息到服务器
     */
    public void onBtnSaveUserInfo() {
        LogUtils.i(TAG, "onBtnSaveUserInfo()");
        mSlaveSaveUserInfo.cancelAll();
        mSlaveSaveUserInfo = new SlaveSaveUserInfo();
        mSlaveSaveUserInfo.start();

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

    }


    /**
     * 保存用户信息奴隶线程
     */
    private class SlaveSaveUserInfo extends NetRequestThread {

        private static final String R_TAG = "SlaveSaveUserInfo";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            String hint = mContext.getResources().getString(R.string.userinfo_update_ing);
            LogUtils.w(TAG, hint);
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            LogUtils.w(TAG, "更新成功");
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            String hint = mContext.getResources().getString(R.string.userinfo_update_net_error);
            LogUtils.w(TAG, hint);
        }

        @Override
        protected String getJsonBody() {
            UserInfoEntity user = mPersonalCenterBiz.getUserInfo();
            String photoBase64 = Base64.encodeToString(RequestInfo.photo.toByteArray(), Base64.DEFAULT);
            UserInfoEntity updateUser = new UserInfoEntity(user.getId(), RequestInfo.nickname, user.getToken(), -1, photoBase64, RequestInfo.phoneNumber, RequestInfo.address);
            Gson gson = new Gson();
            String json = gson.toJson(updateUser);

            LogUtils.i(TAG, "josnBody=%s", json);
            return json;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(R_TAG, "onResponse().s=%s", s);
            //TODO
            mPersonalCenterBiz.updateUserInfo();

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "volleyError=%s", volleyError);

        }

        @Override
        protected String getRequestTag() {
            return R_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_UPDATE_USER_INFO;
        }
    }







}
