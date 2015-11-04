package com.frain.phone;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import com.android.internal.telephony.ITelephony;

public class PhoneNumbManager {
    static String TAG = "PhoneUtils";

    public static void checkNumber(String numb,Context c,TelephonyManager tm){
    	if(numb.equals("18716341029")){
    		autoAnswerPhone(c, tm);
    	}else{
    		endPhone(c, tm);
    	}
    	
    }
    
	 public static ITelephony getITelephony(TelephonyManager telMgr)
            throws Exception {
        Method getITelephonyMethod = telMgr.getClass().getDeclaredMethod(
                "getITelephony");
        getITelephonyMethod.setAccessible(true);// 私有化函数也能使用
        return (ITelephony) getITelephonyMethod.invoke(telMgr);
    }
     
	//自动挂断
    public static void endPhone(Context context,TelephonyManager tm) {
    	 TelephonyManager telMag = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
         Class<TelephonyManager> c = TelephonyManager.class;  
         Method mthEndCall = null;  
         try {  
             mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);  
             mthEndCall.setAccessible(true);  
             ITelephony iTel = (ITelephony) mthEndCall.invoke(telMag,  
                     (Object[]) null);  
             iTel.endCall();  
             Log.i(TAG, iTel.toString());  
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
         Log.i(TAG, "endCall test");  
    }
    //自动接通
	public static void autoAnswerPhone(Context c, TelephonyManager tm) {
		try {
			Log.i(TAG, "autoAnswerPhone");
			ITelephony itelephony = getITelephony(tm);
			// itelephony.silenceRinger();
			itelephony.answerRingingCall();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Log.e(TAG, "用于Android2.3及2.3以上的版本上");
				Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_HEADSETHOOK);
				intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
				c.sendOrderedBroadcast(intent,
						"android.permission.CALL_PRIVILEGED");
				intent = new Intent("android.intent.action.MEDIA_BUTTON");
				keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
				c.sendOrderedBroadcast(intent,
						"android.permission.CALL_PRIVILEGED");
				Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
				localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				localIntent1.putExtra("state", 1);
				localIntent1.putExtra("microphone", 1);
				localIntent1.putExtra("name", "Headset");
				c.sendOrderedBroadcast(localIntent1,
						"android.permission.CALL_PRIVILEGED");
				Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
				KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_HEADSETHOOK);
				localIntent2.putExtra("android.intent.extra.KEY_EVENT",
						localKeyEvent1);
				c.sendOrderedBroadcast(localIntent2,
						"android.permission.CALL_PRIVILEGED");
				Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
				KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				localIntent3.putExtra("android.intent.extra.KEY_EVENT",
						localKeyEvent2);
				c.sendOrderedBroadcast(localIntent3,
						"android.permission.CALL_PRIVILEGED");
				Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
				localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				localIntent4.putExtra("state", 0);
				localIntent4.putExtra("microphone", 1);
				localIntent4.putExtra("name", "Headset");
				c.sendOrderedBroadcast(localIntent4,
						"android.permission.CALL_PRIVILEGED");
			} catch (Exception e2) {
				e2.printStackTrace();
				Intent meidaButtonIntent = new Intent(
						Intent.ACTION_MEDIA_BUTTON);
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
				c.sendOrderedBroadcast(meidaButtonIntent, null);
			}
		}
	}

}
