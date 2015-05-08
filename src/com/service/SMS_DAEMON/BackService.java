package com.service.SMS_DAEMON;

import com.client.SMS_DAEMON.Constant;
import com.client.SMS_DAEMON.Constant.Preference;
import com.receiver.SMS_DAEMON.smsReceiver;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;


public class BackService extends Service {
	private boolean ThreadStatus = true;
	private SharedPreferences preferences;
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
	  public class RefreshThread  extends Thread{
	    	
	    	@Override
			public void run(){

	    		while(ThreadStatus)
	        	{
	    			for(int i = 0;i< 100;i++){
	    				try {
							sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    			}
	    			Log.i("RefreshThread", "线程运行");
	    
	        	}
	    		
	    	
	    	}
	    }
	    
	    RefreshThread mRefreshThread = null;
	private void start(Intent intent)
    {
		mRefreshThread = new RefreshThread();
		mRefreshThread.start();
    	IntentFilter intentFilter = new IntentFilter();
    	intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		if (null == preferences) {
			preferences = Preference.getSharedPreferences(this);
		} 
		String AutoReplyNum = preferences.getString(Constant.C_AutoReplyNum, "10010");
		String AutoReplyText = preferences.getString(Constant.C_AutoReplyText, "hello");
    	String ContainAutoNotReply = preferences.getString(Constant.C_ContainAutoNotReply, "");
    	String TextContainAutoReply = preferences.getString(Constant.C_TextContainAutoReply, "4G");
    	String EditTextSendNum = preferences.getString(Constant.C_EditTextSendNum, "10010");
    	r = new smsReceiver();
    	r.setSms_ContainAutoNotReply(ContainAutoNotReply);
    	r.setSms_ContainAutoReply(TextContainAutoReply);
    	r.setSms_number(AutoReplyNum);
    	r.setSms_reply_content(AutoReplyText);
    	r.setSms_EditTextSendNum(EditTextSendNum);
    	registerReceiver(r,intentFilter);

    }
    private smsReceiver r;
    private void stop()
    {
    	unregisterReceiver(r);
    	if(mRefreshThread != null){
    		ThreadStatus = false;
    		try {
				mRefreshThread.join(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		mRefreshThread = null;
    		
    	}
    }

}
