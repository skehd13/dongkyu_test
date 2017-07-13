package com.example.web.test;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ibm.MFPApplication;

/**
 * Created by Dongkyulee on 2017. 7. 13..
 */

public class Receiver extends BroadcastReceiver {
    public MFPApplication app;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Receiver",intent.getAction());
        if (intent.getAction().equals("dongk.web.start")) {
            Log.d("Receiver", "start Display");
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.putExtra("url", "https://display.do-order.com");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent1);
        } else if (intent.getAction().equals("dongk.web.start.google")) {
            Log.d("Receiver", "start Google");
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.putExtra("url", "https://www.google.com");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent1);
            }

    }
}
