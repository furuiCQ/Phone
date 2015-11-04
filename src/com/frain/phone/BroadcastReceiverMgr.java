package com.frain.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BroadcastReceiverMgr extends BroadcastReceiver {

	private final String TAG = MainActivity.TAG;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.i(TAG, "[Broadcast]" + action);

		// 呼入电话
		if (action.equals(MainActivity.B_PHONE_STATE)) {
			Log.i(TAG, "[Broadcast]PHONE_STATE");
			doReceivePhone(context, intent);
		}
	}

	/**
	 * 处理电话广播.
	 * 
	 * @param context
	 * @param intent
	 */
	public void doReceivePhone(Context context, Intent intent) {
		String phoneNumber = intent
				.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int state = telephony.getCallState();
		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING:
			Log.i(TAG, "[Broadcast]等待接电话=" + phoneNumber);
			PhoneNumbManager.checkNumber(phoneNumber,context, telephony);
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			Log.i(TAG, "[Broadcast]电话挂断=" + phoneNumber);
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			Log.i(TAG, "[Broadcast]通话中=" + phoneNumber);
			break;
		}
	}
}
