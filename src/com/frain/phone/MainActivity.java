package com.frain.phone;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	public final static String TAG = "MyBroadcastReceiver";
	
	public final static String B_PHONE_STATE = 
TelephonyManager.ACTION_PHONE_STATE_CHANGED;
	
	private BroadcastReceiverMgr mBroadcastReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerIt();
	}	

	//按钮1-注册广播
	public void registerIt() {
		Log.i(TAG, "registerIt");
		mBroadcastReceiver = new BroadcastReceiverMgr();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(B_PHONE_STATE);
		intentFilter.setPriority(Integer.MAX_VALUE);
		registerReceiver(mBroadcastReceiver, intentFilter);
	}
	
	//按钮2-撤销广播
	public void unregisterIt() {
		Log.i(TAG, "unregisterIt");
		unregisterReceiver(mBroadcastReceiver);
	}
	
}