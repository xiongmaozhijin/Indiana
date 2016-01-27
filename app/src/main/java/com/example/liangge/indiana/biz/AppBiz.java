package com.example.liangge.indiana.biz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.SharedPrefUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.inner.AppVersionInfo;
import com.google.gson.Gson;

import java.io.File;

/**
 * Created by baoxing on 2016/1/18.
 */
public class AppBiz {

    private static final String TAG = AppBiz.class.getSimpleName();

    private static Context mContext;

    private static AppBiz mInstance;

    private static MessageManager mMessageManager;

    private static SlaveCheckAppUpdateThread mSlaveCheckAppUpdateThread;

    private AppBiz(Context context) {
        mContext = context;
        initManager();
        initRes();
    }

    private void initRes() {
        mSlaveCheckAppUpdateThread = new SlaveCheckAppUpdateThread();
    }

    private void initManager() {
        mMessageManager = MessageManager.getInstance(mContext);
    }


    public static AppBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppBiz(context);
        }
        return mInstance;
    }



    private static class DataInfo {
        public static AppVersionInfo appVersionInfo = new AppVersionInfo();

    }

    /**
     * 返回服务端的版本信息
     * @return
     */
    public AppVersionInfo getVersionInfo() {
        return DataInfo.appVersionInfo;
    }

    /**
     * 检测APP更新
      */
    public void checkAppUpdate() {
        if (!mSlaveCheckAppUpdateThread.isWorking()) {
            mSlaveCheckAppUpdateThread = new SlaveCheckAppUpdateThread();
            mSlaveCheckAppUpdateThread.start();
        }

    }

    /**
     * 是否要更新
     * @return
     */
    public boolean isShouldUpdate() {
        return DataInfo.appVersionInfo.getVersion_code() > getCurVersionCode(mContext);
    }

    public Dialog showUpdateDialog(Context context, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {
//        LogUtils.e(TAG, "showUpdateDialog(). content=%s", getVersionInfo().getUpdate_msg());
        final String strUpdateYes = context.getResources().getString(R.string.update_yes);
        final String strUpdateNo = context.getResources().getString(R.string.update_no);
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(Html.fromHtml(getVersionInfo().getVersion_name()))
                .setMessage(Html.fromHtml(getVersionInfo().getUpdate_msg()))
                .setPositiveButton(strUpdateYes, yesListener)
                .setNegativeButton(strUpdateNo, noListener)
                .setCancelable(false)
                .create();

        alertDialog.show();

        return alertDialog;
    }

    /**
     * 下载夺宝App
     */
    public void downloadApp() {
        LogUtils.i(TAG, "downloadApp()");

        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        String apkUrl = getVersionInfo().getApp_url();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl) );
        request.setMimeType("application/vnd.android.package-archive");
        request.setTitle(mContext.getResources().getString(R.string.download_title));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(getDownloadDirs(), mContext.getResources().getString(R.string.download_apk_name));

        long downloadId = downloadManager.enqueue(request);
        SharedPrefUtils.save(Constant.SharedPerfer.KEY_APK_DOWNLOAD_ID, downloadId);
    }


    private String getDownloadDirs() {
        File download = Environment.getExternalStoragePublicDirectory("download");
        if (!download.exists()) {
            download.mkdirs();
        }
        return "download";
    }


    /** 版本名 */
    public static String getCurVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /** 版本号 */
    public static int getCurVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }


    /**
     * 检查app更新
     */
    private class SlaveCheckAppUpdateThread extends NetRequestThread {

        private static final String R_TAG = "SlaveCheckAppUpdateThread";

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.Comm_Activity.COMM_CHECK_APP_UPDATE_SUCCESS));
        }

        @Override
        protected String getJsonBody() {
            return "";
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(R_TAG, "onResponse=%s", s);
            Gson gson = new Gson();
            DataInfo.appVersionInfo = gson.fromJson(s, AppVersionInfo.class);

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(R_TAG, "volleryError=%s", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return R_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_CHECK_UPDATE_APP;
        }
    }


    /**
     * 显示分享对话框
     * @param context
     */
    public void showShareDialog(final Context context, final String shareContent) {
        View view = View.inflate(context, R.layout.layout_share, null);
        view.findViewById(R.id.btn_share_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(context, shareContent, "com.facebook.katana");
            }
        });
        view.findViewById(R.id.btn_share_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(context, shareContent, "com.tencent.mm");
            }
        });
        view.findViewById(R.id.btn_share_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(context, shareContent, "com.twitter.android");
            }
        });
        view.findViewById(R.id.btn_share_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText(context, shareContent);
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view).setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    private void shareText(Context context, String shareContent) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);

        } catch (Exception e) {
            String hint = context.getString(R.string.share_item_failed);
            LogUtils.toastShortMsg(context, hint);

        }

    }


    private void shareText(Context context, String shareContent, String packageName) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
            sendIntent.setType("text/plain");
            sendIntent.setPackage(packageName);
            context.startActivity(sendIntent);

        } catch (Exception e) {
            String hint = context.getString(R.string.share_item_failed);
            LogUtils.toastShortMsg(context, hint);

        }


    }




}
