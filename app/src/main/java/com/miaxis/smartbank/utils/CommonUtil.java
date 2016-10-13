package com.miaxis.smartbank.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.miaxis.smartbank.domain.Version;

/**
 * Created by xu.nan on 2016/8/17.
 */
public class CommonUtil {

    public static double xround(double x, int num) {
        Log.e("---------- ", ""+x);
        return Math.round(x * Math.pow(10, num)) / Math.pow(10, num) ;
    }

    public static Version getCurVersion(Context context) {
        try {
            Version v = new Version();
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            v.setVersionCode(info.versionCode);
            v.setVersionName(info.versionName);
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
