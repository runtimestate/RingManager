package com.life.android.ringmanager;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class RingService extends Service {

	private PhoneStateListener phoneStateListener;
	private TelephonyManager telephonyManager;
	private AudioManager audioManager;
	private int current = 1;

	@Override
	public void onCreate() {
		this.telephonyManager = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);

		this.audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

		if (this.audioManager == null) {
			return;
		}

		this.current = this.audioManager
				.getStreamVolume(AudioManager.STREAM_RING);

		this.phoneStateListener = new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				VolumeUtil.changeStreamVolume(audioManager, current, state,
						AudioManager.ADJUST_RAISE);

				super.onCallStateChanged(state, incomingNumber);
			}
		};

		this.telephonyManager.listen(phoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent paramIntent) {
		return null;
	}
}
