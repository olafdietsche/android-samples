// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.show_simcard_info;

import java.util.ArrayList;

import android.telephony.TelephonyManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String device_id = tm.getDeviceId();
		if (device_id == null)
			device_id = getString(R.string.number_not_available);

		TextView imei = (TextView) findViewById(R.id.imei);
		imei.setText(device_id);

		String number = tm.getLine1Number();
		if (number == null)
			number = getString(R.string.number_not_available);

		TextView lineNumber = (TextView) findViewById(R.id.line_number);
		lineNumber.setText(number);
		
/*
		ListView list = (ListView) findViewById(R.id.list);
		View header = list.inflate(this, R.layout.header, null);
		list.addHeaderView(header);

		AccountManager am = AccountManager.get(this);
		Account[] accounts = am.getAccounts();

		AccountsListAdapter adapter = new AccountsListAdapter(this, accounts);
		list.setAdapter(adapter);
//*/
	}

	private static final String TAG = MainActivity.class.getName();
}
