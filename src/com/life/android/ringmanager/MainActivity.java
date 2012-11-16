package com.life.android.ringmanager;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class MainActivity extends PreferenceActivity implements
		OnPreferenceChangeListener {

	private String ringCheckKey;
	private String sensorCheckKey;
	private CheckBoxPreference ringCheckPref;
	private CheckBoxPreference sensorCheckPref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		ringCheckKey = getResources().getString(R.string.ringKey);
		sensorCheckKey = getResources().getString(R.string.sensorKey);

		ringCheckPref = (CheckBoxPreference) findPreference(ringCheckKey);
		sensorCheckPref = (CheckBoxPreference) findPreference(sensorCheckKey);

		ringCheckPref.setOnPreferenceChangeListener(this);
		sensorCheckPref.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference paramPreference,
			Object paramObject) {
		boolean isChecked = (Boolean) paramObject;
		if (paramPreference.getKey().equals(ringCheckKey)) {
			ringCheckPref.setChecked(isChecked);
			Intent ringIntent = new Intent();
			ringIntent.setClass(this, RingService.class);
			if (isChecked) {
				startService(ringIntent);
			} else {
				stopService(ringIntent);
			}
		} else if (paramPreference.getKey().equals(sensorCheckKey)) {
			sensorCheckPref.setChecked(isChecked);
			Intent sensorIntent = new Intent();
			sensorIntent.setClass(this, SensorService.class);
			if (isChecked) {
				startService(sensorIntent);
			} else {
				stopService(sensorIntent);
			}
		}
		return true;
	}
}
