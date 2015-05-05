package com.client.SMS_DAEMON;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

public class Constant {
	public static class Preference {

		public static SharedPreferences getSharedPreferences(Context context) {
			return context.getSharedPreferences("SMS_DAEMONSharePref", Context.MODE_PRIVATE);
		}
	}
	public static String C_ContainAutoNotReply = "ContainAutoNotReply";
	public static String C_TextContainAutoReply = "TextContainAutoReply";
	public static String C_AutoReplyNum = "AutoReplyNum";
	public static String C_AutoReplyText = "AutoReplyText";
}
