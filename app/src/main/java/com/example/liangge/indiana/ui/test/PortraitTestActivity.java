package com.example.liangge.indiana.ui.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.EditUserInfoBiz;
import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class PortraitTestActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = PortraitTestActivity.class.getSimpleName();

    private File mUserTempPortraitFile;

    private static final int REQUEST_PORTRAIT_FROM_PHOTO = 0;
    private static final int REQUEST_PORTRAIT_FROM_CUT = 1;
    private static final int REQUEST_PORTRAIT_FROM_GRALLERY = 2;

    /** 图片裁剪输出大小 */
    private static int PROTRAIT_SIZE = 150; //dp

    private ImageView mImgPortrait;

    private EditUserInfoBiz mEditUserInfoBiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_test);
        mImgPortrait = (ImageView) findViewById(R.id.imgview);

        PROTRAIT_SIZE = LocalDisplay.dp2px(PROTRAIT_SIZE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mEditUserInfoBiz = EditUserInfoBiz.getInstance(this);

    }

    public void onBtnPhoto(View view) {
        mUserTempPortraitFile = mEditUserInfoBiz.getUserPortraitTempFile(this);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mUserTempPortraitFile));
        startActivityForResult(i, REQUEST_PORTRAIT_FROM_PHOTO);

    }


    public void onBtnGray(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, null);
        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(i, REQUEST_PORTRAIT_FROM_GRALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult()");

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case REQUEST_PORTRAIT_FROM_PHOTO:
                    startPhotoZoom(Uri.fromFile(mEditUserInfoBiz.getUserPortraitTempFile(this)), PROTRAIT_SIZE);
                    break;
                case REQUEST_PORTRAIT_FROM_CUT:
                    showPortraitFromCut(data);

                    break;
                case REQUEST_PORTRAIT_FROM_GRALLERY:
                    if (data != null) {
                        mEditUserInfoBiz.copyFileFromUri(this, data.getData(), mUserTempPortraitFile);
                        startPhotoZoom(data.getData(), PROTRAIT_SIZE);

                    }


                    break;
            }

        }

    }


    /** 图片压缩及显示 */
    private void showPortraitFromCut(Intent i) {
        LogUtils.i(TAG, "showPortraitFromCut()");

        Bundle bundle = i.getExtras();

        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size());
            mImgPortrait.setImageBitmap(bitmap);

            LogUtils.i(TAG, "压缩前：字节大小-%d, 高度-%d, 宽度-%d ", photo.getByteCount(), photo.getHeight(), photo.getWidth());
            LogUtils.i(TAG, "压缩后：字节大小-%d, 高度-%d, 宽度-%d, 输出流大小： ", bitmap.getByteCount(), bitmap.getHeight(), bitmap.getWidth(), bos.size());

        }

    }


    /**
     * 图片裁剪
     *
     * @param uri
     * @param size
     */
    private void startPhotoZoom(Uri uri, int size) {
        LogUtils.i(TAG, "startPhotoZoom(). uri=%s, size=%d", uri.toString(), size);

        Intent i = new Intent("com.android.camera.action.CROP");
        i.setDataAndType(uri, "image/*");
        i.putExtra("crop", true);
        i.putExtra("aspectX", 1);
        i.putExtra("aspectY", 1);
        i.putExtra("outputX", size);
        i.putExtra("outputY", size);
        i.putExtra("return-data", true);
        startActivityForResult(i, REQUEST_PORTRAIT_FROM_CUT);
    }


    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }
}
