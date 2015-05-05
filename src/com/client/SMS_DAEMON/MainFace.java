package com.client.SMS_DAEMON;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.service.SMS_DAEMON.BackService;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
@EActivity(R.layout.main1)
public class MainFace extends Activity {
	public Intent BackgroundServiceIntent;
	@ViewById
	EditText EditTextAutoReplyNum,EditTextContainAutoReply,EditTextContainAutoNotReply,EditTextAutoReplyText;
	
	@Click(R.id.ButtonSet)
	void OnClickButtonSet(View v){
		
	}
	@Click(R.id.ButtonClose)
	void OnClickButtonClose(View v){
		
	}
    /** Called when the activity is first created. */
    @AfterViews
    void Init(){
    	FindAndModify();
    }
    private void FindAndModify(){
    	Button btnSet = (Button)findViewById(R.id.set);
    	btnSet.setOnClickListener(btnSet_listener);
    	Button btnUnSet = (Button)findViewById(R.id.unset);
    	btnUnSet.setOnClickListener(btnUnSet_listener);
    }
    
    private Button.OnClickListener btnSet_listener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {

			// TODO Auto-generated method stub
			if(BackgroundServiceIntent == null){
				BackgroundServiceIntent = new Intent(MainFace.this, BackService.class);
				EditText editText = (EditText)findViewById(R.id.editText_reply);
				String message = editText.getText().toString();			
				BackgroundServiceIntent.putExtra("content", message);
				startService(BackgroundServiceIntent);
			
			}
			
//			Toast.makeText(MainFace.this, "the sms auto reply function has started!", Toast.LENGTH_LONG).show();
		}
    	
    };
    
    private Button.OnClickListener btnUnSet_listener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(BackgroundServiceIntent != null){
				stopService(BackgroundServiceIntent);
				BackgroundServiceIntent = null;
				
//				Toast.makeText(MainFace.this, "the sms auto reply function has stopped!", Toast.LENGTH_LONG).show();
			}

		}
    	
    }; 
    private long getThreadId() {

		long threadId = 0;
	
		String SMS_READ_COLUMN = "read";
	
		String WHERE_CONDITION = SMS_READ_COLUMN + " = 0";
	
		String SORT_ORDER = "date DESC";
	
		int count = 0;
	
		Cursor cursor = getApplicationContext().getContentResolver().query(
	
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
	
}