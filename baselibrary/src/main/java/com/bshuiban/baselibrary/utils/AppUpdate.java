package com.bshuiban.baselibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

/**
 * Created by xinheng on 2018/6/25.<br/>
 * describe：app升级安装
 */
public class AppUpdate {
    private static void installApk(Context appContext, String downloadApkPath) {
        if (TextUtils.isEmpty(downloadApkPath)) {
            Toast.makeText(appContext, "APP安装文件不存在或已损坏", Toast.LENGTH_LONG).show();
            return;
        }
        File apkFile = new File(Uri.parse(downloadApkPath).getPath());
        if (!apkFile.exists()) {
            Toast.makeText(appContext, "APP安装文件不存在或已损坏", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            boolean haveInstallPermission = appContext.getPackageManager().canRequestPackageInstalls();
            if(haveInstallPermission){//先获取是否有安装未知来源应用的权限
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(appContext, "com.bshuiban.fileprovider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            }else{
                Toast.makeText(appContext, "请允许安装未知来源", Toast.LENGTH_SHORT).show();

            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(appContext, "com.bshuiban.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            //Intent intent = new Intent();
            //intent.addFlags(268435456);
            //intent.setAction("android.intent.action.VIEW");
            String var2 = apkFile.getName();
            String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
            String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
            intent.setDataAndType(Uri.fromFile(apkFile), type);
            try {
                appContext.startActivity(intent);
            } catch (Exception var5) {
                var5.printStackTrace();
                Toast.makeText(appContext, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
            }
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        appContext.startActivity(intent);
    }
}
