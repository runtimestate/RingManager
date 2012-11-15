package com.life.android.ringmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity implements OnClickListener {

	private CheckBox ringCheckBox;
	private CheckBox sensorCheckBox;

	private Button saveButton;
	private Button cancelButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ringCheckBox = (CheckBox) findViewById(R.id.ringCheckBox);
		sensorCheckBox = (CheckBox) findViewById(R.id.sensorCheckBox);

		saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(this);
		cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.saveButton:
			boolean ringChecked = ringCheckBox.isChecked();
			Intent ringIntent = new Intent();
			ringIntent.setClass(this, RingService.class);
			if (ringChecked) {
				startService(ringIntent);
			} else {
				stopService(ringIntent);
			}

			boolean sensorChecked = sensorCheckBox.isChecked();
			Intent sensorIntent = new Intent();
			sensorIntent.setClass(this, SensorService.class);
			if (sensorChecked) {
				startService(sensorIntent);
			} else {
				stopService(sensorIntent);
			}
			break;
		case R.id.cancelButton:
			finish();
			break;
		}
	}
}
