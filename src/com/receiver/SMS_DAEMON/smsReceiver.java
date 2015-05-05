package com.receiver.SMS_DAEMON;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class smsReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub   
		//GetSmsNum(intent);
		StringBuilder number = new StringBuilder();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] myOBJpdus = (Object[]) bundle.get("pdus");
            SmsMessage[] message = new SmsMessage[myOBJpdus.length];
            for (int i = 0; i < myOBJpdus.length; i++) {
                    message[i] = SmsMessage.createFromPdu((byte[]) myOBJpdus[i]);
            }
            for (SmsMessage currentMessage : message) {
            	number.append(currentMessage.getDisplayOriginatingAddress());
            }
            sms_number=number.toString();
		}
		long id = getThreadId(context);

		Uri mUri=Uri.parse("content://sms/conversations/" + id);

		context.getContentResolver().delete(mUri, null, null);
		//Toast.makeText(context, sms_number+sms_reply_content, Toast.LENGTH_LONG).show();
		//this.abortBroadcast();
//		SmsManager smsManager = SmsManager.getDefault();    		
//		smsManager.sendTextMessage(sms_number, null, sms_reply_content, null, null);
	}
    private long getThreadId(Context context) {

		long threadId = 0;
	
		String SMS_READ_COLUMN = "read";
	
		String WHERE_CONDITION = SMS_READ_COLUMN + " = 0";
	
		String SORT_ORDER = "date DESC";
	
		int count = 0;
	
		Cursor cursor = context.getContentResolver().query(
	
		Uri.parse("content://sms/inbox"),
	
		new String[] { "_id", "thread_id", "address", "person", "date", "body" },
	
		WHERE_CONDITION,
	
		null,
	
		SORT_ORDER);
	
		if (cursor != null) {
	
			try {
		
				count = cursor.getCount();
			
				if(count > 0){
			
					cursor.moveToFirst();
				
					threadId = cursor.getLong(1);
				} 
			}
			finally {
				cursor.close();
			}
		}
	
		Log.i("threadId", String.valueOf(threadId));
	
		return threadId;

	}
	/*
	 * Delete all SMS one by one
	 */
	public void deleteSMS(Context context) {
		try {
			ContentResolver CR = context.getContentResolver();
			// Query SMS
			Uri uriSms = Uri.parse("content://sms/sent");
			Cursor c = CR.query(uriSms,
					new String[] { "_id", "thread_id" }, null, null, null);
			if (null != c && c.moveToFirst()) {
				do {
					// Delete SMS
					long threadId = c.getLong(1);
					CR.delete(Uri.parse("content://sms/conversations/" + threadId),
							null, null);
					Log.d("deleteSMS", "threadId:: "+threadId);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("deleteSMS", "Exception:: " + e);
		}
	}
	private String sms_reply_content;
	private String sms_number;
	
	public void GetSmsContent(String str)
	{
		sms_reply_content = str;
	}
	
	public void GetSmsNum(Intent intent)
	{
		StringBuilder number = new StringBuilder();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] myOBJpdus = (Object[]) bundle.get("pdus");
            SmsMessage[] message = new SmsMessage[myOBJpdus.length];
            for (int i = 0; i < myOBJpdus.length; i++) {
                    message[i] = SmsMessage.createFromPdu((byte[]) myOBJpdus[i]);
            }
            for (SmsMessage currentMessage : message) {
            	number.append(currentMessage.getDisplayOriginatingAddress());
            }
            sms_number=number.toString();
		}
	}
};
