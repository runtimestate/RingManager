package com.life.android.ringmanager;

import android.media.AudioManager;
import android.telephony.TelephonyManager;

public class VolumeUtil {

	public static void changeStreamVolume(AudioManager audioManager,
			int currentVolume, int callState, int direction) {
		switch (callState) {
		case TelephonyManager.CALL_STATE_RINGING:
			if (direction == AudioManager.ADJUST_RAISE) {
				for (int i = 1; i <= currentVolume; i++) {
					try {
						audioManager.setStreamVolume(AudioManager.STREAM_RING,
								i, AudioManager.FLAG_VIBRATE);
						Thread.sleep(1500);
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
