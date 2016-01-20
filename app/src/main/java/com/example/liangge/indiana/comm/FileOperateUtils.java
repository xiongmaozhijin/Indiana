package com.example.liangge.indiana.comm;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;

/**
 * 文件操作工具类
 * Created by baoxing on 2015/12/9.
 */
public class FileOperateUtils {

    private static final String TAG = FileOperateUtils.class.getSimpleName();

    public interface Contants {
        public static final int FILE_NOT_FOUND = -1;
        public static final int IO_EXCEPTION = -2;
        public static final int SUCCEED = 1;
    }

    /**
     * 保存文件到程序内部空间  /data/data/package
     * @param context
     * @param fileName
     * @param content
     * @return -1:fileNotFound; -2:IOException; 1:写入成功
     */
    public static int writeFileInter(Context context, String fileName, String content) {
        return writeFile(context.getFilesDir().getPath(), fileName, content);
    }

    /**
     * 删除内部文件    /data/data/package
     * @param context
     * @param filename
     * @return  是否成功删除
     */
    public static boolean deleteFileInter(Context context, String filename) {
        return deleteFile(context.getFilesDir().getPath(), filename);
    }

    /**
     *
     * @param dirPath
     * @param fileName
     * @param content
     * @return  {@link FileOperateUtils.Contants}
     */
    public static int writeFile(String dirPath, String fileName, String content) {
        int result = Contants.SUCCEED;
        File file = new File(dirPath, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());

        } catch (FileNotFoundException e) {
            result = Contants.FILE_NOT_FOUND;

        } catch (IOException e) {
            result = Contants.IO_EXCEPTION;

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {

                }
            }
        }

        return result;
    }


    /**
     * 追加文件内容
     * @param file
     * @param content
     * @return {@link FileOperateUtils.Contants}
     */
    public static int appendFileContent(File file, String content) {
        int result = Contants.SUCCEED;

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            bufferedWriter.write(content);

        } catch (FileNotFoundException e) {
            result = Contants.FILE_NOT_FOUND;

        } catch (IOException e) {
            result = Contants.IO_EXCEPTION;

        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();

                } catch (IOException e) {
                }
            }
        }

        return result;
    }


    public static boolean isFileExisted(String dirPth, String filename) {
        File file = new File(dirPth, filename);
        return isFileExisted(file);
    }

    public static boolean isFileExisted(File file) {
        return file.exists();
    }

    public static boolean deleteFile(String dirPath, String filename) {
        File file = new File(dirPath, filename);
        return deleteFile(file);
    }

    public static boolean deleteFile(File file) {
        boolean bIsDelete = false;
        if (file.exists() ) {
            bIsDelete = file.delete();
        }

        return bIsDelete;
    }

    /**
     * 创建目录
     * @param pathDir
     * @return true if the directory was created, false on failure or if the directory already existed.
     */
    public static boolean makeDirs(String pathDir) {
        File file = new File(pathDir);
        return makeDirs(file);
    }

    public static boolean makeDirs(File file) {
        return file.mkdirs();
    }

    /**
     * 获取文件的大小，字节为单位
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long lSize = 0;
        if (file.exists() && file.isFile() ) {
            lSize = file.length();
        }

        return lSize;
    }


    /**
     * 根据Uri返回对应的File
     * @param context
     * @param contentUri
     * @return
     */
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

    /**
     * 根据 uri 把文件复制到 dstFile 中
     * @param context
     * @param uri
     * @param dstFile
     * @return
     */
    public static void copyFile(Context context, Uri uri, File dstFile) {
        File srcfile = getRealFileFromURI(context, uri);
        copyFile(srcfile, dstFile);
    }


    public static void copyFile(File srcfile, File dstfile) {
        LogUtils.i(TAG, "copy file from %s to %s", srcfile.getAbsolutePath(), dstfile.getAbsolutePath());

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


    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void byte2File(byte[] buf, File file) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 返回可读的文件夹大小
     * @param path
     * @return
     */
    public static String getFolderSizeHumanable(String path) {
        return getFolderSizeHumanable(new File(path));
    }

    /**
     * 返回可读的文件夹大小
     * @param file
     * @return
     */
    public static String getFolderSizeHumanable(File file) {
        if (file!=null && file.isDirectory()) {
            long size = getFolderSize(file);
            return getFormatSize(size);
        }

        return "";
    }

    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(java.io.File file){
        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++)
            {
                if (fileList[i].isDirectory())
                {
                    size = size + getFolderSize(fileList[i]);

                }else{
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }


    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size/1024;
        if(kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte/1024;
        if(megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if(gigaByte < 1) {
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte/1024;
        if(teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


}
