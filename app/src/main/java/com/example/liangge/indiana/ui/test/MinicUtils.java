/*
package com.example.minicrpc.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;

import com.example.minicrpc.App;
import com.example.minicrpc.R;
import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.entity.ContactPerson;
import com.example.minicrpc.entity.LuckyPeople;
import com.example.minicrpc.fragments.FragmentBirthday;

public class MinicUtils {

    private static final String TAG = "MinicUtils";
    private static final boolean DEBUG = MinicRPCLog.DEBUG;


    private MinicUtils() {}


    */
/**
     * 计算离生日还剩多少天
     * 这个函数是不严谨的，仅做简单测试使用
     * 日期解析的格式搞好久，要注意，其中DateFormat.getDateInstance().parse()在虚拟机上和
     * 真机上运行结果又不同！
     * SimpleDateFormat解析时，其中的字符格式要求是要非常清晰的 MM 和 M 不同。
     * @param born
     * @return
     * @throws ParseException
     *//*

    public static int calculateBetweenDays(final Date born) {
        int days = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());

        try {
            int year = Calendar.getInstance().get(Calendar.YEAR);

            String birthday_M_D = new SimpleDateFormat("M-d", Locale.getDefault()).format(born);
            Date now = new Date();
            String birthday = year + "-" + birthday_M_D;

            if (DEBUG) {
                MinicRPCLog.d(TAG, "birthday is %s.", birthday);
            }


//			Date born1 = DateFormat.getDateInstance().parse(birthday);
            Date born1 = sdf.parse(birthday);

            String nowM_D = new SimpleDateFormat("M-d", Locale.getDefault()).format(now);
            String nowY_M_D = year + "-" + nowM_D;

            if (DEBUG) {
                MinicRPCLog.d(TAG, "nowY_M_D is %s.", nowY_M_D);
            }

//			now = DateFormat.getDateInstance().parse(nowY_M_D);
            now = sdf.parse(nowY_M_D);

            long t = born1.getTime() - now.getTime();


            if (t < 0) {
                year += 1;
                birthday = year + "-" + birthday_M_D;
//				born1 = DateFormat.getDateInstance().parse(birthday);
                born1 = sdf.parse(birthday);
                t = born1.getTime() - now.getTime();
            }

            days = (int) (t / (1000*60*60*24));

        } catch (ParseException e) {
            e.printStackTrace();

            MinicRPCLog.e(TAG, "%s%n. %s", "format error", e.toString());
        }

        return days;
    }


    public static String calculateBetweenDays(final Date born, Context context) {
        int days = calculateBetweenDays(born);
        String s = String.format(context.getResources().getString(R.string.birthday_info_birth_leave_hint), days);
        return s;
    }

    */
/**
     * 获取唯一的文件图片文件名 xxx.jpg
     * @return
     *//*

    public static String getUniqueImageFileName() {
        if (DEBUG) {
            MinicRPCLog.d(TAG, "%s", "getUniqueImageFileName");
        }


        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_M_d_hh_mm_ss", Locale.getDefault());
        String fileName = sdf.format(date);
        fileName = fileName + ".jpg";
        if (DEBUG) {
            MinicRPCLog.d(TAG, "生成的唯一图片文件名是：%s", fileName);
        }

        return fileName;
    }

    */
/**
     * 返回唯一的文件名文件，适用于imageload
     * @param context
     * @return
     *//*

    public static File getUniqueImageFileName(Context context) {
        App app = (App) context.getApplicationContext();
        String imageDir = app.getImageDir();
        File file = new File(imageDir, getUniqueImageFileName());
        return file;
    }

    */
/**
     * 返回唯一的文件名文件
     * @param context
     * @return
     *//*

    public static File getUniqueImageFileNameReal(Context context) {
        App app = (App) context.getApplicationContext();
        String imageDir = app.getImageDirReal();
        File file = new File(imageDir, getUniqueImageFileName());
        return file;
    }

    */
/**
     * 保存文件
     * @param inputfile
     * @param os
     * @return	1 保存成功; -1 保存失败 ； -2 没有该文件
     *//*

    public static int savefile(File savefile, ByteArrayOutputStream os) {
        FileOutputStream fos = null;
        int result = 1;
        try {
            fos = new FileOutputStream(savefile);
            try {
                fos.write(os.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
                result = -1;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = -2;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    */
/**
     * 保存文件
     * @param bitmap
     * @param context
     * @return
     *//*

    public static File saveFile(final Bitmap bitmap, Context context) {
        File dstfile = getUniqueImageFileNameReal(context);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, bos);
        savefile(dstfile, bos);
        return dstfile;
    }

    public static File saveFile(String dstfile, InputStream is) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            fos = new FileOutputStream(dstfile);
            bis = new BufferedInputStream(is);
            int length = bis.available();
            byte[] buffer = new byte[length];
            while (length != -1) {
                length = bis.read(buffer);
                fos.write(buffer);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new File(dstfile);
    }


    public static File getFile(final String filepath) {
        File file = new File(filepath);
        return file;
    }

    */
