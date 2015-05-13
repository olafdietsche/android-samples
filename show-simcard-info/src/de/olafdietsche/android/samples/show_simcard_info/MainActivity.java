// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.show_simcard_info;

import android.telephony.TelephonyManager;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String info;
		TextView tv;

		info = tm.getDeviceId();
		if (info == null)
			info = getString(R.string.number_not_available);

		tv = (TextView) findViewById(R.id.imei);
		tv.setText(info);

		info = tm.getSimSerialNumber();
		if (info == null)
			info = getString(R.string.number_not_available);

		tv = (TextView) findViewById(R.id.serial_number);
		tv.setText(info);

		info = tm.getLine1Number();
		if (info == null)
			info = getString(R.string.number_not_available);

		tv = (TextView) findViewById(R.id.line_number);
		tv.setText(info);
	}

	private static final String TAG = MainActivity.class.getName();
}
