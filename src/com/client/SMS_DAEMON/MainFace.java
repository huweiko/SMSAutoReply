package com.client.SMS_DAEMON;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.service.SMS_DAEMON.BackService;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.client.SMS_DAEMON.R;
@EActivity(R.layout.main1)
public class MainFace extends BaseActivity {
	public Intent BackgroundServiceIntent;
	@ViewById
	EditText EditTextAutoReplyNum,EditTextContainAutoReply,EditTextContainAutoNotReply,EditTextAutoReplyText;
	
	@Click(R.id.ButtonSet)
	void OnClickButtonSet(View v){
		if(BackgroundServiceIntent == null){
			BackgroundServiceIntent = new Intent(MainFace.this, BackService.class);
			String message = EditTextAutoReplyText.getText().toString();			
			BackgroundServiceIntent.putExtra("content", message);
			startService(BackgroundServiceIntent);
		
		}
	}
	@Click(R.id.ButtonClose)
	void OnClickButtonClose(View v){
		if(BackgroundServiceIntent != null){
			stopService(BackgroundServiceIntent);
			BackgroundServiceIntent = null;
			
//			Toast.makeText(MainFace.this, "the sms auto reply function has stopped!", Toast.LENGTH_LONG).show();
		}
	}
    /** Called when the activity is first created. */
    @AfterViews
    void Init(){
//    	FindAndModify();
    }
    
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