/**
     * 根据Uri获取full path
     * @param context
     * @param contentUri
     * @return
     *//*

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    */
/**
     * 根据Uri返回对应的File
     * @param context
     * @param contentUri
     * @return
     *//*

    public static File getRealFileFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            return new File(path);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static void copyFile(File srcfile, File dstfile) {
        if (DEBUG) {
            MinicRPCLog.d(TAG, "copy file from %s to %s", srcfile.getAbsolutePath(), dstfile.getAbsolutePath());
        }
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new FileInputStream(srcfile);
            fos = new FileOutputStream(dstfile);
            int length = fis.available();
            byte[] buffer = new byte[length];
            while (length != -1) {
                length = fis.read(buffer);
                fos.write(buffer);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static void copyFile(File srcfile, String dstfilename, Context context) {
        App app = (App) context.getApplicationContext();
        String imageDir = app.getImageDirReal();
        File dstfile = new File(imageDir, dstfilename);
        copyFile(srcfile, dstfile);
    }

    public static File copyFile(Context context, Uri uri) {
        File srcfile = getRealFileFromURI(context, uri);
        File dstfile = getUniqueImageFileNameReal(context);
        copyFile(srcfile, dstfile);
        return dstfile;
    }

    */
/**
     * 清除临时文件
     * @param listTempFile
     * @param skipfile
     *//*

    public static void clearTempFile(List<File> listTempFile, File skipfile) {
        listTempFile.remove(skipfile);
        for (File file : listTempFile) {
            if (file.exists()) {
                file.delete();
            }
        }
        listTempFile.clear();
    }

    */
/**
     * 获取联系人相关信息
     * @param uri
     * @return
     *//*

    public static ContactPerson getContactPersonDetail(Uri uri, Context context) {

        String[] PHONEA_PROJECTION = new String[]{
                Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID, Phone.CONTACT_ID
        };
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        ContactPerson cp = getContactPersonDetail(uri, cursor, context);

        return cp;
    }

    private static ContactPerson getContactPersonDetail(Uri uri, Cursor cursor, Context context) {
        ContactPerson cp = new ContactPerson();
        if (cursor != null) {
            cursor.moveToFirst();
            int id_ = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String id = cursor.getString(id_);
            int name_ = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(name_);
            Cursor allPhones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",
                    new String[]{id}, null);

            List<String> allPhoneslist = new ArrayList<String>();
            if (allPhones.moveToFirst()) {
                for (; !allPhones.isAfterLast(); allPhones.moveToNext()) {
                    int telType_ = allPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int telType = allPhones.getInt(telType_);
                    if (telType == 2) {
                        int telNo_ = allPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String telNo = allPhones.getString(telNo_);
                        allPhoneslist.add(telNo);
                    }
                }
            }
            if (!allPhones.isClosed()) {
                allPhones.close();
            }

            Bitmap bitmapPortratit = null;
            int photoid_ = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
            long photoid = cursor.getLong(photoid_);
            if (photoid > 0) {
                InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
                bitmapPortratit = BitmapFactory.decodeStream(is);
            }


            cp.setAllphones(allPhoneslist);
            cp.setId(Long.parseLong(id) );
            cp.setName(name);
            cp.setProtrait(bitmapPortratit);

        }


        return cp;
    }


    public static Date getDate(String dateS) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
            MinicRPCLog.e(TAG, "解析错误:%s", e.toString());
        }

        return date;
    }

    */
/**
     * 返回yyyy-M-d
     * @param date
     * @return
     *//*

    public static String getDate(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String r = sdf.format(date);
        return r;
    }

    public static String getDate(final Date date, String stringPattern, String dateformat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat, Locale.getDefault());
        String ymd = sdf.format(date);
        String s = String.format(stringPattern, ymd);

        return s;
    }

    */
/**
     * 转换成ImageLoad可以加载的型式
     * @param path
     * @return
     *//*

    public static String wrapperToImageload(String path) {
        if (path == null || path.equals("")) {
            return "";
        }
        String p = path.replaceFirst("^/[s|S]torage", "file:///mnt");
        p = p.replaceFirst("^file:///mnt/sdcard0", "file:///mnt/sdcard");
        if (DEBUG) {
            MinicRPCLog.e(TAG, "wrapper:%s", p);
        }
        return p;
    }

    */
/**
     * getStringBirthdayHint
     * @param luckyDog
     * @param context
     * @return
     *//*

    public static String getStringBirthdayHint(final LuckyDog luckyDog, Context context) {
        String rs = "";

        int old = howOld(luckyDog.getBirthday());

        if (luckyDog.getSex() == LuckyPeople.MAN) {
            rs = String.format(context.getResources().getString(R.string.birthday_info_birth_hint), context.getResources().getString(R.string.he), old);
        } else {
            rs = String.format(context.getResources().getString(R.string.birthday_info_birth_hint), context.getResources().getString(R.string.she), old);
        }

        return rs;
    }


    private static int howOld(final Date birthday) {
        int r = -1;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        String birthdayY = sdf.format(birthday);
        String nowY = sdf.format(now);
        r = Integer.parseInt(nowY) - Integer.parseInt(birthdayY);

        if (r != -1) {
            if (isOver(birthday)) {
                r += 1;
            }
        }

        return r;
    }


    private static boolean isOver(final Date birthday) {
        boolean b = false;
        SimpleDateFormat sdf = new SimpleDateFormat("M-d", Locale.getDefault());
        String md = sdf.format(birthday);
        Date now = new Date();
        sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        String y= sdf.format(now);
        String ymd = y+md;
        Date birDate = new Date();
        try {
            birDate = sdf.parse(ymd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        b = now.getTime()-birDate.getTime() > 0;


        return b;
    }


    public static String getThisYearBirthDateString(final Date birthday) {
        Date now = new Date();
        SimpleDateFormat sdf  =  new SimpleDateFormat("yyyy", Locale.getDefault());
        String y = sdf.format(now);
        sdf = new SimpleDateFormat("M-d", Locale.getDefault());
        String md = sdf.format(birthday);
        String ymd = y + "-" + md;

        return ymd;
    }



}
*/
