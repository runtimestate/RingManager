package com.life.android.ringmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent ringServiceIntent = new Intent(context, RingService.class);
		ringServiceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startService(ringServiceIntent); 
	}
}
