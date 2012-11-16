package com.life.android.ringmanager;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class SensorService extends Service implements SensorEventListener {

	private PhoneStateListener phoneStateListener;
	private TelephonyManager telephonyManager;
	private AudioManager audioManager;
	private SensorManager sensorManager;
	private Sensor sensor;
	private int current = 1;

	private Float firstX;
	private Float firstY;
	private Float firstZ;

	@Override
	public void onCreate() {
		this.telephonyManager = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);

		this.sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
		this.sensor = this.sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		this.audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

		if (this.audioManager == null) {
			return;
		}

		this.current = this.audioManager
				.getStreamVolume(AudioManager.STREAM_RING);

		this.phoneStateListener = new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				registerSensorListener(sensorManager, SensorService.this,
						sensor, state);
				super.onCallStateChanged(state, incomingNumber);
			}
		};

		this.telephonyManager.listen(phoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	@Override
	public void onSensorChanged(SensorEvent paramSensorEvent) {
		float x = paramSensorEvent.values[SensorManager.DATA_X];
		float y = paramSensorEvent.values[SensorManager.DATA_Y];
		float z = paramSensorEvent.values[SensorManager.DATA_Z];
		if (firstX == null) {
			firstX = x;
		}
		if (firstY == null) {
			firstY = y;
		}
		if (firstZ == null) {
			firstZ = z;
		}

		if (firstX == 0 && firstY == 0 && firstZ > 0 && x != 0 && y != 0) {
			VolumeUtil.changeStreamVolume(this.audioManager, this.current,
					this.telephonyManager.getCallState(),
					AudioManager.ADJUST_LOWER);
		}
	}

	@Override
	public IBinder onBind(Intent paramIntent) {
		return null;
	}

	@Override
	public void onAccuracyChanged(Sensor paramSensor, int paramInt) {
	}

	private void registerSensorListener(SensorManager sensorManager,
			SensorEventListener sensorEventListener, Sensor sensor,
			int callState) {
		switch (callState) {
		case TelephonyManager.CALL_STATE_RINGING:
			sensorManager.registerListener(sensorEventListener, sensor,
					SensorManager.SENSOR_DELAY_NORMAL);
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			sensorManager.unregisterListener(sensorEventListener);
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			sensorManager.unregisterListener(sensorEventListener);
			break;
		}
	}
}
