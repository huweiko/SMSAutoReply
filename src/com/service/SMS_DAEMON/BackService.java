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

		start(intent);
		super.onStart(intent, startId);
	}
	
	private void start(Intent intent)
    {
    	IntentFilter intentFilter = new IntentFilter();
    	intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		String AutoReplyNum = intent.getStringExtra("AutoReplyNum");
		String TextContainAutoReply = intent.getStringExtra("TextContainAutoReply");
		String ContainAutoNotReply = intent.getStringExtra("ContainAutoNotReply");
		String AutoReplyText = intent.getStringExtra("AutoReplyText");
    	r = new smsReceiver();
    	r.setSms_ContainAutoNotReply(ContainAutoNotReply);
    	r.setSms_ContainAutoReply(TextContainAutoReply);
    	r.setSms_number(AutoReplyNum);
    	r.setSms_reply_content(AutoReplyText);
    	registerReceiver(r,intentFilter);

    }
    private smsReceiver r;
    private void stop()
    {
    	unregisterReceiver(r);
    }

}
