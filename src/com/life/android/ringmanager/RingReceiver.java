package com.life.android.ringmanager;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
import android.media.AudioManager;
import android.telephony.TelephonyManager;

public class RingReceiver extends BroadcastReceiver
//		implements
//		SensorEventListener 
		{

	private Intent intent;
	private TelephonyManager telephonyManager;
	private AudioManager audioManager;
	private int current = 1;

	@Override
	public void onReceive(Context context, Intent intent) {

		this.intent = intent;
		this.telephonyManager = (TelephonyManager) context
				.getSystemService(Service.TELEPHONY_SERVICE);

		this.audioManager = (AudioManager) context
				.getSystemService(Service.AUDIO_SERVICE);

		if (this.audioManager == null) {
			return;
		}

		this.current = this.audioManager
				.getStreamVolume(AudioManager.STREAM_RING);

		if (!(this.intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL))) {
			changeStreamVolume(this.audioManager, this.current,
					this.telephonyManager.getCallState(),
					AudioManager.ADJUST_RAISE);
		}
	}

//	@Override
//	public void onSensorChanged(SensorEvent event) {
//		float x = event.values[SensorManager.DATA_X];
//		float y = event.values[SensorManager.DATA_Y];
//		float z = event.values[SensorManager.DATA_Z];
//
//		if (!(this.intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL))
//				&& x != 0 && y != 0 && z != 10) {
//			changeStreamVolume(this.audioManager, this.current,
//					this.telephonyManager.getCallState(),
//					AudioManager.ADJUST_LOWER);
//		}
//	}
//
//	@Override
//	public void onAccuracyChanged(Sensor sensor, int accuracy) {
//	}

	private void changeStreamVolume(AudioManager audioManager,
			int currentVolume, int callState, int direction) {
		switch (callState) {
		case TelephonyManager.CALL_STATE_RINGING:
			if (direction == AudioManager.ADJUST_RAISE) {
				for (int i = 1; i <= currentVolume; i++) {
					try {
						audioManager.setStreamVolume(AudioManager.STREAM_RING,
								i, AudioManager.FLAG_VIBRATE);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (direction == AudioManager.ADJUST_LOWER) {
				for (int i = currentVolume; i > 1; i--) {
					try {
						audioManager.setStreamVolume(AudioManager.STREAM_RING,
								i, AudioManager.FLAG_VIBRATE);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			audioManager.setStreamVolume(AudioManager.STREAM_RING,
					currentVolume, AudioManager.FLAG_VIBRATE);
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			audioManager.setStreamVolume(AudioManager.STREAM_RING,
					currentVolume, AudioManager.FLAG_VIBRATE);
			break;
		}
	}
}
