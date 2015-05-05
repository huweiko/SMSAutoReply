package com.client.SMS_DAEMON;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���Activity����ջ
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//����Activity&�Ӷ�ջ���Ƴ�
		AppManager.getAppManager().removeActivity(this);
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

}
