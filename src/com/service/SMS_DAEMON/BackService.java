package com.service.SMS_DAEMON;

import com.receiver.SMS_DAEMON.smsReceiver;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


public class BackService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub		
		
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stop();
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		String value = intent.getStringExtra("content");
		start(value);
		super.onStart(intent, startId);
	}
	
	private void start(String str)
    {
    	IntentFilter intentFilter = new IntentFilter();
    	intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
    	r = new smsReceiver();
    	r.GetSmsContent(str);
    	registerReceiver(r,intentFilter);

    }
    private smsReceiver r;
    private void stop()
    {
    	unregisterReceiver(r);
    }

}
