package com.miaxis.smartbank.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver{     
	static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	 
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent newIntent = new Intent(context,EmqttService.class);
            context.startService(newIntent);
    	}
    }
}
