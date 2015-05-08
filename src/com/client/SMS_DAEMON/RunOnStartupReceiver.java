package com.client.SMS_DAEMON;

import com.service.SMS_DAEMON.BackService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RunOnStartupReceiver extends BroadcastReceiver { 
		Context appContext;
        @Override
        public void onReceive(Context context, Intent intent) {
        	appContext = context.getApplicationContext();
			Intent my_intent = new Intent(appContext,BackService.class);
			appContext.startService(my_intent);
        }
} 
