package com.example.liangge.indiana.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.EditUserInfoBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.UserInfoEntity;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 编辑个人信息
 */
public class EditUserInfoActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = EditUserInfoActivity.class.getSimpleName();

    private TextView mTxvId;

    private ImageView mImgUserPortrait;

    private EditText mEdtUsername;

    private EditText mEdtPhone;

    private EditText mEdtGoodName;
    private EditText mEdtGoodPhone;
    private EditText mEdtGoodAddress;

    private ByteArrayOutputStream mBAOPortrait;

    private String[] mStrDialogItems;

    /** 收货地址Wrapper */
    private View mViewAddressInnerWrapper;
    private View mViewSplit;

    private ImageView mViewArrow;

    private PersonalCenterBiz mPersonalCenterBiz;


    private File mUserTempPortraitFile;

    private static final int REQUEST_PORTRAIT_FROM_PHOTO = 0;
    private static final int REQUEST_PORTRAIT_FROM_CUT = 1;
    private static final int REQUEST_PORTRAIT_FROM_GRALLERY = 2;

    /** 图片裁剪输出大小 */
    private static int PROTRAIT_SIZE = 150; //dp

    private EditUserInfoBiz mEditUserInfoBiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        initView();
        initManager();
        initRes();
    }

    private void initRes() {
        String item1 = getResources().getString(R.string.edit_user_portrait_from_phone);
        String item2 = getResources().getString(R.string.edit_user_portrait_from_gallery);
        mStrDialogItems = new String[] {item1, item2};

    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
        mEditUserInfoBiz = EditUserInfoBiz.getInstance(this);
    }

    private void initView() {
        mTxvId = (TextView) findViewById(R.id.personal_info_id);
        mImgUserPortrait = (ImageView) findViewById(R.id.user_info_portrait);
        mEdtUsername = (EditText) findViewById(R.id.personal_info_username);
        mEdtPhone = (EditText) findViewById(R.id.personal_info_phone);

        mViewAddressInnerWrapper = findViewById(R.id.edit_address_inner_wrapper);
        mViewSplit = findViewById(R.id.line_temp1);

        mEdtGoodName = (EditText) findViewById(R.id.personal_info_good_name);
        mEdtGoodPhone = (EditText) findViewById(R.id.personal_info_good_phone);
        mEdtGoodAddress = (EditText) findViewById(R.id.personal_info_good_address);

        mViewArrow = (ImageView) findViewById(R.id.edit_address_arrow);

        mImgUserPortrait.post(new Runnable() {
            @Override
            public void run() {
                initUserInfo();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (mPersonalCenterBiz.isLogin()) {


        } else {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        LogUtils.i(TAG, "initUserInfo()");
        if (mPersonalCenterBiz.isLogin()) {
            UserInfoEntity user = mPersonalCenterBiz.getUserInfo();
            mTxvId.setText(user.getId()+"");
            ImageLoader.getInstance().displayImage(user.getPhoto(), mImgUserPortrait, getUserPortraitImageConfig());
            mEdtUsername.setText(user.getNickname());
            mEdtPhone.setText(user.getPhone_number());
            if ( (user.getAddress() != null) && (user.getAddress().size()>0) ) {
                UserInfoEntity.UserAddress address = user.getAddress().get(0);
                if (address != null) {
                    mEdtGoodName.setText(address.getName());
                    mEdtGoodPhone.setText(address.getPhone());
                    mEdtGoodAddress.setText(address.getDetail());
                }
            }

        }
    }

    /**
     * 点击了地址管理
     * @param view
     */
    public void onBtnEditAddress(View view) {
        LogUtils.w(TAG, "onBtnEditAddress()");
        Boolean isExpand = view.getTag()==null ? false : (Boolean) view.getTag();
        LogUtils.w(TAG, "isExpand=%b", isExpand);

        if (isExpand) {
            mViewAddressInnerWrapper.setVisibility(View.GONE);
            mViewSplit.setVisibility(View.INVISIBLE);
            mViewArrow.setImageResource(R.drawable.ic_more);

        } else {
            mViewAddressInnerWrapper.setVisibility(View.VISIBLE);
            mViewSplit.setVisibility(View.VISIBLE);
            mViewArrow.setImageResource(R.drawable.ic_more_down);

        }

        isExpand = !isExpand;
        view.setTag(isExpand);
    }

    public void onBtnEditPortrait(View view) {
        LogUtils.i(TAG, "编辑头像信息");
        showEditUserPortraitDialog();
    }

    private void showEditUserPortraitDialog() {
        new AlertDialog.Builder(this).setItems(mStrDialogItems,
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                onBtnPhoto(null);
                                break;

                            case 1:
                                onBtnGray(null);
                                break;
                        }
                    }
                } )
                .create()
                .show();
    }


    /**
     * 保存个人信息
     * @param view
     */
    public void onBtnSave(View view) {
        LogUtils.i(TAG, "onBtnSave()");
        String nickname = mEdtUsername.getText().toString();
        String phoneNumber = mEdtPhone.getText().toString();
        String goodName = mEdtGoodName.getText().toString();
        String goodPhone = mEdtGoodPhone.getText().toString();
        String goodDetailAddress = mEdtGoodAddress.getText().toString();

        UserInfoEntity user = mPersonalCenterBiz.getUserInfo();
        long isUpdate;
       if ( (user.getAddress()!=null) && (user.getAddress().size()>0) && (user.getAddress().get(0)!=null) ) {
           isUpdate = user.getAddress().get(0).getId();
       } else {
           isUpdate = 0;
       }

        UserInfoEntity.UserAddress address1 = new UserInfoEntity.UserAddress(isUpdate, goodName, goodPhone, "", "", "", goodDetailAddress, 1);
        List<UserInfoEntity.UserAddress> addresses = new ArrayList<>();
        addresses.add(address1);


        boolean isValid = isVaildUserInfo(nickname, phoneNumber, addresses);

        if (isValid) {
            if (mBAOPortrait == null) {
                mBAOPortrait = new ByteArrayOutputStream();
            }
            mEditUserInfoBiz.setUpdateRequest(mBAOPortrait, nickname, phoneNumber, addresses);
            mEditUserInfoBiz.onBtnSaveUserInfo();

        } else {
            String errorHint = getResources().getString(R.string.userinfo_not_finish);
            LogUtils.toastShortMsg(this, errorHint);

        }


    }


    private boolean isVaildUserInfo(String nickname, String phoneNumber, List<UserInfoEntity.UserAddress> addresses) {
        boolean r = true;
        UserInfoEntity.UserAddress address1 = addresses.get(0);
        if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address1.getDetail()) ) {
            r = false;
        }

        return r;
    }


    public void onBtnBack(View view) {
        finish();
    }


    @Override
    protected void handleUIMessage(String strUIAction) {
        LogUtils.w(TAG, "handleUIMessage(). action=%s", strUIAction);

        if (strUIAction.equals(UIMessageConts.PersonalCenterMessage.PERSONALCENTER_M_UPDATE_USER_INFO)) {
            initUserInfo();
        }
    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }






    public void onBtnPhoto(View view) {
        LogUtils.i(TAG, "onBtnPhoto()");
        mUserTempPortraitFile = mEditUserInfoBiz.getUserPortraitTempFile(this);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mUserTempPortraitFile));
        startActivityForResult(i, REQUEST_PORTRAIT_FROM_PHOTO);

    }


    public void onBtnGray(View view) {
        LogUtils.i(TAG, "onBtnGray()");
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
                        startPhotoZoom(data.getData(), PROTRAIT_SIZE);

                    }


                    break;
            }

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





    /** 图片压缩及显示 */
    private void showPortraitFromCut(Intent i) {
        LogUtils.i(TAG, "showPortraitFromCut()");

        Bundle bundle = i.getExtras();

        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size());
//            mImgUserPortrait.setImageBitmap(bitmap);
            mBAOPortrait = bos;
            mEditUserInfoBiz.saveUserPortrait(bos, this);

            displayUserPortrait(bitmap);
        }

    }


    private void displayUserPortrait(Bitmap bitmap) {
        String uri = Uri.fromFile(mEditUserInfoBiz.getUserPortraitTempFile(this)).toString();
        LogUtils.i(TAG, "displayUserPortrait(). uri=%s", uri);
//        ImageLoader.getInstance().displayImage(uri, mImgUserPortrait, getUserPortraitImageConfig());
        mImgUserPortrait.setImageBitmap(bitmap);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBAOPortrait !=  null) {

        }


    }


}
