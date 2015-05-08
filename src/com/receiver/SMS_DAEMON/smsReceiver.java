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
		StringBuilder SmsMessageText = new StringBuilder();
		Bundle bundle = intent.getExtras();
		
		if (bundle != null) {
			Object[] myOBJpdus = (Object[]) bundle.get("pdus");
            SmsMessage[] message = new SmsMessage[myOBJpdus.length];
            for (int i = 0; i < myOBJpdus.length; i++) {
                    message[i] = SmsMessage.createFromPdu((byte[]) myOBJpdus[i]);
            }
            if(myOBJpdus.length > 0){
            	number.append(message[0].getDisplayOriginatingAddress());
            }
            for (SmsMessage currentMessage : message) {
            	SmsMessageText.append(currentMessage.getDisplayMessageBody());
            }
            String SmsMessageContent = SmsMessageText.toString();
            String l_number = number.toString();
            Log.i("收到短信from："+l_number, SmsMessageContent);
            //如果以该数字开头的号码则回复
            if(l_number.indexOf(getSms_number()) != -1){
	            //如果包括此内容则不回复
//	            if(SmsMessageContent.indexOf(getSms_ContainAutoNotReply()) == -1){
	            	//如果包括此内容则回复
	            	if(SmsMessageContent.indexOf(getSms_ContainAutoReply()) != -1){
	            		
                		SmsManager smsManager = SmsManager.getDefault();
                		//回复设定的内容
                		smsManager.sendTextMessage(getSms_EditTextSendNum(), null, SmsMessageContent, null, null);
                		Log.i("发送短信to："+getSms_EditTextSendNum(), SmsMessageContent);
	            	}
	
//	            }
	        }

		}
/*		long id = getThreadId(context);

		Uri mUri=Uri.parse("content://sms/conversations/" + id);

		context.getContentResolver().delete(mUri, null, null);*/
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
	private String sms_ContainAutoReply;
	private String sms_ContainAutoNotReply;
	private String sms_EditTextSendNum;
	public void GetSmsContent(String str)
	{
		setSms_reply_content(str);
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
            setSms_number(number.toString());
		}
	}
	public String getSms_reply_content() {
		return sms_reply_content;
	}
	public void setSms_reply_content(String sms_reply_content) {
		this.sms_reply_content = sms_reply_content;
	}
	public String getSms_number() {
		return sms_number;
	}
	public void setSms_number(String sms_number) {
		this.sms_number = sms_number;
	}
	public String getSms_ContainAutoReply() {
		return sms_ContainAutoReply;
	}
	public void setSms_ContainAutoReply(String sms_ContainAutoReply) {
		this.sms_ContainAutoReply = sms_ContainAutoReply;
	}
	public String getSms_ContainAutoNotReply() {
		return sms_ContainAutoNotReply;
	}
	public void setSms_ContainAutoNotReply(String sms_ContainAutoNotReply) {
		this.sms_ContainAutoNotReply = sms_ContainAutoNotReply;
	}
	public String getSms_EditTextSendNum() {
		return sms_EditTextSendNum;
	}
	public void setSms_EditTextSendNum(String sms_EditTextSendNum) {
		this.sms_EditTextSendNum = sms_EditTextSendNum;
	}
};
