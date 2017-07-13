package com.example.web.test;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CordovaActivity;

import com.worklight.androidgap.api.WL;
import com.worklight.androidgap.api.WLInitWebFrameworkResult;
import com.worklight.androidgap.api.WLInitWebFrameworkListener;

import com.ibm.MFPApplication;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends CordovaActivity implements WLInitWebFrameworkListener {
	public String URL = "https://display.do-order.com";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		sendBroadcast(new Intent("dongk.web.noeventcheck"));
		Log.d("eventCheck", "No Event Check");
        if (!((MFPApplication)this.getApplication()).hasCordovaSplashscreen()) {
            WL.getInstance().showSplashScreen(this);
        }
		WL.getInstance().hideSplashScreen();
        init();

		WL.getInstance().initializeWebFramework(getApplicationContext(), this);
	}

	/**
	 * The IBM MobileFirst Platform calls this method after its initialization is complete and web resources are ready to be used.
	 */
 	public void onInitWebFrameworkComplete(WLInitWebFrameworkResult result){
		if (result.getStatusCode() == WLInitWebFrameworkResult.SUCCESS) {
//			Log.d("!!!", URL);
			if (getIntent().hasExtra("url")) {
				Log.d("URL check", "URL check - " + getIntent().getStringExtra("url"));
				URL = getIntent().getStringExtra("url");
				loadUrl(URL);
			}else {
				Log.d("URL", "URL - " + URL);
				super.loadUrl(URL);
				Log.e("!!!", WL.getInstance().getMainHtmlFilePath());
			}
		} else {
			handleWebFrameworkInitFailure(result);
		}
	}

	private void handleWebFrameworkInitFailure(WLInitWebFrameworkResult result){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setNegativeButton(R.string.close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which){
				finish();
			}
		});

		alertDialogBuilder.setTitle(R.string.error);
		alertDialogBuilder.setMessage(result.getMessage());
		alertDialogBuilder.setCancelable(false).create().show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sendBroadcast(new Intent("dongk.web.eventcheck"));
		Log.d("eventCheck", "On Event Check");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (URL.equals("https://display.do-order.com")) {
					Log.d("restart", "display - restart");
					sendBroadcast(new Intent("dongk.web.restart"));
				}
				else if (URL.equals("https://www.google.com")) {
					Log.d("restart", "google - restart");
					sendBroadcast(new Intent("dongk.web.restart.google"));
				}
			}
		}, 5000);

	}
}
