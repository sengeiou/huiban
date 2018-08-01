package com.bshuiban.baselibrary.utils;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class FileUtils {
    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存缓存文件
     *
     * @param file_name 文件名
     * @param bytes     文件数据
     * @return 文件是否保存成功
     */
    public static boolean saveCacheFile(String file_name, byte[] bytes) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Hbag/Cache/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        path += file_name;
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            outputStream.write(bytes);
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String SDPATH = null;

    public String getSDPATH() {
        return SDPATH;
    }

    public FileUtils() {
        // SDCARD
        SDPATH = Environment.getExternalStorageDirectory() + "";
        System.out.println("SDPATH=" + SDPATH);
    }

    /*
     * 创建文件路径
     */
    public File CreatSDFile(String fileNmae) {
        File file = new File(SDPATH + fileNmae);
        try {
            if (!isFileExist(SDPATH + fileNmae)) {
                file.createNewFile();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    public static void isExist(String path) {
        File file = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
     * 创建文件根目录
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /*
     * 检查文件是否存在
     */
    public boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /*
     * 检查文件是否存在
     */
    public static String isCheckFileExist(String fileName) {
        File file = new File(fileName);
        String name = file.getName();
        return name;
    }

    public static File saveBitmap(Bitmap bitmap, String dir, String name) {
        File d = new File(dir);
        if (!d.exists()) {
            d.mkdirs();
        }
        File f = new File(dir, name);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return f;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static synchronized void saveFile(File file, String text) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file, true));
            bfw.write(text);
            bfw.newLine();
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * 检查文件是否存在
     */
    public static boolean isFilesExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static String getMd5ByFile(File file) {
        String value = "";
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getUriPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param originFile 需要分割的文件的路径
     * @param partNumber 文件的具体部分，第1、2、3。。。
     * @param partSize   每部分文件的大小，以KB为单位
     * @return 返回切片文件路径
     */
    public static String getPartFile(String originFile, int partNumber, int partSize, String filename) {

        RandomAccessFile rFile;
        OutputStream os;
        try {
            rFile = new RandomAccessFile(originFile, "r");
            byte[] b = new byte[partSize * 1024];
            rFile.seek(partNumber * (partSize * 1024));
            int s = rFile.read(b);
            if (s != -1) {
                os = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + filename);
                os.write(b, 0, s);
                os.flush();
                os.close();
                return Environment.getExternalStorageDirectory() + "/" + filename;
            }
        } catch (IOException e) {
            return null;
        }
        return null;

    }

    /**
     * 获取文件的分片个数
     *
     * @param size 文件大小，以KB为单位
     * @param max  文件分块大小，以KB为单位
     * @return 文件的分块个数
     */
    public static int getBsum(int size, int max) {
        int bsum = size % max;
        if (bsum > 0) {
            bsum = (size / max) + 1;
        } else {
            bsum = size / max;
        }
        return bsum;
    }

    /**
     * 读取文本文件
     *
     * @param path
     * @return
     */
    public static String getTxt(String path) {
        String data = null;
        int len;
        byte[] strBuffer;
        File file = new File(path);
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                len = (int) file.length();
                strBuffer = new byte[len];
                inputStream.read(strBuffer, 0, len);
                inputStream.close();
                data = new String(strBuffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
